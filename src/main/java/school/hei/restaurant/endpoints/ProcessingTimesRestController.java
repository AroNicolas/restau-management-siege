package school.hei.restaurant.endpoints;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import school.hei.restaurant.endpoints.mapper.ProcessingTimesRestMapper;
import school.hei.restaurant.endpoints.rest.ProcessingTimesRest;
import school.hei.restaurant.model.ProcessingTimes;
import school.hei.restaurant.service.ProcessingTimesService;
import school.hei.restaurant.service.exception.ServerException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProcessingTimesRestController {
    private final ProcessingTimesService processingTimesService;
    private final ProcessingTimesRestMapper processingTimesRestMapper;

    @GetMapping("/dishes/{id}/bestProcessingTime")
    public ResponseEntity<Object> getProcessingTime(@PathVariable Long id,
                                                    @RequestParam(required = false) int top,
                                                    @RequestParam(required = false, defaultValue = "SECONDS") String durationUnit,
                                                    @RequestParam(required = false, defaultValue = "AVERAGE") String calculationMode) {
        try {
            List<ProcessingTimes> processingTimes = processingTimesService.getProcessingTime(id, top, durationUnit, calculationMode);
            List<ProcessingTimesRest> processingTimesRests = processingTimes.stream()
                    .map(processingTime -> processingTimesRestMapper.toRest(processingTime))
                    .toList();
            return ResponseEntity.ok().body(processingTimesRests);
        } catch (ServerException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
