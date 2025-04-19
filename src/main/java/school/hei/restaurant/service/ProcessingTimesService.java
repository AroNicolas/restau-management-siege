package school.hei.restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.restaurant.dao.operation.ProcessingTimesCrudOperations;
import school.hei.restaurant.model.ProcessingTimes;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProcessingTimesService {
    private final ProcessingTimesCrudOperations processingTimesCrudOperations;

    public List<ProcessingTimes> getProcessingTime(Long id, int top, String durationUnit, String calculationMode) {
        return processingTimesCrudOperations.getBestsProcessingTimes(id, top, durationUnit, calculationMode);
    }
}
