package com.renault.restaurantbackend.api.v1.model;

import com.renault.restaurantbackend.domain.enums.WorkerType;
import lombok.Data;

@Data
public class LoginStaffDTO {
  private Long id;
  private String username;
  private WorkerType workerType;
}
