package school.hei.restaurant.service.mapper;

import school.hei.restaurant.dto.BestProcessingTimeDTO;
import school.hei.restaurant.model.BestProcessingTimes;

public class ProcessingTimeMapper {
    public static BestProcessingTimes fromDTO(BestProcessingTimeDTO dto) {
        BestProcessingTimes entity = new BestProcessingTimes();
        entity.setDishIdentifier(dto.getDishIdentifier());
        entity.setDish(dto.getDishName());
        entity.setQuantity(dto.getQuantity());
        entity.setStartedAt(dto.getStartedAt());
        entity.setFinishedAt(dto.getFinishedAt());
        return entity;
    }
}
