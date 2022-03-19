package com.renault.restaurantbackend.controllers;

import com.renault.restaurantbackend.api.v1.model.ClientDTO;
import com.renault.restaurantbackend.api.v1.model.ClientListDTO;
import com.renault.restaurantbackend.controllers.ClientController;
import com.renault.restaurantbackend.services.ClientService;
import com.renault.restaurantbackend.services.ClientServiceImpl;
import java.util.Arrays;
import java.util.List;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.quality.Strictness;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

class ClientControllerTest {
  /*Expected behavior of this class:
  1-OK: Fetch list of ClientDTO's (by waiter)
  2-OK: Create a new Client (check-in of client, by waiter)
  3-todo: Close account (check-out of client, by waiter) //todo
  4-todo: Check bill (by client)
   */

  @InjectMocks
  ClientController clientController;

  @Mock
  ClientService clientService;

  MockMvc mockMvc;

  String BASE_URL = ClientController.BASE_URL;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(clientController)
        .build();
  }

  @Test
  public void getListOfClientsDTO() throws Exception {
    //given
    ClientDTO clientDTO_example_1 = new ClientDTO();
    ClientDTO clientDTO_example_2 = new ClientDTO();
    List<ClientDTO> clients = Arrays.asList(clientDTO_example_1, clientDTO_example_2);
    given(clientService.getAllClients()).willReturn(new ClientListDTO(clients));

    //when and then
    mockMvc.perform(get(BASE_URL + "/")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.clients", hasSize(2)));
  }
  @Test
  public void createANewClientAndReturnsDTO() throws Exception {
    //given
    String clientExampleName = "clientExampleName";
    ClientDTO clientDTO = new ClientDTO(); clientDTO.setName(clientExampleName);

    given(clientService.createClient(clientExampleName)).willReturn(clientDTO);
    //when and then
    mockMvc.perform(post(BASE_URL + "/"+clientExampleName)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", equalTo(clientExampleName)));
  }
  @Test
  public void checkoutAClientByGivingClientsTableAndName() {
    //given
    String clientExampleName = "clientExampleName";
    int tableNumber = 1;
    ClientDTO clientDTO = new ClientDTO(); clientDTO.setName(clientExampleName);
    //todo: implement this!
    given(clientService.checkoutClient(clientExampleName, tableNumber)).willReturn(clientDTO);

  }

}