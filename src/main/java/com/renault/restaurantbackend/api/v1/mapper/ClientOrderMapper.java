package com.renault.restaurantbackend.api.v1.mapper;

import com.renault.restaurantbackend.api.v1.model.ClientOrderDTO;
import com.renault.restaurantbackend.domain.ClientOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClientOrderMapper {

  ClientOrderMapper INSTANCE = Mappers.getMapper(ClientOrderMapper.class);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "cook", target = "cookDTO")
  @Mapping(source = "waiter", target = "waiterDTO")
  ClientOrderDTO clientOrderToClientOrderDTO(ClientOrder order);
}
