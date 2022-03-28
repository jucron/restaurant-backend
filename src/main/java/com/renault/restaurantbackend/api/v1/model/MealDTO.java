package com.renault.restaurantbackend.api.v1.model;

import com.renault.restaurantbackend.domain.ClientOrder;
import com.renault.restaurantbackend.domain.ClientTable;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;

@Data
public class MealDTO {

  private String meal;
  private double value;
  private Set<ClientOrderDTO> orderDTOS;
  private Set<MenuDTO> menuDTOS;
}
