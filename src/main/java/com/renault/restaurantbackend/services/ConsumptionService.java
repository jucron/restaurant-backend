package com.renault.restaurantbackend.services;

import com.renault.restaurantbackend.api.v1.model.ConsumptionDTO;
import com.renault.restaurantbackend.api.v1.model.lists.ConsumptionListDTO;
import com.renault.restaurantbackend.controllers.forms.ConsumptionForm;

public interface ConsumptionService {
  ConsumptionDTO createConsumption(ConsumptionForm form);

  ConsumptionDTO updateConsumption(ConsumptionForm form);

  void deleteConsumption(ConsumptionForm form);

  ConsumptionListDTO getListConsumption(long orderId);
}
