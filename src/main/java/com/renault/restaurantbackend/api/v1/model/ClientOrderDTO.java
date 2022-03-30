package com.renault.restaurantbackend.api.v1.model;

import com.renault.restaurantbackend.domain.Status;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;

@Data
public class ClientOrderDTO {
  private Long id;
  private LocalDateTime lastUpdated;
  private Status status;
  private CookDTO cookDTO;
  private WaiterDTO waiterDTO;
  private Set<MealDTO> mealDTOS;
  private Set<BeverageDTO> beverageDTOS;
}
