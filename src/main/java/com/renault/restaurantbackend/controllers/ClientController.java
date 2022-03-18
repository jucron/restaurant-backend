package com.renault.restaurantbackend.controllers;

import com.renault.restaurantbackend.api.v1.model.ClientDTO;
import com.renault.restaurantbackend.api.v1.model.ClientListDTO;
import com.renault.restaurantbackend.services.ClientService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ClientController.BASE_URL)
@RequiredArgsConstructor
public class ClientController {
  public static final String BASE_URL = "/api/v1/clients";
  private final ClientService clientService;

  @GetMapping("/get")
  @ResponseStatus(HttpStatus.OK)
  public ClientListDTO getClients() {
    return clientService.getAllClients();
  }

}
