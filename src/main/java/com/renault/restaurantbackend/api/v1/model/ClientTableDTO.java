package com.renault.restaurantbackend.api.v1.model;

import com.renault.restaurantbackend.domain.Status;
import lombok.Data;

@Data
public class ClientTableDTO {

  private int number;
  private Status status;
  private WaiterDTO waiterDTO;
}
