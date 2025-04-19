package school.hei.restaurant.endpoints.mapper;

import org.springframework.stereotype.Component;
import school.hei.restaurant.endpoints.rest.BestProcessingTimesRest;
import school.hei.restaurant.model.BestProcessingTimes;

import java.util.function.Function;

@Component
public class BestProcessingTimesRestMapper implements Function<BestProcessingTimes, BestProcessingTimesRest> {

    @Override
    public BestProcessingTimesRest apply(BestProcessingTimes bestProcessingTimes) {
        return new BestProcessingTimesRest(bestProcessingTimes.getSalesPoint(),
                bestProcessingTimes.getDish(),
                bestProcessingTimes.getPreparationDuration(),
                bestProcessingTimes.getDurationUnit());
    }
}
