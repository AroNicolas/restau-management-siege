package school.hei.restaurant.dao.mapper;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import school.hei.restaurant.model.BestProcessingTimes;
import school.hei.restaurant.model.DurationUnit;

import java.sql.ResultSet;
import java.util.function.BiFunction;

@Component
@RequiredArgsConstructor
public class ProcessingTimesMapper implements BiFunction<ResultSet, String, BestProcessingTimes> {

    @SneakyThrows
    @Override
    public BestProcessingTimes apply(ResultSet rs, String resolvedUnit) {
        BestProcessingTimes item = new BestProcessingTimes();

        item.setSalesPoint(rs.getString("sales_point"));
        item.setDish(rs.getString("dish_name"));

        double rawDuration = rs.getDouble("preparation_duration");

        double convertedDuration = switch (resolvedUnit) {
            case "MINUTES" -> rawDuration / 60.0;
            case "HOUR"    -> rawDuration / 3600.0;
            default        -> (double) rawDuration; // SECONDS
        };

        item.setPreparationDuration(convertedDuration);
        item.setDurationUnit(DurationUnit.valueOf(resolvedUnit));

        return item;
    }
}