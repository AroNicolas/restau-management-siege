package school.hei.restaurant.dao.mapper;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import school.hei.restaurant.model.BestProcessingTimes;
import school.hei.restaurant.model.DurationUnit;
import school.hei.restaurant.model.ProcessingTimes;

import java.sql.ResultSet;
import java.time.Duration;
import java.util.function.BiFunction;

@Component
@RequiredArgsConstructor
public class ProcessingTimesMapper implements BiFunction<ResultSet, String, ProcessingTimes> {

    @SneakyThrows
    @Override
    public ProcessingTimes apply(ResultSet rs, String resolvedUnit) {
            ProcessingTimes processingTimes = new ProcessingTimes();
            processingTimes.setUpdatedAt(rs.getTimestamp("updated_at").toInstant());

            BestProcessingTimes item = new BestProcessingTimes();
            item.setSalesPoint(rs.getString("sales_point"));
            item.setDish(rs.getString("dish_name"));

            double rawDuration = rs.getDouble("preparation_duration");

            double seconds = switch (resolvedUnit) {
                case "MINUTES" -> rawDuration / 60.0;
                case "HOUR" -> rawDuration / 3600.0;
                default -> rawDuration;
            };

            long secondsPart = (long) seconds;
            long nanosPart = (long) ((seconds - secondsPart) * 1_000_000_000);

            Duration duration = Duration.ofSeconds(secondsPart, nanosPart);
            item.setPreparationDuration(duration);
            item.setDurationUnit(DurationUnit.valueOf(resolvedUnit));

            processingTimes.setBestProcessingTimes(item);
            return processingTimes;
        }
    }