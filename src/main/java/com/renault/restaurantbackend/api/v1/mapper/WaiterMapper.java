package com.renault.restaurantbackend.api.v1.mapper;

import com.renault.restaurantbackend.api.v1.model.MenuDTO;
import com.renault.restaurantbackend.api.v1.model.WaiterDTO;
import com.renault.restaurantbackend.domain.Menu;
import com.renault.restaurantbackend.domain.Waiter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WaiterMapper {

  WaiterMapper INSTANCE = Mappers.getMapper(WaiterMapper.class);

  @Mapping(source = "id", target = "id")
  WaiterDTO WaiterToWaiterDTO(Waiter waiter);

}
