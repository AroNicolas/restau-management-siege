package school.hei.restaurant.endpoints;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.hei.restaurant.endpoints.mapper.BestSalesRestMapper;
import school.hei.restaurant.endpoints.rest.BestSalesRest;
import school.hei.restaurant.model.BestSales;
import school.hei.restaurant.service.SalesService;
import school.hei.restaurant.service.exception.ServerException;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SalesRestController {
    private final BestSalesRestMapper bestSalesRestMapper;
    private final SalesService salesService;

    @GetMapping("/bestSales")
    public ResponseEntity<Object> getBestSales(@RequestParam int top) {
        try {
            List<BestSales> bestSales = salesService.getBestSales(top);
            List<BestSalesRest> bestSalesRests = bestSales.stream()
                    .map(bestSale -> bestSalesRestMapper.toRest(bestSale))
                    .toList();
            return ResponseEntity.ok().body(bestSalesRests);
        } catch (ServerException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}

