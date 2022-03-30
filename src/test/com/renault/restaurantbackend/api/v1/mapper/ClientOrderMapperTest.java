package com.renault.restaurantbackend.api.v1.mapper;

import com.renault.restaurantbackend.api.v1.model.ClientOrderDTO;
import com.renault.restaurantbackend.domain.Beverage;
import com.renault.restaurantbackend.domain.ClientOrder;
import com.renault.restaurantbackend.domain.Cook;
import com.renault.restaurantbackend.domain.Meal;
import com.renault.restaurantbackend.domain.Status;
import com.renault.restaurantbackend.domain.Waiter;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ClientOrderMapperTest {

  ClientOrderMapper clientOrderMapper = ClientOrderMapper.INSTANCE;

  @Test
  void clientOrderToClientOrderDTO() {
    //given
    ClientOrder order = new ClientOrder(); order.setId(1L);
    order.setStatus(Status.OPEN); order.setLastUpdated(LocalDateTime.now());
    order.setCook(new Cook()); order.setWaiter(new Waiter());
    order.setMeals(new HashSet<>(List.of(new Meal())));
    order.setBeverages(new HashSet<>(List.of(new Beverage())));
    //when
    ClientOrderDTO orderDTO = clientOrderMapper.clientOrderToClientOrderDTO(order);
    //then
    assertEquals(order.getId(),orderDTO.getId());
    assertEquals(order.getStatus(),orderDTO.getStatus());
    assertEquals(order.getLastUpdated(),orderDTO.getLastUpdated());
    assertNotNull(orderDTO.getCookDTO());
    assertNotNull(orderDTO.getWaiterDTO());
    assertEquals(1,orderDTO.getMealDTOS().size());
    assertEquals(1,orderDTO.getBeverageDTOS().size());
  }
}