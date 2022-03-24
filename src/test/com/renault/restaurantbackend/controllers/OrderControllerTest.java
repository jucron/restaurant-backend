package com.renault.restaurantbackend.controllers;

import com.renault.restaurantbackend.api.v1.mapper.ClientOrderMapper;
import com.renault.restaurantbackend.api.v1.model.ClientOrderDTO;
import com.renault.restaurantbackend.domain.Client;
import com.renault.restaurantbackend.domain.ClientOrder;
import com.renault.restaurantbackend.domain.Status;
import com.renault.restaurantbackend.services.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.renault.restaurantbackend.domain.Status.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrderControllerTest {
  /*Expected behavior of this class:
    1 - OK: get Order that belong to a Client
    2 - todo: assign a Cook to an Order
    3 - todo: assign a Waiter to an Order
    4 - todo: assign a Meal to an Order
    5 - todo: assign a Beverage to an Order
   */
  private final String BASE_URL = OrderController.BASE_URL;
  private final long CLIENT_ID = 1;
  private final long ORDER_ID = 10;

  @InjectMocks
  OrderController orderController;

  @Mock
  OrderService orderService;

  MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(orderController)
        .build();
  }

  @Test
  void getAClientOrderByGivingClientsId() throws Exception {
    //given
    ClientOrderDTO orderDTO = new ClientOrderDTO(); orderDTO.setId(ORDER_ID); orderDTO.setStatus(OPEN);

    given(orderService.getOrdersByClientId(CLIENT_ID)).willReturn(orderDTO);

    //when and then
    mockMvc.perform(get(BASE_URL + "/"+CLIENT_ID+"/get")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", equalTo((int) ORDER_ID)))
        .andExpect(jsonPath("$.status", equalTo("OPEN")));
  }
  @Test
  void assignAWaiterToAnOrder() throws Exception {

  }
  @Test
  void assignACookToAnOrder() throws Exception {

  }
  @Test
  void assignAMealToAnOrder() throws Exception {

  }
  @Test
  void assignABeverageToAnOrder() throws Exception {

  }
}