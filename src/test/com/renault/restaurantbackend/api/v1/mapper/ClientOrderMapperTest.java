package com.renault.restaurantbackend.api.v1.mapper;

import com.renault.restaurantbackend.api.v1.model.ClientOrderDTO;
import com.renault.restaurantbackend.domain.ClientOrder;
import com.renault.restaurantbackend.domain.Status;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientOrderMapperTest {

  ClientOrderMapper clientOrderMapper = ClientOrderMapper.INSTANCE;

  @Test
  void clientOrderToClientOrderDTO() {
    //given
    ClientOrder order = new ClientOrder(); order.setId(1L);
    order.setStatus(Status.OPEN); order.setLastUpdated(LocalDateTime.now());
    //when
    ClientOrderDTO orderDTO = clientOrderMapper.clientOrderToClientOrderDTO(order);
    //then
    assertEquals(order.getId(),orderDTO.getId());
    assertEquals(order.getStatus(),orderDTO.getStatus());
    assertEquals(order.getLastUpdated(),orderDTO.getLastUpdated());
  }
}