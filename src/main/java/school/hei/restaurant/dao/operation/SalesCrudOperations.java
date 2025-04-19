package school.hei.restaurant.dao.operation;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;
import school.hei.restaurant.dao.DataSource;
import school.hei.restaurant.dao.mapper.BestSalesMapper;
import school.hei.restaurant.model.BestSales;
import school.hei.restaurant.model.Sales;
import school.hei.restaurant.service.exception.ServerException;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SalesCrudOperations{
    private final DataSource dataSource;
    private final BestSalesMapper bestsalesMapper;

    public List<BestSales> getBestSales(int top) {
        Instant now = Instant.now();

        String insertSql = """
        INSERT INTO best_sales (
            dish_identifier,
            sales_point,
            dish_name,
            quantity_sold,
            total_amount,
            updated_at
        )
        WITH summed_sales AS (
            SELECT
                dish_identifier,
                dish_name,
                sales_point,
                SUM(quantity_sold) AS total_quantity,
                SUM(quantity_sold * unit_price) AS total_amount
            FROM sales
            GROUP BY dish_identifier, dish_name, sales_point
        ),
        ranked_sales AS (
            SELECT *,
                   RANK() OVER (PARTITION BY dish_identifier ORDER BY total_quantity DESC) AS rk
            FROM summed_sales
        ),
        top_dishes AS (
            SELECT * FROM ranked_sales WHERE rk = 1
        )
        SELECT
            dish_identifier,
            sales_point,
            dish_name,
            total_quantity,
            total_amount,
            ? -- updated_at
        FROM top_dishes
        ORDER BY total_quantity DESC
        LIMIT ?
    """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {

            insertStmt.setTimestamp(1, Timestamp.from(now));
            insertStmt.setInt(2, top);
            insertStmt.executeUpdate();
        } catch (SQLException e) {
            throw new ServerException(e);
        }

        // Lecture des données persistées (top X, dernier updated_at)
        String selectSql = """
        SELECT sales_point, dish_name, quantity_sold, total_amount, updated_at
        FROM best_sales
        WHERE updated_at = (SELECT MAX(updated_at) FROM best_sales)
        ORDER BY quantity_sold DESC
        LIMIT ?
    """;

        List<BestSales> results = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(selectSql)) {

            selectStmt.setInt(1, top);
            try (ResultSet rs = selectStmt.executeQuery()) {
                while (rs.next()) {
                    results.add(bestsalesMapper.apply(rs));
                }
            }
        } catch (SQLException e) {
            throw new ServerException(e);
        }
        return results;
    }

    @SneakyThrows
    public void saveAll(List<Sales> saleEntities) {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement =
                         connection.prepareStatement("""
                                 INSERT INTO sales (dish_identifier, sales_point, dish_name, quantity_sold, unit_price)
                                 VALUES (?, ?, ?, ?, ?)
                                 """)) {
                saleEntities.forEach(entityToSave -> {
                    try {
                        statement.setLong(1, entityToSave.getDishIdentifier());
                        statement.setString(2, entityToSave.getSalesPoint());
                        statement.setString(3, entityToSave.getDish());
                        statement.setInt(4, entityToSave.getQuantitySold());
                        statement.setDouble(5, entityToSave.getUnitPrice());
                        statement.addBatch(); // group by batch so executed as one query in database
                    } catch (SQLException e) {
                        throw new ServerException(e);
                    }
                });
                statement.executeBatch();
                statement.executeUpdate();
            }
        }
    }
}
