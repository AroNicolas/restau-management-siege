package school.hei.restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.restaurant.client.ExternalApiClient;
import school.hei.restaurant.dao.operation.ProcessingTimesCrudOperations;
import school.hei.restaurant.dao.operation.SalesCrudOperations;
import school.hei.restaurant.dto.BestProcessingTimeDTO;
import school.hei.restaurant.dto.SalesDTO;
import school.hei.restaurant.model.BestProcessingTimes;
import school.hei.restaurant.model.Sales;
import school.hei.restaurant.service.mapper.ProcessingTimeMapper;
import school.hei.restaurant.service.mapper.SaleMapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SynchronizationService {

    private final ExternalApiClient apiClient;
    private final SalesCrudOperations salesCrudOperations;
    private final ProcessingTimesCrudOperations processingTimesCrudOperations;

    private final Map<String, String> salesPoints = Map.of(
            "http://192.168.43.249:8081", "Analamahintsy",
            "http://192.168.43.249:8082", "Antanimena"
    );

    public void synchronizeData() {
        for (var entry : salesPoints.entrySet()) {
            String baseUrl = entry.getKey();
            String name = entry.getValue();

            List<SalesDTO> sales = apiClient.fetchSales(baseUrl);
            List<Sales> saleEntities = sales.stream()
                    .map(s -> {
                        s.setSalesPoint(name);
                        return SaleMapper.fromDTO(s);
                    })
                    .collect(Collectors.toList());
            salesCrudOperations.saveAll(saleEntities);

            List<BestProcessingTimeDTO> preps = apiClient.fetchPreparations(baseUrl);
            List<BestProcessingTimes> prepEntities = preps.stream()
                    .map(s -> {
                        s.setSalesPoint(name);
                        return ProcessingTimeMapper.fromDTO(s);
                    })
                    .collect(Collectors.toList());
            processingTimesCrudOperations.saveAll(prepEntities);
        }
    }
}
