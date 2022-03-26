package com.renault.restaurantbackend.services;

import com.renault.restaurantbackend.api.v1.model.ClientDTO;
import com.renault.restaurantbackend.api.v1.model.ClientListDTO;
import com.renault.restaurantbackend.api.v1.model.ConsumptionListDTO;
import com.renault.restaurantbackend.api.v1.model.MealDTO;

public interface MealService {

  MealDTO assignMealToOrder(long orderId, String mealName);
}
