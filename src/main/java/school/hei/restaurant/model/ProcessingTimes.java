package school.hei.restaurant.model;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ProcessingTimes {
    private Long id;
    private Instant updatedAt;
    private BestProcessingTimes bestProcessingTimes;
}
