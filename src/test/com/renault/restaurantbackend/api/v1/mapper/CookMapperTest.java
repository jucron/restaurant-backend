package com.renault.restaurantbackend.api.v1.mapper;

import com.renault.restaurantbackend.api.v1.model.CookDTO;
import com.renault.restaurantbackend.domain.Cook;
import com.renault.restaurantbackend.domain.LoginStaff;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CookMapperTest {
  CookMapper cookMapper = CookMapper.INSTANCE;

  @Test
  void cookToCookDTO() {
    //given
    long cookId = 1L;
    String cookName = "cook_name";
    String cookUsername = "cook_username";
    Cook cook = new Cook(); cook.setId(cookId);cook.setName(cookName);
    LoginStaff login = new LoginStaff(); login.setUsername(cookUsername);
    cook.setLogin(login);
    //when
    CookDTO cookDTO = cookMapper.CookToCookDTO(cook);
    //then
    assertEquals(cookId,cookDTO.getId());
    assertEquals(cookName,cookDTO.getName());
    assertEquals(cookUsername,cookDTO.getLoginDTO().getUsername());
  }
}