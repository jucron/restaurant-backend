package com.renault.restaurantbackend.api.v1.mapper;

import com.renault.restaurantbackend.api.v1.model.ClientDTO;
import com.renault.restaurantbackend.domain.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClientMapper {

  ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

  @Mapping(source = "id", target = "id")
  ClientDTO clientToClientDTO(Client client);

}
