package school.hei.restaurant.endpoints.mapper;

import org.springframework.stereotype.Component;
import school.hei.restaurant.endpoints.rest.SalesRest;
import school.hei.restaurant.model.Sales;

import java.util.function.Function;

@Component
public class SalesRestMapper implements Function<Sales, SalesRest> {

    @Override
    public SalesRest apply(Sales sales) {
        return new SalesRest(sales.getSalesPoint(),
                sales.getDish(),
                sales.getQuantitySold(),
                sales.getTotalAmount());
    }
}
