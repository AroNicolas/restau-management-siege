package school.hei.restaurant.endpoints.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import school.hei.restaurant.model.DurationUnit;

import java.time.Duration;

@AllArgsConstructor
@Getter
public class BestProcessingTimesRest {
    private String salesPoint;
    private String dish;
    private Duration preparationDuration;
    private DurationUnit durationUnit;
}
