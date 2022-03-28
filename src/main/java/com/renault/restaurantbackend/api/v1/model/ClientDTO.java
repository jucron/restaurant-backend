package com.renault.restaurantbackend.api.v1.model;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ClientDTO {
  private Long id;
  private String name;
  private LocalDateTime checkInTime;
  private LocalDateTime checkOutTime;
  private ClientOrderDTO orderDTO;
  private ClientTableDTO tableDTO;
}
