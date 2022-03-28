package com.renault.restaurantbackend.controllers;

import com.renault.restaurantbackend.api.v1.model.MealDTO;
import com.renault.restaurantbackend.services.MealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(MealController.BASE_URL)
@RequiredArgsConstructor
public class MealController {
  public static final String BASE_URL = "/api/v1/meals";
  private final MealService mealService;

  @PostMapping({"/{mealName}/{orderId}/order"})
  @ResponseStatus(HttpStatus.OK)
  public MealDTO assignMealToOrder(@PathVariable long orderId, @PathVariable String mealName) {
    return mealService.assignMealToOrder(orderId, mealName);
  }
}
