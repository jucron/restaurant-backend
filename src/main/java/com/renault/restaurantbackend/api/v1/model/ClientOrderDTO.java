package com.renault.restaurantbackend.api.v1.model;

import com.renault.restaurantbackend.domain.enums.Status;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ClientOrderDTO {
  private Long id;
  private Status status;
  private LocalDateTime lastUpdated;
  private ConsumptionDTO consumptionDTO;
  private CookDTO cookDTO;
  private WaiterDTO waiterDTO;
}
