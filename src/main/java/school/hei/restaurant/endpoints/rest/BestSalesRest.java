package school.hei.restaurant.endpoints.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@AllArgsConstructor
@Getter
public class BestSalesRest {
    private Instant updatedAt;
    private SalesRest sales;
}
