package com.renault.restaurantbackend.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ClientController.BASE_URL)
public class ClientController {
  public static final String BASE_URL = "/api/v1/clients";

}
