package com.renault.restaurantbackend.controllers;

import com.renault.restaurantbackend.api.v1.model.ClientDTO;
import com.renault.restaurantbackend.api.v1.model.ClientListDTO;
import com.renault.restaurantbackend.controllers.forms.ClientNameAndTableNumberForm;
import com.renault.restaurantbackend.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ClientController.BASE_URL)
@RequiredArgsConstructor
public class ClientController {
  public static final String BASE_URL = "/api/v1/clients";
  private final ClientService clientService;

  @GetMapping("/")
  @ResponseStatus(HttpStatus.OK)
  public ClientListDTO getClients() {
    return clientService.getAllClients();
  }
  @PostMapping({"/create/{name}/{tableNumber}"})
  @ResponseStatus(HttpStatus.CREATED)
  public ClientDTO createClients(@PathVariable String name, @PathVariable int tableNumber) {
    return clientService.createClient(name, tableNumber);
  }
  @PutMapping({"/checkout"})
  @ResponseStatus(HttpStatus.OK)
  public ClientDTO checkoutClients(@RequestBody ClientNameAndTableNumberForm form) {
    return clientService.checkoutClient(form.getName(),form.getTableNumber());
  }
  //@GetMapping({"/consumption"})
  //@ResponseStatus(HttpStatus.OK)
  //public ConsumptionListDTO getConsumptionList(@RequestBody ClientNameAndTableNumberForm form) {
  //  return clientService.getListOfConsumption(form.getName(),form.getTableNumber());
  //}

}
