package com.renault.restaurantbackend.api.v1.mapper;

import com.renault.restaurantbackend.api.v1.model.MenuDTO;
import com.renault.restaurantbackend.domain.Menu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MenuMapper {

  MenuMapper INSTANCE = Mappers.getMapper(MenuMapper.class);

  @Mapping(source = "meals", target = "mealDTOS")
  @Mapping(source = "beverages", target = "beverageDTOS")
  MenuDTO MenuToMenuDTO(Menu menu);

}
