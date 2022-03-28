package com.renault.restaurantbackend.api.v1.mapper;

import com.renault.restaurantbackend.api.v1.model.MealDTO;
import com.renault.restaurantbackend.domain.Meal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MealMapper {

  MealMapper INSTANCE = Mappers.getMapper(MealMapper.class);

  @Mapping(source = "meal", target = "meal")
  @Mapping(source = "orders", target = "ordersDTO")
  @Mapping(source = "menus", target = "menusDTO")
  MealDTO MealToMealDTO(Meal meal);
}
