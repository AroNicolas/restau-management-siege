package school.hei.restaurant.model;

import lombok.*;

import java.time.Duration;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class BestProcessingTimes {
    private Long id;
    private Long dishIdentifier;
    private String salesPoint;
    private String dish;
    private Double quantity;
    private Duration preparationDuration;
    private DurationUnit durationUnit;
    private Instant startedAt;
    private Instant finishedAt;
}
