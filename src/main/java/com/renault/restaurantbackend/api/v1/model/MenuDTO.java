package com.renault.restaurantbackend.api.v1.model;

import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;

@Data
public class MenuDTO {

  private Long id;
  private String name;
  private LocalDateTime lastUpdated;
  private Set<MealDTO> mealDTOS;
  private Set<BeverageDTO> beverageDTOS;
}
