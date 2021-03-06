package com.renault.restaurantbackend.api.v1.model;

import com.renault.restaurantbackend.domain.enums.Status;
import lombok.Data;

@Data
public class ClientTableDTO {

  private int number;
  private Status status;
  private WorkerDTO waiterDTO;
}
