package com.renault.restaurantbackend.controllers;

import com.renault.restaurantbackend.api.v1.model.ClientOrderDTO;
import com.renault.restaurantbackend.api.v1.model.CookDTO;
import com.renault.restaurantbackend.api.v1.model.WaiterDTO;
import com.renault.restaurantbackend.services.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.renault.restaurantbackend.domain.enums.Status.OPEN;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrderControllerTest {
  /*Expected behavior of this class:
    1 - OK: get Order that belong to a Client
    2 - OK: assign a Cook to an Order
    3 - OK: assign a Waiter to an Order
   */
  private final String BASE_URL = OrderController.BASE_URL;
  private final long CLIENT_ID = 1;
  private final long ORDER_ID = 10;
  private final long WAITER_ID = 100;
  private final long COOK_ID = 1000;

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
    //given
    ClientOrderDTO orderDTO = new ClientOrderDTO(); orderDTO.setId(ORDER_ID);
    WaiterDTO waiterDTO = new WaiterDTO(); waiterDTO.setId(WAITER_ID);
    orderDTO.setStatus(OPEN); orderDTO.setWaiterDTO(waiterDTO);

    given(orderService.assignWaiterToOrder(ORDER_ID, WAITER_ID)).willReturn(orderDTO);

    //when and then
    mockMvc.perform(post(BASE_URL +"/"+ORDER_ID+"/"+WAITER_ID+"/waiter")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", equalTo((int) ORDER_ID)))
        .andExpect(jsonPath("$.status", equalTo("OPEN")))
        .andExpect(jsonPath("$.waiterDTO.id", equalTo((int)WAITER_ID)));
  }
  @Test
  void assignACookToAnOrder() throws Exception {
    //given
    ClientOrderDTO orderDTO = new ClientOrderDTO(); orderDTO.setId(ORDER_ID);
    CookDTO cookDTO = new CookDTO(); cookDTO.setId(COOK_ID);
    orderDTO.setStatus(OPEN); orderDTO.setCookDTO(cookDTO);

    given(orderService.assignCookToOrder(ORDER_ID, COOK_ID)).willReturn(orderDTO);

    //when and then
    mockMvc.perform(post(BASE_URL +"/"+ORDER_ID+"/"+COOK_ID+"/cook")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", equalTo((int) ORDER_ID)))
        .andExpect(jsonPath("$.status", equalTo("OPEN")))
        .andExpect(jsonPath("$.cookDTO.id", equalTo((int)COOK_ID)));
  }
}