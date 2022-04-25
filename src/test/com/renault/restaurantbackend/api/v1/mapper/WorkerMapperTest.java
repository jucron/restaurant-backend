package com.renault.restaurantbackend.api.v1.mapper;

import com.renault.restaurantbackend.api.v1.model.WorkerDTO;
import com.renault.restaurantbackend.domain.LoginStaff;
import com.renault.restaurantbackend.domain.Worker;
import org.junit.jupiter.api.Test;

import static com.renault.restaurantbackend.domain.enums.WorkerType.COOK;
import static org.junit.jupiter.api.Assertions.assertEquals;

class WorkerMapperTest {
  WorkerMapper cookMapper = com.renault.restaurantbackend.api.v1.mapper.WorkerMapper.INSTANCE;

  @Test
  void cookToCookDTO() {
    //given
    long cookId = 1L;
    String cookName = "cook_name";
    String cookUsername = "cook_username";
    Worker cook = new Worker(); cook.setId(cookId);cook.setName(cookName);cook.setWorkerType(COOK);
    LoginStaff login = new LoginStaff(); login.setUsername(cookUsername);
    cook.setLogin(login);
    //when
    WorkerDTO cookDTO = cookMapper.workerToWorkerDTO(cook);
    //then
    assertEquals(cookId,cookDTO.getId());
    assertEquals(cookName,cookDTO.getName());
    assertEquals(cookUsername,cookDTO.getLoginDTO().getUsername());
    assertEquals(COOK,cookDTO.getWorkerType());
  }
}