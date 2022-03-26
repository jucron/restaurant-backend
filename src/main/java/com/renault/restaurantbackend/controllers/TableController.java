package com.renault.restaurantbackend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(TableController.BASE_URL)
@RequiredArgsConstructor
public class TableController {
  public static final String BASE_URL = "/api/v1/tables";
}
