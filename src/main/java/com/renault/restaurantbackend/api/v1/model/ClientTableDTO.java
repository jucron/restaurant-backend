package com.renault.restaurantbackend.api.v1.model;

import com.renault.restaurantbackend.domain.Status;
import com.renault.restaurantbackend.domain.Waiter;
import lombok.Data;

@Data
public class ClientTableDTO {
  private Long id;
  private int number;
  private Status status;
  private Waiter waiter;
}
