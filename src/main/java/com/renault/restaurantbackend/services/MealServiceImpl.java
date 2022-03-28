package com.renault.restaurantbackend.services;

import com.renault.restaurantbackend.api.v1.model.MealDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MealServiceImpl implements MealService {


  @Override
  public MealDTO assignMealToOrder(long orderId, String mealName) {
    return null;
  }
}
