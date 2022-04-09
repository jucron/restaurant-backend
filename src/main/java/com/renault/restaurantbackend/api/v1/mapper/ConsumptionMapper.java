package com.renault.restaurantbackend.api.v1.mapper;

import com.renault.restaurantbackend.api.v1.model.ConsumptionDTO;
import com.renault.restaurantbackend.domain.Consumption;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ConsumptionMapper {

  ConsumptionMapper INSTANCE = Mappers.getMapper(ConsumptionMapper.class);

  @Mapping(source = "consumable", target = "consumableDTO")
  @Mapping(source = "order", target = "orderDTO")
  ConsumptionDTO consumptionToDTO(Consumption consumption);
}
