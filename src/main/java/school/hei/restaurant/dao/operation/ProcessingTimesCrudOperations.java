package school.hei.restaurant.dao.operation;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;
import school.hei.restaurant.dao.DataSource;
import school.hei.restaurant.dao.mapper.ProcessingTimesMapper;
import school.hei.restaurant.model.BestProcessingTimes;
import school.hei.restaurant.model.DurationUnit;
import school.hei.restaurant.model.ProcessingTimes;
import school.hei.restaurant.service.exception.ServerException;

import java.sql.*;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProcessingTimesCrudOperations {
    private final DataSource dataSource;
    private final ProcessingTimesMapper processingTimesMapper;

    @SneakyThrows
    public List<ProcessingTimes> getBestProcessingTime(
            Long dishId,
            Integer top,
            String unit,
            String mode
    ) {
        int resolvedTop = (top != null) ? top : 5;
        String resolvedUnit = (unit != null) ? unit.toUpperCase() : "SECONDS";
        String resolvedMode = (mode != null) ? mode.toUpperCase() : "AVERAGE";

        if (!List.of("SECONDS", "MINUTES", "HOUR").contains(resolvedUnit)) {
            throw new IllegalArgumentException("Invalid duration unit");
        }
        if (!List.of("AVERAGE", "MINIMUM", "MAXIMUM").contains(resolvedMode)) {
            throw new IllegalArgumentException("Invalid calculation mode");
        }

        String durationExpression = switch (resolvedMode) {
            case "AVERAGE" -> "AVG(EXTRACT(EPOCH FROM (finished_at - started_at)) / quantity)";
            case "MINIMUM" -> "MIN(EXTRACT(EPOCH FROM (finished_at - started_at)) / quantity)";
            case "MAXIMUM" -> "MAX(EXTRACT(EPOCH FROM (finished_at - started_at)) / quantity)";
            default -> throw new IllegalStateException("Unexpected mode: " + resolvedMode);
        };

        String sql = """
        SELECT
            sales_point,
            dish_name,
            %s AS preparation_duration
        FROM processing_time
        WHERE dish_identifier = ?
        GROUP BY sales_point, dish_name
        ORDER BY preparation_duration ASC
        LIMIT ?
    """.formatted(durationExpression);

        Instant now = Instant.now();
        List<ProcessingTimes> result = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, dishId);
            stmt.setInt(2, resolvedTop);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                BestProcessingTimes item = processingTimesMapper.apply(rs, resolvedUnit);

                // Persister dans la table best_processing_times
                try (PreparedStatement insertStmt = conn.prepareStatement("""
                INSERT INTO best_processing_times (
                    dish_identifier, sales_point, dish_name,
                    preparation_duration, duration_unit, calculation_mode
                ) VALUES (?, ?, ?, ?, ?, ?)
            """)) {
                    insertStmt.setLong(1, dishId);
                    insertStmt.setString(2, item.getSalesPoint());
                    insertStmt.setString(3, item.getDish());

                    Duration d = item.getPreparationDuration();
                    insertStmt.setObject(4, Duration.ofSeconds(d.getSeconds(), d.getNano()));
                    insertStmt.setString(5, resolvedUnit);
                    insertStmt.setString(6, resolvedMode);
                    insertStmt.executeUpdate();
                }

                // Emballer dans ProcessingTimes
                ProcessingTimes processing = new ProcessingTimes();
                processing.setUpdatedAt(now);
                processing.setBestProcessingTimes(item);
                result.add(processing);
            }
        }

        return result;
    }



    @SneakyThrows
    public void saveAll(List<BestProcessingTimes> prepEntities) {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement =
                         connection.prepareStatement("""
                                 INSERT INTO processing_time (dish_identifier, sales_point, dish_name, quantity, started_at, finished_at)
                                 VALUES (?, ?, ?, ?, ?, ?)
                                 """)) {
                prepEntities.forEach(entityToSave -> {
                    try {
                        statement.setLong(1, entityToSave.getDishIdentifier());
                        statement.setString(2, entityToSave.getSalesPoint());
                        statement.setString(3, entityToSave.getDish());
                        statement.setDouble(4, entityToSave.getQuantity());
                        statement.setTimestamp(5, Timestamp.from(entityToSave.getStartedAt()));
                        statement.setTimestamp(6, Timestamp.from(entityToSave.getFinishedAt()));
                        statement.addBatch(); // group by batch so executed as one query in database
                    } catch (SQLException e) {
                        throw new ServerException(e);
                    }
                });
                statement.executeBatch();
                statement.executeUpdate();
            }
        }
    }
}
