package com.renault.restaurantbackend.services;

import com.renault.restaurantbackend.api.v1.mapper.ConsumptionMapper;
import com.renault.restaurantbackend.api.v1.model.ConsumptionDTO;
import com.renault.restaurantbackend.controllers.forms.ConsumptionForm;
import com.renault.restaurantbackend.domain.Consumption;
import com.renault.restaurantbackend.repositories.ConsumptionRepository;
import java.time.LocalDateTime;
import java.util.List;
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

  @Override public ConsumptionDTO updateConsumption(ConsumptionForm form) {
    List<Consumption> consumptionList = consumptionRepository.findByOrderId(form.getOrder().getId());
    if (consumptionList.size()==0) {return null;} //If Order not found in repo

    for(Consumption consumption : consumptionList) {
      if (consumption.getConsumable()==form.getConsumable()) {
        consumption.setQuantity(form.getQuantity());
        consumptionRepository.save(consumption);
        return consumptionMapper.consumptionToDTO(consumption);
      }
    }
    return null; //If consumption not found in repo
  }
}
