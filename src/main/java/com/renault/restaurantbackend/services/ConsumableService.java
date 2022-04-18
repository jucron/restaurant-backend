package com.renault.restaurantbackend.services;

import com.renault.restaurantbackend.api.v1.model.ConsumableDTO;

public interface ConsumableService {
  ConsumableDTO createConsumable(ConsumableDTO consumableDTO, String menuName);

  void deleteConsumable(String consumableName);
}
