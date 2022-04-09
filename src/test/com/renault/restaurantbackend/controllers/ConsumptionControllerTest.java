package com.renault.restaurantbackend.controllers;

import com.renault.restaurantbackend.api.v1.mapper.AbstractRestControllerTest;
import com.renault.restaurantbackend.api.v1.model.ClientOrderDTO;
import com.renault.restaurantbackend.api.v1.model.ConsumableDTO;
import com.renault.restaurantbackend.api.v1.model.ConsumptionDTO;
import com.renault.restaurantbackend.controllers.forms.ConsumptionForm;
import com.renault.restaurantbackend.domain.ClientOrder;
import com.renault.restaurantbackend.domain.Consumable;
import com.renault.restaurantbackend.services.ConsumptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ConsumptionControllerTest extends AbstractRestControllerTest {
  /* Expected
   * Create a Consumption for a Client's Order with quantity, Consumable
   * Delete a Consumption previously created
   * Fetch Client's Consumption List and total value
   */
  @InjectMocks
  ConsumptionController consumptionController;
  @Mock ConsumptionService consumptionService;

  MockMvc mockMvc;

  private final String BASE_URL = ConsumptionController.BASE_URL;
  private final long ORDER_ID = 1L;
  private final int QUANTITY_EXAMPLE = 10;
  private final String CONSUMABLE_EXAMPLE = "Consumable_example";

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(consumptionController)
        .build();
  }
  @Test
  void createConsumptionGivenOrderIdAndQuantityAndConsumable_returnsDTO() throws Exception {
    //given - Form to be delivered and DTOs to be returned when method is called
    Consumable consumable = new Consumable(); consumable.setConsumable(CONSUMABLE_EXAMPLE);
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
    given(consumptionService.createConsumption(form)).willReturn(consumptionDTO); //todo: implement this

    //when and then
    mockMvc.perform(post(BASE_URL + "/create")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(form)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.quantity", equalTo(QUANTITY_EXAMPLE)))
        .andExpect(jsonPath("$.consumableDTO.consumable", equalTo(CONSUMABLE_EXAMPLE)))
        .andExpect(jsonPath("$.orderDTO.id", equalTo((int)ORDER_ID)));
  }
}