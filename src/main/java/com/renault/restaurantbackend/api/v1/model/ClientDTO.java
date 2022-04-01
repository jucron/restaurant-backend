package com.renault.restaurantbackend.api.v1.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@With
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {
  private Long id;
  private String name;
  private LocalDateTime checkInTime;
  private LocalDateTime checkOutTime;
  private ClientOrderDTO orderDTO;
  private ClientTableDTO tableDTO;
}
