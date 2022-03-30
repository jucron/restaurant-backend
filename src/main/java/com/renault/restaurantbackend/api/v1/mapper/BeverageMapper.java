package com.renault.restaurantbackend.api.v1.mapper;

import com.renault.restaurantbackend.api.v1.model.BeverageDTO;
import com.renault.restaurantbackend.domain.Beverage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BeverageMapper {

  BeverageMapper INSTANCE = Mappers.getMapper(BeverageMapper.class);

  @Mapping(source = "beverage", target = "beverage")
  BeverageDTO beverageToBeverageDTO(Beverage beverage);

}
