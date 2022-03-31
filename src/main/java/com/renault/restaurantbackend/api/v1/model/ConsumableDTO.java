package com.renault.restaurantbackend.api.v1.model;

import com.renault.restaurantbackend.domain.enums.ConsumableType;
import lombok.Data;

@Data
public class ConsumableDTO {

  private String consumable;
  private ConsumableType consumableType;
  private double value;
}
