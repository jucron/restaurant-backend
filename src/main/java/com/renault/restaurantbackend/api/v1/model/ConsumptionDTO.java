package com.renault.restaurantbackend.api.v1.model;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ConsumptionDTO {

  private int quantity;
  private LocalDateTime lastUpdated;
  private ConsumableDTO consumableDTO;
  private ClientOrderDTO orderDTO;
}
