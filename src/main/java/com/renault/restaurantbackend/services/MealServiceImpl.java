package com.renault.restaurantbackend.services;

import com.renault.restaurantbackend.api.v1.model.ConsumableDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MealServiceImpl implements MealService {


  @Override
  public ConsumableDTO assignMealToOrder(long orderId, String mealName) {
    return null;
  }
}
