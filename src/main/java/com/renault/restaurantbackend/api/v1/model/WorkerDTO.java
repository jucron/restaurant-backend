package com.renault.restaurantbackend.api.v1.model;

import com.renault.restaurantbackend.domain.enums.WorkerType;
import lombok.Data;

@Data
public class WorkerDTO {
  private Long id;
  private String name;
  private WorkerType workerType;
  private boolean active;
  private LoginStaffDTO loginDTO;
}
