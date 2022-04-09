package com.renault.restaurantbackend.controllers.forms;

import com.renault.restaurantbackend.domain.ClientOrder;
import com.renault.restaurantbackend.domain.Consumable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@AllArgsConstructor
@NoArgsConstructor
@With
public class ConsumptionForm {
  int quantity;
  ClientOrder order;
  Consumable consumable;
}
