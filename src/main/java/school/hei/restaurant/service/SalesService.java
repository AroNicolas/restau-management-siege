package school.hei.restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.restaurant.dao.operation.SalesCrudOperations;
import school.hei.restaurant.model.BestSales;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalesService {
    private final SalesCrudOperations salesCrudOperations;

    public List<BestSales> getBestSales(int top) {
        return salesCrudOperations.getBestSales(top);
    }

}
