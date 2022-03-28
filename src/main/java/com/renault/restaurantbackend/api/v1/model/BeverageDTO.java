package com.renault.restaurantbackend.api.v1.model;

import com.renault.restaurantbackend.domain.ClientOrder;
import java.util.Set;
import lombok.Data;

@Data
public class BeverageDTO {

  private String beverage;
  private double value;
  private Set<ClientOrderDTO> orderDTOS;
  private Set<MenuDTO> menuDTOS;
}
