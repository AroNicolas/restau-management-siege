package school.hei.restaurant.endpoints.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import school.hei.restaurant.endpoints.rest.BestSalesRest;
import school.hei.restaurant.endpoints.rest.SalesRest;
import school.hei.restaurant.model.BestSales;

@Component
public class BestSalesRestMapper {
    @Autowired
    private SalesRestMapper salesRestMapper;

    public BestSalesRest toRest(BestSales bestSales) {
        SalesRest sales = salesRestMapper.apply(bestSales.getSales());
        return new BestSalesRest(bestSales.getUpdatedAt(), sales);
    }
}
