package com.renault.restaurantbackend.api.v1.mapper;

import com.renault.restaurantbackend.api.v1.model.WaiterDTO;
import com.renault.restaurantbackend.domain.Waiter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WaiterMapper {

  WaiterMapper INSTANCE = Mappers.getMapper(WaiterMapper.class);

  @Mapping(source = "login", target = "loginDTO")
  WaiterDTO WaiterToWaiterDTO(Waiter waiter);
}
