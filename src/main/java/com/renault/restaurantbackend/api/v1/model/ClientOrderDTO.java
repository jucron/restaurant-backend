package com.renault.restaurantbackend.api.v1.model;

import com.renault.restaurantbackend.domain.enums.Status;
import lombok.Data;

@Data
public class ClientOrderDTO {
  private Long id;
  private Status status;
  private CookDTO cookDTO;
  private WaiterDTO waiterDTO;
}
