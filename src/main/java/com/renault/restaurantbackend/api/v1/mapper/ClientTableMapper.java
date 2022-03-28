package com.renault.restaurantbackend.api.v1.mapper;

import com.renault.restaurantbackend.api.v1.model.ClientTableDTO;
import com.renault.restaurantbackend.domain.ClientTable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClientTableMapper {

  ClientTableMapper INSTANCE = Mappers.getMapper(ClientTableMapper.class);

  @Mapping(source = "number", target = "number")
  @Mapping(source = "waiter", target = "waiterDTO")
  ClientTableDTO clientTableToClientTableDTO(ClientTable table);
}
