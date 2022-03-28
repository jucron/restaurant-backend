package com.renault.restaurantbackend.api.v1.mapper;

import com.renault.restaurantbackend.api.v1.model.BeverageDTO;
import com.renault.restaurantbackend.domain.Beverage;
import com.renault.restaurantbackend.domain.ClientOrder;
import com.renault.restaurantbackend.domain.Menu;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BeverageMapperTest {
  BeverageMapper beverageMapper = BeverageMapper.INSTANCE;
  @Test
  void beverageToBeverageDTO() {
    //given
    String beverage_example = "beverage_example";
    double beverage_value = 10;
    Beverage beverage = new Beverage(); beverage.setBeverage(beverage_example); beverage.setValue(beverage_value);
    beverage.setOrders(new HashSet<>(Set.of(new ClientOrder())));
    beverage.setMenus(new HashSet<>(Set.of(new Menu())));
    //when
    BeverageDTO beverageDTO = beverageMapper.beverageToBeverageDTO(beverage);
    //then
    assertEquals(beverage_example,beverageDTO.getBeverage());
    assertEquals(beverage_value,beverageDTO.getValue());
    assertEquals(1,beverageDTO.getMenusDTO().size());
    assertEquals(1,beverageDTO.getOrdersDTO().size());
  }
}