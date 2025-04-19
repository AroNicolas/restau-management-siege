package school.hei.restaurant.dao.mapper;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import school.hei.restaurant.model.BestSales;
import school.hei.restaurant.model.Sales;

import java.sql.ResultSet;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class BestSalesMapper implements Function<ResultSet, BestSales> {

    @SneakyThrows
    @Override
    public BestSales apply(ResultSet resultSet) {
        BestSales best = new BestSales();
        best.setUpdatedAt(resultSet.getTimestamp("updated_at").toInstant());

        Sales sales = new Sales();
        sales.setSalesPoint(resultSet.getString("sales_point"));
        sales.setDish(resultSet.getString("dish_name"));
        sales.setQuantitySold(resultSet.getInt("quantity_sold"));
        sales.setTotalAmount(resultSet.getLong("total_amount"));

        best.setSales(sales);

        return best;
    }
}
