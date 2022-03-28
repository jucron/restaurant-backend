package com.renault.restaurantbackend.api.v1.mapper;

import com.renault.restaurantbackend.api.v1.model.WaiterDTO;
import com.renault.restaurantbackend.domain.LoginStaff;
import com.renault.restaurantbackend.domain.Waiter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WaiterMapperTest {
  WaiterMapper waiterMapper = WaiterMapper.INSTANCE;
  @Test
  void waiterToWaiterDTO() {
    //given
    long waiterId = 1L;
    String waiterName = "waiter_name";
    String waiterUsername = "waiter_username";
    Waiter waiter = new Waiter(); waiter.setId(waiterId);waiter.setName(waiterName);
    LoginStaff login = new LoginStaff(); login.setUsername(waiterUsername);
    waiter.setLogin(login);
    //when
    WaiterDTO waiterDTO = waiterMapper.WaiterToWaiterDTO(waiter);
    //then
    assertEquals(waiterId,waiterDTO.getId());
    assertEquals(waiterName,waiterDTO.getName());
    assertEquals(waiterUsername,waiterDTO.getLoginDTO().getUsername());

  }
}