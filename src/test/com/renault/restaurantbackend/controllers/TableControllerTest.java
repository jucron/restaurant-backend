package com.renault.restaurantbackend.controllers;

import com.renault.restaurantbackend.api.v1.model.ClientTableDTO;
import com.renault.restaurantbackend.domain.Status;
import com.renault.restaurantbackend.services.TableService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TableControllerTest {
  /*Expected behavior of this class:
    1- OK: Create a Table with a unique number
    2- todo: Check Tables that are 'OPEN' to assign new clients
    3- todo: assign a waiter to a tableNumber
    Note: Table status is managed by Client check-in/out
   */
  private final String BASE_URL = TableController.BASE_URL;
  private final long TABLE_ID = 1L;
  private final int TABLE_NUMBER = 10;

  @InjectMocks
  TableController tableController;

  @Mock
  TableService tableService;

  MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(tableController)
        .build();
  }
  @Test
  void createATableWithAnUniqueNumber_returnsDTO() throws Exception {
    //given
    ClientTableDTO tableDTO = new ClientTableDTO(); tableDTO.setId(TABLE_ID);
    tableDTO.setNumber(TABLE_NUMBER); tableDTO.setStatus(Status.CLOSED);
    given(tableService.createTable()).willReturn(tableDTO);
    //when and then
    mockMvc.perform(post(BASE_URL + "/create")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", equalTo((int)TABLE_ID)))
        .andExpect(jsonPath("$.number", equalTo(TABLE_NUMBER)))
        .andExpect(jsonPath("$.status", equalTo("CLOSED")));
  }
}