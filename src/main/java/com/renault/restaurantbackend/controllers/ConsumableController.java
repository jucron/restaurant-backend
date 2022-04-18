package com.renault.restaurantbackend.controllers;

import com.renault.restaurantbackend.api.v1.model.ConsumableDTO;
import com.renault.restaurantbackend.services.ConsumableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ConsumableController.BASE_URL)
@RequiredArgsConstructor
public class ConsumableController {
  public static final String BASE_URL = "/api/v1/consumables";

  private final ConsumableService consumableService;

  @PostMapping({"/{menuName}/create"})
  @ResponseStatus(HttpStatus.CREATED)
  public ConsumableDTO createConsumable(@RequestBody ConsumableDTO consumable,
                                        @PathVariable String menuName) {
    return consumableService.createConsumable(consumable,menuName);
  }
  @DeleteMapping({"/{consumableName}/delete"})
  @ResponseStatus(HttpStatus.FOUND)
  public void deleteConsumable(@PathVariable String consumableName) {
    consumableService.deleteConsumable(consumableName);
  }
}
