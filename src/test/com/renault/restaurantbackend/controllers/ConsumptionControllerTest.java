package com.renault.restaurantbackend.controllers;

import com.renault.restaurantbackend.api.v1.mapper.AbstractRestControllerTest;
import com.renault.restaurantbackend.api.v1.model.ClientOrderDTO;
import com.renault.restaurantbackend.api.v1.model.ConsumableDTO;
import com.renault.restaurantbackend.api.v1.model.ConsumptionDTO;
import com.renault.restaurantbackend.api.v1.model.lists.ConsumptionListDTO;
import com.renault.restaurantbackend.controllers.forms.ConsumptionForm;
import com.renault.restaurantbackend.domain.ClientOrder;
import com.renault.restaurantbackend.domain.Consumable;
import com.renault.restaurantbackend.services.ConsumptionService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.renault.restaurantbackend.domain.enums.ConsumableType.BEVERAGE;
import static com.renault.restaurantbackend.domain.enums.ConsumableType.MEAL;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ConsumptionControllerTest extends AbstractRestControllerTest {
  /* Expected
   * OK: Create a Consumption for a Client's Order with quantity and a Consumable
   * OK: Update an existing Consumption's quantity
   * OK: Delete an existing Consumption
   * OK: Fetch Client's Consumption List and total value
   */
  @InjectMocks
  ConsumptionController consumptionController;
  @Mock ConsumptionService consumptionService;

  MockMvc mockMvc;

  private final String BASE_URL = ConsumptionController.BASE_URL;
  private final long ORDER_ID = 1L;
  private final int QUANTITY_EXAMPLE = 10;
  private final String CONSUMABLE_EXAMPLE_1 = "Consumable_example_1";
  private final String CONSUMABLE_EXAMPLE_2 = "Consumable_example_2";

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(consumptionController)
        .build();
  }
  @Test
  void createConsumptionGivenOrderIdAndQuantityAndConsumable_returnsDTO() throws Exception {
    //given - Form to be delivered and DTOs to be returned when method is called
    Consumable consumable = new Consumable(); consumable.setConsumable(CONSUMABLE_EXAMPLE_1);
    ClientOrder order = new ClientOrder(); order.setId(ORDER_ID);

    ConsumptionForm form = new ConsumptionForm()
        .withOrder(order)
        .withQuantity(QUANTITY_EXAMPLE)
        .withConsumable(consumable);
    //DTOs
    ConsumableDTO consumableDTO = new ConsumableDTO(); consumableDTO.setConsumable(consumable.getConsumable());
    ConsumptionDTO consumptionDTO = new ConsumptionDTO(); consumptionDTO.setQuantity(QUANTITY_EXAMPLE);
    ClientOrderDTO orderDTO = new ClientOrderDTO(); orderDTO.setId(order.getId());
    consumptionDTO.setConsumableDTO(consumableDTO); consumptionDTO.setOrderDTO(orderDTO);
    //Method call
    given(consumptionService.createConsumption(form)).willReturn(consumptionDTO);

    //when and then
    mockMvc.perform(post(BASE_URL + "/create")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(form)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.quantity", equalTo(QUANTITY_EXAMPLE)))
        .andExpect(jsonPath("$.consumableDTO.consumable", equalTo(CONSUMABLE_EXAMPLE_1)))
        .andExpect(jsonPath("$.orderDTO.id", equalTo((int)ORDER_ID)));
  }
  @Test
  void updateAConsumptionQuantity_returnsDTO() throws Exception {
    //given - Form to be delivered and DTOs to be returned when method is called
    Consumable consumable = new Consumable(); consumable.setConsumable(CONSUMABLE_EXAMPLE_1);
    ClientOrder order = new ClientOrder(); order.setId(ORDER_ID);

    int newQuantity = QUANTITY_EXAMPLE+1;
    ConsumptionForm form = new ConsumptionForm()
        .withOrder(order)
        .withQuantity(newQuantity)
        .withConsumable(consumable);
    //DTOs
    ConsumableDTO consumableDTO = new ConsumableDTO(); consumableDTO.setConsumable(consumable.getConsumable());
    ConsumptionDTO consumptionDTO = new ConsumptionDTO(); consumptionDTO.setQuantity(newQuantity);
    ClientOrderDTO orderDTO = new ClientOrderDTO(); orderDTO.setId(order.getId());
    consumptionDTO.setConsumableDTO(consumableDTO); consumptionDTO.setOrderDTO(orderDTO);

    //Method call
    given(consumptionService.updateConsumption(form)).willReturn(consumptionDTO);

    //when and then
    mockMvc.perform(put(BASE_URL + "/update")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(form)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.quantity", equalTo(newQuantity)))
        .andExpect(jsonPath("$.consumableDTO.consumable", equalTo(CONSUMABLE_EXAMPLE_1)))
        .andExpect(jsonPath("$.orderDTO.id", equalTo((int)ORDER_ID)));
  }
  @Test
  void deleteAConsumption() throws Exception {
    //given - Form to be delivered and check if Consumable exists
    Consumable consumable = new Consumable(); consumable.setConsumable(CONSUMABLE_EXAMPLE_1);
    ClientOrder order = new ClientOrder(); order.setId(ORDER_ID);

    ConsumptionForm form = new ConsumptionForm()
        .withOrder(order)
        .withQuantity(QUANTITY_EXAMPLE)
        .withConsumable(consumable);

    //when and then
    mockMvc.perform(delete(BASE_URL + "/delete")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(form)))
        .andExpect(status().isFound())
        ;
    verify(consumptionService).deleteConsumption(form);
  }
  @Test
  void getListOfConsumptionDTO() throws Exception {
    //given
    double consumableValue1 = 10; double consumableValue2 = 15;
    int quantity1 = 5; int quantity2 = 10;
    double totalCost = (quantity1*consumableValue1)+(quantity2*consumableValue2);

    ClientOrderDTO orderDTO = new ClientOrderDTO(); orderDTO.setId(ORDER_ID);
    ConsumableDTO consumableDTO1 = new ConsumableDTO(); consumableDTO1.setConsumable(CONSUMABLE_EXAMPLE_1);
    consumableDTO1.setConsumableType(MEAL); consumableDTO1.setValue(consumableValue1);
    ConsumableDTO consumableDTO2 = new ConsumableDTO(); consumableDTO2.setConsumable(CONSUMABLE_EXAMPLE_2);
    consumableDTO2.setConsumableType(BEVERAGE); consumableDTO2.setValue(consumableValue2);
    ConsumptionDTO consumptionDTO1 = new ConsumptionDTO(); consumptionDTO1.setOrderDTO(orderDTO);
    consumptionDTO1.setQuantity(quantity1); consumptionDTO1.setConsumableDTO(consumableDTO1);
    ConsumptionDTO consumptionDTO2 = new ConsumptionDTO(); consumptionDTO2.setOrderDTO(orderDTO);
    consumptionDTO2.setQuantity(quantity2); consumptionDTO2.setConsumableDTO(consumableDTO2);

    ConsumptionListDTO consumptionListDTO = new ConsumptionListDTO()
        .withConsumptionDTOS(List.of(consumptionDTO1,consumptionDTO2))
        .withTotalCost(totalCost);

    given(consumptionService.getListConsumption(ORDER_ID)).willReturn(consumptionListDTO);

    //when and then
    mockMvc.perform(get(BASE_URL +"/"+ORDER_ID+"/list")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.consumptionDTOS[0].orderDTO.id", equalTo( (int) ORDER_ID)))
        .andExpect(jsonPath("$.consumptionDTOS[0].quantity", equalTo( quantity1)))
        .andExpect(jsonPath("$.consumptionDTOS[1].quantity", equalTo( quantity2)))
        .andExpect(jsonPath("$.consumptionDTOS", hasSize(2)))
        .andExpect(jsonPath("$.consumptionDTOS[0].consumableDTO.consumableType", equalTo(MEAL.toString())))
        .andExpect(jsonPath("$.totalCost", equalTo(totalCost)))
    ;
  }

}