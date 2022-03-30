package com.renault.restaurantbackend.api.v1.mapper;

import com.renault.restaurantbackend.api.v1.model.CookDTO;
import com.renault.restaurantbackend.domain.Cook;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CookMapper {

  CookMapper INSTANCE = Mappers.getMapper(CookMapper.class);

  @Mapping(source = "login", target = "loginDTO")
  CookDTO CookToCookDTO(Cook cook);
}
