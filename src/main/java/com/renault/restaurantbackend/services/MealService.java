package com.renault.restaurantbackend.services;

import com.renault.restaurantbackend.api.v1.model.ConsumableDTO;

public interface MealService {
  ConsumableDTO assignMealToOrder(long orderId, String mealName);
}
