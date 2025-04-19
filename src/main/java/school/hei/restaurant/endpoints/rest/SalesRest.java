package school.hei.restaurant.endpoints.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SalesRest {
    private String salesPoint;
    private String dish;
    private int quantitySold;
    private Long totalAmount;
}
