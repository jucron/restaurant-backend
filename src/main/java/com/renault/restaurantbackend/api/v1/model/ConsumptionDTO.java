package com.renault.restaurantbackend.api.v1.model;

import lombok.Data;

@Data
public class ConsumptionDTO {

  private int quantity;
  private ConsumableDTO consumableDTO;
}
