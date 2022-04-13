package com.renault.restaurantbackend.services;

import com.renault.restaurantbackend.api.v1.mapper.ConsumptionMapper;
import com.renault.restaurantbackend.api.v1.model.ConsumptionDTO;
import com.renault.restaurantbackend.api.v1.model.lists.ConsumptionListDTO;
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

import static com.renault.restaurantbackend.domain.enums.ConsumableType.BEVERAGE;
import static com.renault.restaurantbackend.domain.enums.ConsumableType.MEAL;
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
  private final int QUANTITY_EXAMPLE_1 = 10;
  private final int QUANTITY_EXAMPLE_2 = 15;
  private final String CONSUMABLE_EXAMPLE_1 = "Consumable_example_1";
  private final String CONSUMABLE_EXAMPLE_2 = "Consumable_example_2";

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    consumptionService = new ConsumptionServiceImpl(consumptionMapper,consumptionRepository);
  }

  @Test
  void createConsumptionByGivingOrderIdAndConsumpForm_ReturnsDTO() {
    //given
    Consumable consumable = new Consumable(); consumable.setConsumable(CONSUMABLE_EXAMPLE_1);
    ClientOrder order = new ClientOrder(); order.setId(ORDER_ID);
    ConsumptionForm form = new ConsumptionForm()
        .withOrder(order)
        .withQuantity(QUANTITY_EXAMPLE_1)
        .withConsumable(consumable);

    //when
    ConsumptionDTO consumptionDTO = consumptionService.createConsumption(form);
    //then
    verify(consumptionRepository).save(consumptionArgumentCaptor.capture()); //Consumption is persisted and capturing it to check logic
    verify(consumptionMapper).consumptionToDTO(any(Consumption.class)); //Mapping is used to create DTO
    //check it values were correctly assigned in Consumption object, before persisting:
    Consumption capturedConsumption = consumptionArgumentCaptor.getValue();
    assertEquals(CONSUMABLE_EXAMPLE_1,capturedConsumption.getConsumable().getConsumable());
    assertEquals(ORDER_ID,capturedConsumption.getOrder().getId());
    assertEquals(QUANTITY_EXAMPLE_1,capturedConsumption.getQuantity());
  }
  @Test
  void updateAConsumptionQuantityGivenForm_returnsDTO() {
    //given
    Consumable consumable = new Consumable(); consumable.setConsumable(CONSUMABLE_EXAMPLE_1);
    ClientOrder order = new ClientOrder(); order.setId(ORDER_ID);
    Consumption consumption = new Consumption(); consumption.setQuantity(QUANTITY_EXAMPLE_1);
    consumption.setOrder(order); consumption.setConsumable(consumable);

    int newQuantity = QUANTITY_EXAMPLE_1 +100;
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
    assertNotEquals(QUANTITY_EXAMPLE_1,capturedConsumption.getQuantity());
    assertEquals(newQuantity,capturedConsumption.getQuantity());
  }
  @Test
  void deleteAConsumptionGivenForm() {
    //given
    Consumable consumable = new Consumable(); consumable.setConsumable(CONSUMABLE_EXAMPLE_1);
    ClientOrder order = new ClientOrder(); order.setId(ORDER_ID);
    Consumption consumption = new Consumption(); consumption.setQuantity(QUANTITY_EXAMPLE_1);
    consumption.setOrder(order); consumption.setConsumable(consumable);

    ConsumptionForm form = new ConsumptionForm()
        .withOrder(order)
        .withQuantity(QUANTITY_EXAMPLE_1)
        .withConsumable(consumable);

    given(consumptionRepository.findByOrderId(ORDER_ID)).willReturn(List.of(consumption));

    //when
    consumptionService.deleteConsumption(form);

    //then
    verify(consumptionRepository).findByOrderId(ORDER_ID); //Fetch list of consumptions by OrderId
    verify(consumptionRepository).delete(consumption); //Delete is called for this consumption
  }
  @Test
  void getListOfConsumptionsGivenOrderId() {
    //given
    double consumableValue1 = 10; double consumableValue2 = 15;
    double totalCost = (QUANTITY_EXAMPLE_1*consumableValue1)+(QUANTITY_EXAMPLE_2*consumableValue2);

    Consumable consumable1 = new Consumable(); consumable1.setConsumable(CONSUMABLE_EXAMPLE_1);
    consumable1.setConsumableType(MEAL); consumable1.setValue(consumableValue1);
    Consumable consumable2 = new Consumable(); consumable2.setConsumable(CONSUMABLE_EXAMPLE_2);
    consumable2.setConsumableType(BEVERAGE); consumable2.setValue(consumableValue2);

    ClientOrder order = new ClientOrder(); order.setId(ORDER_ID);
    Consumption consumption1 = new Consumption(); consumption1.setQuantity(QUANTITY_EXAMPLE_1);
    consumption1.setOrder(order); consumption1.setConsumable(consumable1);
    Consumption consumption2 = new Consumption(); consumption2.setQuantity(QUANTITY_EXAMPLE_2);
    consumption2.setOrder(order); consumption2.setConsumable(consumable2);

    List<Consumption> consumptionList = List.of(consumption1,consumption2);
    given(consumptionRepository.findByOrderId(ORDER_ID)).willReturn(consumptionList);
    given(consumptionMapper.consumptionToDTO(any(Consumption.class))).willReturn(new ConsumptionDTO());

    //when
    ConsumptionListDTO consumptionListDTO = consumptionService.getListConsumption(ORDER_ID);

    //then
    verify(consumptionRepository).findByOrderId(ORDER_ID); //Fetch list of consumption by OrderId
    verify(consumptionMapper).consumptionToDTO(consumption1); //Mapping consumption1 to DTO
    verify(consumptionMapper).consumptionToDTO(consumption2); //Mapping consumption2 to DTO

    assertEquals(2,consumptionListDTO.getConsumptionDTOS().size()); //Two consumptions in the List
    assertEquals(totalCost,consumptionListDTO.getTotalCost()); //Total cost matches what we calculated previously
  }
}