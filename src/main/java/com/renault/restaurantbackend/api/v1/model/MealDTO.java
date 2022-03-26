package com.renault.restaurantbackend.api.v1.model;

import com.renault.restaurantbackend.domain.ClientOrder;
import com.renault.restaurantbackend.domain.ClientTable;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class MealDTO {
  private Long id;
  private String meal;
  private double value;
}
