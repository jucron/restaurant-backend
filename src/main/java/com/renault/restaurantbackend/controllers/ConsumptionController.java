package com.renault.restaurantbackend.controllers;

import com.renault.restaurantbackend.api.v1.model.ConsumptionDTO;
import com.renault.restaurantbackend.controllers.forms.ConsumptionForm;
import com.renault.restaurantbackend.services.ConsumptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ConsumptionController.BASE_URL)
@RequiredArgsConstructor
public class ConsumptionController {
  public static final String BASE_URL = "/api/v1/consumptions";

  ConsumptionService consumptionService;

  @PostMapping({"/create"})
  @ResponseStatus(HttpStatus.CREATED)
  public ConsumptionDTO createClients(@RequestBody ConsumptionForm form) {
    return consumptionService.createConsumption(form);
  }
}
