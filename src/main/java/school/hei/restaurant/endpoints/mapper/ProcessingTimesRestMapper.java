package school.hei.restaurant.endpoints.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import school.hei.restaurant.endpoints.rest.BestProcessingTimesRest;
import school.hei.restaurant.endpoints.rest.ProcessingTimesRest;
import school.hei.restaurant.model.ProcessingTimes;

@Component
public class ProcessingTimesRestMapper {
    @Autowired
    private BestProcessingTimesRestMapper bestProcessingTimesRestMapper;

    public ProcessingTimesRest toRest(ProcessingTimes processingTimes) {
        BestProcessingTimesRest bestProcessingTimes = bestProcessingTimesRestMapper.apply(processingTimes.getBestProcessingTimes());
        return new ProcessingTimesRest(processingTimes.getUpdatedAt(), bestProcessingTimes);
    }
}
