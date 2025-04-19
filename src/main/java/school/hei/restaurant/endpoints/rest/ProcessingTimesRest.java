package school.hei.restaurant.endpoints.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@AllArgsConstructor
@Getter
public class ProcessingTimesRest {
    private Instant updatedAt;
    private BestProcessingTimesRest bestProcessingTimes;
}
