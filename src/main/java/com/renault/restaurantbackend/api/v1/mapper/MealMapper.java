package com.renault.restaurantbackend.api.v1.mapper;

import com.renault.restaurantbackend.api.v1.model.ClientTableDTO;
import com.renault.restaurantbackend.api.v1.model.MealDTO;
import com.renault.restaurantbackend.domain.ClientTable;
import com.renault.restaurantbackend.domain.Meal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MealMapper {

  MealMapper INSTANCE = Mappers.getMapper(MealMapper.class);

  @Mapping(source = "id", target = "id")
  MealDTO MealToMealDTO(Meal meal);

}
