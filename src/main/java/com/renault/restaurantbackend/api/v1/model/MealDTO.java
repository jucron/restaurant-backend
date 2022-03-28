package com.renault.restaurantbackend.api.v1.model;

import java.util.Set;
import lombok.Data;

@Data
public class MealDTO {

  private String meal;
  private double value;
  private Set<ClientOrderDTO> ordersDTO;
  private Set<MenuDTO> menusDTO;
}
