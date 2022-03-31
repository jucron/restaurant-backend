package com.renault.restaurantbackend.api.v1.mapper;

import com.renault.restaurantbackend.api.v1.model.ConsumptionDTO;
import com.renault.restaurantbackend.domain.Consumable;
import com.renault.restaurantbackend.domain.Consumption;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ConsumptionMapperTest {

  ConsumptionMapper consumptionMapper = ConsumptionMapper.INSTANCE;
  @Test
  void consumptionToDTO() {
    //given
    int quantity = 5;
    Consumption consumption = new Consumption(); consumption.setConsumable(new Consumable());
    consumption.setQuantity(quantity);
    //when
    ConsumptionDTO consumptionDTO = consumptionMapper.consumptionToDTO(consumption);
    //then+
    assertNotNull(consumptionDTO.getConsumableDTO());
    assertEquals(quantity,consumptionDTO.getQuantity());
  }
}