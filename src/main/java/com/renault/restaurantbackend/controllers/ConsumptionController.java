package com.renault.restaurantbackend.controllers;

import com.renault.restaurantbackend.api.v1.model.ConsumptionDTO;
import com.renault.restaurantbackend.api.v1.model.lists.ConsumptionListDTO;
import com.renault.restaurantbackend.controllers.forms.ConsumptionForm;
import com.renault.restaurantbackend.services.ConsumptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ConsumptionController.BASE_URL)
@RequiredArgsConstructor
public class ConsumptionController {
  public static final String BASE_URL = "/api/v1/consumptions";

  private final ConsumptionService consumptionService;

  @PostMapping({"/create"})
  @ResponseStatus(HttpStatus.CREATED)
  public ConsumptionDTO createConsumption(@RequestBody ConsumptionForm form) {
    return consumptionService.createConsumption(form);
  }
  @PutMapping({"/update"})
  @ResponseStatus(HttpStatus.OK)
  public ConsumptionDTO updateConsumption(@RequestBody ConsumptionForm form) {
    return consumptionService.updateConsumption(form);
  }
  @DeleteMapping({"/delete"})
  @ResponseStatus(HttpStatus.FOUND)
  public void deleteConsumption(@RequestBody ConsumptionForm form) {
    consumptionService.deleteConsumption(form);
  }
  @GetMapping({"/{orderId}/list"})
  @ResponseStatus(HttpStatus.OK)
  public ConsumptionListDTO getListOfConsumption(@PathVariable long orderId) {
    return consumptionService.getListConsumption(orderId);
  }
}
