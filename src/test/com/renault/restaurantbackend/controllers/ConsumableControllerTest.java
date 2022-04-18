package com.renault.restaurantbackend.controllers;

import com.renault.restaurantbackend.api.v1.mapper.AbstractRestControllerTest;
import com.renault.restaurantbackend.api.v1.model.ConsumableDTO;
import com.renault.restaurantbackend.services.ConsumableService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.renault.restaurantbackend.domain.enums.ConsumableType.MEAL;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ConsumableControllerTest extends AbstractRestControllerTest {
  /* Expected:
   OK: Create a Consumable, with type (like Meal/Beverage), name and value. Associate it with an existing Menu
   todo: Delete a Meal/Beverage, also removing its Menu's reference
   */
  @InjectMocks
  ConsumableController consumableController;

  @Mock ConsumableService consumableService;

  MockMvc mockMvc;

  private final String BASE_URL = ConsumableController.BASE_URL;
  private final String MENU_NAME = "Menu_name";
  private final double VALUE_EXAMPLE = 10;
  private final String CONSUMABLE_EXAMPLE = "Consumable_example";


  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(consumableController).build();
  }
  @Test
  void createAConsumableByGivingDTOAndMenuName_returnsDTO() throws Exception {
    //given
    ConsumableDTO consumable = createConsumableDTO();

    given(consumableService.createConsumable(consumable,MENU_NAME)).willReturn(consumable);

    //when and then
    mockMvc.perform(post(BASE_URL + "/"+MENU_NAME+"/create")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(consumable)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.consumable", equalTo(CONSUMABLE_EXAMPLE)))
        .andExpect(jsonPath("$.consumableType", equalTo(MEAL.toString())))
        .andExpect(jsonPath("$.value", equalTo(VALUE_EXAMPLE)));

  }
  ConsumableDTO createConsumableDTO() {
    ConsumableDTO consumable = new ConsumableDTO(); consumable.setConsumable(CONSUMABLE_EXAMPLE);
    consumable.setConsumableType(MEAL); consumable.setValue(VALUE_EXAMPLE);
    return consumable;
  }
}