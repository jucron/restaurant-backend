package com.renault.restaurantbackend.api.v1.mapper;

import com.renault.restaurantbackend.api.v1.model.ConsumableDTO;
import com.renault.restaurantbackend.domain.Consumable;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ConsumableMapper {

  ConsumableMapper INSTANCE = Mappers.getMapper(ConsumableMapper.class);

  ConsumableDTO consumableToDTO(Consumable consumable);
}
