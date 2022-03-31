package com.renault.restaurantbackend.api.v1.mapper;

import com.renault.restaurantbackend.api.v1.model.ConsumableDTO;
import com.renault.restaurantbackend.domain.Consumable;
import org.junit.jupiter.api.Test;

import static com.renault.restaurantbackend.domain.enums.ConsumableType.BEVERAGE;
import static com.renault.restaurantbackend.domain.enums.ConsumableType.MEAL;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ConsumableMapperTest {

  ConsumableMapper consumableMapper = ConsumableMapper.INSTANCE;
  @Test
  void consumableToDTO() {
    //given
    String meal_example = "meal_example";
    String beverage_example = "beverage_example";
    double meal_value = 10;
    double beverage_value = 10;
    Consumable meal = new Consumable(); meal.setConsumable(meal_example); meal.setValue(meal_value);
    Consumable beverage = new Consumable(); beverage.setConsumable(beverage_example); beverage.setValue(beverage_value);
    meal.setConsumableType(MEAL); beverage.setConsumableType(BEVERAGE);
    //when
    ConsumableDTO mealDTO = consumableMapper.consumableToDTO(meal);
    ConsumableDTO beverageDTO = consumableMapper.consumableToDTO(beverage);
    //then
    assertEquals(meal_example,mealDTO.getConsumable());
    assertEquals(meal_value,mealDTO.getValue());
    assertEquals(MEAL,mealDTO.getConsumableType());

    assertEquals(beverage_example,beverageDTO.getConsumable());
    assertEquals(beverage_value,beverageDTO.getValue());
    assertEquals(BEVERAGE,beverageDTO.getConsumableType());
  }
}