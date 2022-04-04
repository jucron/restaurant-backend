package com.renault.restaurantbackend.api.v1.model;

import java.time.LocalDateTime;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@With
@NoArgsConstructor
@AllArgsConstructor
public class MenuDTO {

  private Long id;
  private String name;
  private LocalDateTime lastUpdated;
  private Set<ConsumableDTO> consumableDTOS;
}
