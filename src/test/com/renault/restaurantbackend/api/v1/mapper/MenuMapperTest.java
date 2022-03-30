package com.renault.restaurantbackend.api.v1.mapper;

import com.renault.restaurantbackend.api.v1.model.MenuDTO;
import com.renault.restaurantbackend.domain.Beverage;
import com.renault.restaurantbackend.domain.Meal;
import com.renault.restaurantbackend.domain.Menu;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MenuMapperTest {
  MenuMapper menuMapper = MenuMapper.INSTANCE;
  @Test
  void menuToMenuDTO() {
    long menuId = 1L;
    String menuName = "menu_name";
    LocalDateTime lastUpdated = LocalDateTime.now();
    Menu menu = new Menu(); menu.setId(menuId); menu.setName(menuName);
    menu.setLastUpdated(lastUpdated);
    menu.setMeals(new HashSet<>(List.of(new Meal())));
    menu.setBeverages(new HashSet<>(List.of(new Beverage())));
    //when
    MenuDTO menuDTO = menuMapper.MenuToMenuDTO(menu);
    //then
    assertEquals(menuId,menuDTO.getId());
    assertEquals(menuName,menuDTO.getName());
    assertEquals(lastUpdated,menuDTO.getLastUpdated());
    assertEquals(1,menuDTO.getMealDTOS().size());
    assertEquals(1,menuDTO.getBeverageDTOS().size());
  }
}