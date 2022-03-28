package com.renault.restaurantbackend.api.v1.mapper;

import com.renault.restaurantbackend.api.v1.model.BeverageDTO;
import com.renault.restaurantbackend.api.v1.model.MealDTO;
import com.renault.restaurantbackend.domain.Beverage;
import com.renault.restaurantbackend.domain.Meal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BeverageMapper {

  BeverageMapper INSTANCE = Mappers.getMapper(BeverageMapper.class);

  @Mapping(source = "id", target = "id")
  BeverageDTO BeverageToBeverageDTO(Beverage beverage);

}
