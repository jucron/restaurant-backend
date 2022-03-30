package com.renault.restaurantbackend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(CookController.BASE_URL)
@RequiredArgsConstructor
public class CookController {
  public static final String BASE_URL = "/api/v1/cooks";
}
