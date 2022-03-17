package com.renault.restaurantbackend.controllers;

import com.renault.restaurantbackend.controllers.ClientController;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

class ClientControllerTest {

  String BASE_URL = ClientController.BASE_URL;

  @InjectMocks
  ClientController clientController;

  MockMvc mockMvc;

  @BeforeEach
  public void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(clientController)
        .build();
  }

  @Test
  public void getListOfClients() throws Exception {
    //given
    /*
    ClientDTO clientDTO_example_1 = new ClientDTO();
    ClientDTO clientDTO_example_2 = new ClientDTO();

    List<ClientDTO> clients = Arrays.asList(clientDTO_example_1, clientDTO_example_2);
    //when
    given(clientService.getAllClients()).willReturn(clients);
    //then
    mockMvc.perform(get(BASE_URL + "/")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.clients", hasSize(2)));

     */
    fail(); //todo: implement this test after Mapper classes
  }


}