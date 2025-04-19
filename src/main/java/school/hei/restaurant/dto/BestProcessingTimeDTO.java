package school.hei.restaurant.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class BestProcessingTimeDTO {
    private Long dishIdentifier;
    private String dishName;
    private Double quantity;
    private Instant startedAt;
    private Instant finishedAt;
    private String salesPoint;
}
