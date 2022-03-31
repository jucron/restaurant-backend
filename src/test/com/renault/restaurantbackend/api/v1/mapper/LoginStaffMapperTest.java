package com.renault.restaurantbackend.api.v1.mapper;

import com.renault.restaurantbackend.api.v1.model.LoginStaffDTO;
import com.renault.restaurantbackend.domain.LoginStaff;
import com.renault.restaurantbackend.domain.enums.WorkerType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoginStaffMapperTest {
    LoginStaffMapper loginStaffMapper = LoginStaffMapper.INSTANCE;
  @Test
  void loginToLoginDTO() {
    //given
    String username = "username";
    String password = "password";
    long id = 1L;
    WorkerType workerType = WorkerType.COOK;
    LoginStaff login = new LoginStaff(); login.setUsername(username);
    login.setId(id);login.setPassword(password); login.setWorkerType(workerType);
    //when
    LoginStaffDTO loginDTO = loginStaffMapper.LoginToLoginDTO(login);
    //then
    assertEquals(id,loginDTO.getId());
    assertEquals(username,loginDTO.getUsername());
    assertEquals(workerType,loginDTO.getWorkerType());
  }
}