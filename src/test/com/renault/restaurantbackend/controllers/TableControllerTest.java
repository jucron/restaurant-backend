package com.renault.restaurantbackend.controllers;

import com.renault.restaurantbackend.api.v1.model.ClientTableDTO;
import com.renault.restaurantbackend.api.v1.model.WaiterDTO;
import com.renault.restaurantbackend.api.v1.model.lists.ClientTableListDTO;
import com.renault.restaurantbackend.domain.enums.Status;
import com.renault.restaurantbackend.services.TableService;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TableControllerTest {
  /*Expected behavior of this class:
    1- OK: Create a Table with a unique number
    2- OK: get list of tables and their status
    3- OK: assign a waiter to a tableNumber
    Note: Table status is managed by Client check-in/out
   */
  private final String BASE_URL = TableController.BASE_URL;
  private final long TABLE_ID = 1L;
  private final long WAITER_ID = 10L;
  private final int TABLE_NUMBER = 100;

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
    ClientTableDTO tableDTO = new ClientTableDTO();
    tableDTO.setNumber(TABLE_NUMBER); tableDTO.setStatus(Status.CLOSED);
    given(tableService.createTable()).willReturn(tableDTO);
    //when and then
    mockMvc.perform(post(BASE_URL + "/create")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.number", equalTo(TABLE_NUMBER)))
        .andExpect(jsonPath("$.status", equalTo("CLOSED")));
  }
  @Test
  void checkTables_returnsListDTO() throws Exception {
    //given
    ClientTableDTO tableDTO1 = new ClientTableDTO(); ClientTableDTO tableDTO2 = new ClientTableDTO();

    List<ClientTableDTO> tables = Arrays.asList(tableDTO1, tableDTO2);
    given(tableService.getAllTables()).willReturn(new ClientTableListDTO(tables));

    //when and then
    mockMvc.perform(get(BASE_URL + "/get")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.tables", hasSize(2)));
  }
  @Test
  void assignAWaiterToATable_returnsDTO() throws Exception {
    //given
    WaiterDTO waiterDTO = new WaiterDTO();waiterDTO.setId(WAITER_ID);
    ClientTableDTO tableDTO = new ClientTableDTO(); tableDTO.setWaiterDTO(waiterDTO);
    given(tableService.assignWaiterToTable(TABLE_NUMBER,WAITER_ID)).willReturn(tableDTO);
    //when and then
    mockMvc.perform(post(BASE_URL +"/"+TABLE_NUMBER+"/"+WAITER_ID+ "/waiter")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.waiterDTO.id", equalTo((int)WAITER_ID)));
  }
}