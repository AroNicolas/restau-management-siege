package school.hei.restaurant.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Sales {
    private Long id;
    private Long dishIdentifier;
    private String salesPoint;
    private String dish;
    private int quantitySold;
    private Double unitPrice;
    private Long totalAmount;
}
