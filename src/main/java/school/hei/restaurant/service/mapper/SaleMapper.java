package school.hei.restaurant.service.mapper;

import school.hei.restaurant.dto.SalesDTO;
import school.hei.restaurant.model.Sales;

public class SaleMapper {
    public static Sales fromDTO(SalesDTO dto) {
        Sales entity = new Sales();
        entity.setDishIdentifier(dto.getDishIdentifier());
        entity.setDish(dto.getDishName());
        entity.setQuantitySold(dto.getQuantitySold());
        entity.setUnitPrice(dto.getUnitPrice());
        return entity;
    }
}