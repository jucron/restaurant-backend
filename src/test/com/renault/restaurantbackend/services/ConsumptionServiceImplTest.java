package com.renault.restaurantbackend.services;

import com.renault.restaurantbackend.api.v1.mapper.ConsumptionMapper;
import com.renault.restaurantbackend.api.v1.model.ConsumptionDTO;
import com.renault.restaurantbackend.controllers.forms.ConsumptionForm;
import com.renault.restaurantbackend.domain.ClientOrder;
import com.renault.restaurantbackend.domain.Consumable;
import com.renault.restaurantbackend.domain.Consumption;
import com.renault.restaurantbackend.repositories.ConsumptionRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class ConsumptionServiceImplTest {

  private ConsumptionService consumptionService;

  @Mock private ConsumptionMapper consumptionMapper;
  @Mock private ConsumptionRepository consumptionRepository;

  @Captor private ArgumentCaptor<Consumption> consumptionArgumentCaptor;

  private final long ORDER_ID = 1L;
  private final int QUANTITY_EXAMPLE = 10;
  private final String CONSUMABLE_EXAMPLE = "Consumable_example";

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    consumptionService = new ConsumptionServiceImpl(consumptionMapper,consumptionRepository);
  }

  @Test
  void createConsumptionByGivingOrderIdAndConsumpForm_ReturnsDTO() {
    //given
    Consumable consumable = new Consumable(); consumable.setConsumable(CONSUMABLE_EXAMPLE);
    ClientOrder order = new ClientOrder(); order.setId(ORDER_ID);
    ConsumptionForm form = new ConsumptionForm()
        .withOrder(order)
        .withQuantity(QUANTITY_EXAMPLE)
        .withConsumable(consumable);

    //when
    ConsumptionDTO consumptionDTO = consumptionService.createConsumption(form);
    //then
    verify(consumptionRepository).save(consumptionArgumentCaptor.capture()); //Consumption is persisted and capturing it to check logic
    verify(consumptionMapper).consumptionToDTO(any(Consumption.class)); //Mapping is used to create DTO
    //check it values were correctly assigned in Consumption object, before persisting:
    Consumption capturedConsumption = consumptionArgumentCaptor.getValue();
    assertEquals(CONSUMABLE_EXAMPLE,capturedConsumption.getConsumable().getConsumable());
    assertEquals(ORDER_ID,capturedConsumption.getOrder().getId());
    assertEquals(QUANTITY_EXAMPLE,capturedConsumption.getQuantity());
  }
  @Test
  void updateAConsumptionQuantityGivenForm_returnsDTO() {
    //given
    Consumable consumable = new Consumable(); consumable.setConsumable(CONSUMABLE_EXAMPLE);
    ClientOrder order = new ClientOrder(); order.setId(ORDER_ID);
    Consumption consumption = new Consumption(); consumption.setQuantity(QUANTITY_EXAMPLE);
    consumption.setOrder(order); consumption.setConsumable(consumable);

    int newQuantity = QUANTITY_EXAMPLE+100;
    ConsumptionForm form = new ConsumptionForm()
        .withOrder(order)
        .withQuantity(newQuantity)
        .withConsumable(consumable);

    given(consumptionRepository.findByOrderId(ORDER_ID)).willReturn(List.of(consumption));

    //when
    ConsumptionDTO consumptionDTO = consumptionService.updateConsumption(form);

    //then
    verify(consumptionRepository).findByOrderId(ORDER_ID); //Fetch list of consumptions by OrderId
    verify(consumptionRepository).save(consumptionArgumentCaptor.capture()); //Consumption is persisted and capturing it to check logic
    verify(consumptionMapper).consumptionToDTO(any(Consumption.class)); //Mapping is used to create DTO
    //check it Quantity was correctly assigned in Consumption object, before persisting:
    Consumption capturedConsumption = consumptionArgumentCaptor.getValue();
    assertNotEquals(QUANTITY_EXAMPLE,capturedConsumption.getQuantity());
    assertEquals(newQuantity,capturedConsumption.getQuantity());
  }
  @Test
  void deleteAConsumptionGivenForm() {
    //given
    Consumable consumable = new Consumable(); consumable.setConsumable(CONSUMABLE_EXAMPLE);
    ClientOrder order = new ClientOrder(); order.setId(ORDER_ID);
    Consumption consumption = new Consumption(); consumption.setQuantity(QUANTITY_EXAMPLE);
    consumption.setOrder(order); consumption.setConsumable(consumable);

    ConsumptionForm form = new ConsumptionForm()
        .withOrder(order)
        .withQuantity(QUANTITY_EXAMPLE)
        .withConsumable(consumable);

    given(consumptionRepository.findByOrderId(ORDER_ID)).willReturn(List.of(consumption));

    //when
    consumptionService.deleteConsumption(form);

    //then
    verify(consumptionRepository).findByOrderId(ORDER_ID); //Fetch list of consumptions by OrderId
    verify(consumptionRepository).delete(consumption); //Delete is called for this consumption
  }
}