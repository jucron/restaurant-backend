package com.renault.restaurantbackend.api.v1.mapper;

import com.renault.restaurantbackend.api.v1.model.ClientOrderDTO;
import com.renault.restaurantbackend.domain.ClientOrder;
import com.renault.restaurantbackend.domain.Worker;
import com.renault.restaurantbackend.domain.enums.Status;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ClientOrderMapperTest {

  ClientOrderMapper clientOrderMapper = ClientOrderMapper.INSTANCE;

  @Test
  void clientOrderToClientOrderDTO() {
    //given
    ClientOrder order = new ClientOrder(); order.setId(1L);
    order.setStatus(Status.OPEN);
    order.setCook(new Worker()); order.setWaiter(new Worker());

    //when
    ClientOrderDTO orderDTO = clientOrderMapper.orderToOrderDTO(order);
    //then
    assertEquals(order.getId(),orderDTO.getId());
    assertEquals(order.getStatus(),orderDTO.getStatus());
    assertNotNull(orderDTO.getCookDTO());
    assertNotNull(orderDTO.getWaiterDTO());
  }
}