package com.renault.restaurantbackend.services;

import com.renault.restaurantbackend.api.v1.mapper.ConsumptionMapper;
import com.renault.restaurantbackend.api.v1.model.ConsumptionDTO;
import com.renault.restaurantbackend.controllers.forms.ConsumptionForm;
import com.renault.restaurantbackend.domain.Consumption;
import com.renault.restaurantbackend.repositories.ConsumptionRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsumptionServiceImpl implements ConsumptionService {

  private final ConsumptionMapper consumptionMapper;
  private final ConsumptionRepository consumptionRepository;

  @Override public ConsumptionDTO createConsumption(ConsumptionForm form) {
    //create Consumption, set fields based on Form, and persist it
    Consumption newConsumption = new Consumption(); newConsumption.setConsumable(form.getConsumable());
    newConsumption.setQuantity(form.getQuantity()); newConsumption.setOrder(form.getOrder());
    newConsumption.setLastUpdated(LocalDateTime.now());
    consumptionRepository.save(newConsumption);
    return consumptionMapper.consumptionToDTO(newConsumption);
  }
}
