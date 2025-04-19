package school.hei.restaurant.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import school.hei.restaurant.dto.BestProcessingTimeDTO;
import school.hei.restaurant.dto.SalesDTO;

import java.util.List;

@Component
public class ExternalApiClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public List<SalesDTO> fetchSales(String baseUrl) {
        String url = baseUrl + "/sales";
        ResponseEntity<List<SalesDTO>> response = restTemplate.exchange(
                url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<SalesDTO>>() {});
        return response.getBody();
    }

    public List<BestProcessingTimeDTO> fetchPreparations(String baseUrl) {
        String url = baseUrl + "/preparations";
        ResponseEntity<List<BestProcessingTimeDTO>> response = restTemplate.exchange(
                url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<BestProcessingTimeDTO>>() {});
        return response.getBody();
    }
}
