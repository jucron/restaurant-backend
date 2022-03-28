package com.renault.restaurantbackend.api.v1.mapper;

import com.renault.restaurantbackend.api.v1.model.MealDTO;
import com.renault.restaurantbackend.domain.ClientOrder;
import com.renault.restaurantbackend.domain.Meal;
import com.renault.restaurantbackend.domain.Menu;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MealMapperTest {

  MealMapper mealMapper = MealMapper.INSTANCE;
  @Test
  void mealToMealDTO() {
    //given
    String meal_example = "meal_example";
    double meal_value = 10;
    Meal meal = new Meal(); meal.setMeal(meal_example); meal.setValue(meal_value);
    meal.setOrders(new HashSet<>(Set.of(new ClientOrder()))); meal.setMenus(new HashSet<>(Set.of(new Menu())));
    //when
    MealDTO mealDTO = mealMapper.MealToMealDTO(meal);
    //then
    assertEquals(meal_example,mealDTO.getMeal());
    assertEquals(meal_value,mealDTO.getValue());
    assertEquals(1,mealDTO.getMenusDTO().size());
    assertEquals(1,mealDTO.getOrdersDTO().size());
  }
}