package school.hei.restaurant.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SalesDTO {
    private Long dishIdentifier;
    private String dishName;
    private Integer quantitySold;
    private Double unitPrice;
    private String salesPoint;
}
