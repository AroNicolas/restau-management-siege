package school.hei.restaurant.model;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class BestSales {
    private Long id;
    private Instant updatedAt;
    private Sales sales;
    private Long totalAmount;
}
