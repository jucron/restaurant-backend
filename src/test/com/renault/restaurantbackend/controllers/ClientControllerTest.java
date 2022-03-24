package com.renault.restaurantbackend.controllers;

import com.renault.restaurantbackend.api.v1.mapper.AbstractRestControllerTest;
import com.renault.restaurantbackend.api.v1.model.ClientDTO;
import com.renault.restaurantbackend.api.v1.model.ClientListDTO;
import com.renault.restaurantbackend.api.v1.model.ConsumptionListDTO;
import com.renault.restaurantbackend.controllers.forms.ClientNameAndTableNumberForm;
import com.renault.restaurantbackend.domain.Beverage;
import com.renault.restaurantbackend.domain.Client;
import com.renault.restaurantbackend.domain.ClientOrder;
import com.renault.restaurantbackend.domain.ClientTable;
import com.renault.restaurantbackend.domain.Meal;
import com.renault.restaurantbackend.domain.Status;
import com.renault.restaurantbackend.services.ClientService;
import com.renault.restaurantbackend.services.ClientServiceImpl;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.NotNull;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.renault.restaurantbackend.domain.Status.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

class ClientControllerTest extends AbstractRestControllerTest {
  /*Expected behavior of this class:
  1-OK: Fetch list of ClientDTO's (by waiter)
  2-OK: Create a new Client (check-in of client, by waiter)
  3-OK: Close account (check-out of client, by waiter)
  4-todo: Check bill (by client)
   */

  @InjectMocks
  ClientController clientController;

  @Mock
  ClientService clientService;

  MockMvc mockMvc;

  String BASE_URL = ClientController.BASE_URL;

  private final String CLIENT_EXAMPLE_NAME = "clientExampleName";
  private final String MEAL_EXAMPLE = "mealExample";
  private final String BEVERAGE_EXAMPLE = "beverageExample";

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(clientController)
        .build();
  }

  @Test
  void getListOfClientsDTO() throws Exception {
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
  void createANewClientAndReturnsDTOWithOrderAndTable() throws Exception {
    //given
    String clientExampleName = CLIENT_EXAMPLE_NAME;
    int tableNumber = 1;
    ClientDTO clientDTO = new ClientDTO(); clientDTO.setName(clientExampleName); clientDTO.setCheckInTime(LocalDateTime.now());
    ClientTable table = new ClientTable(); table.setNumber(tableNumber); table.setStatus(OPEN);
    ClientOrder order = new ClientOrder(); order.setStatus(OPEN);
    clientDTO.setOrder(order); clientDTO.setClientTable(table);

    given(clientService.createClient(clientExampleName, tableNumber)).willReturn(clientDTO);
    //when and then
    mockMvc.perform(post(BASE_URL + "/create/"+clientExampleName+"/"+tableNumber)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name", equalTo(clientExampleName)))
        /** {@link clientService} must implement the following: */
        .andExpect(jsonPath("$.checkInTime", notNullValue()))
        .andExpect(jsonPath("$.order.status", equalTo("OPEN")))
        .andExpect(jsonPath("$.clientTable.status", equalTo("OPEN")))
        .andExpect(jsonPath("$.clientTable.number", equalTo(tableNumber)));
  }
  @Test
  void checkoutAClientByGivingClientsTableAndName() throws Exception {
    //given
    String clientExampleName = CLIENT_EXAMPLE_NAME;
    int tableNumber = 1;
    ClientNameAndTableNumberForm form = new ClientNameAndTableNumberForm(
        clientExampleName,tableNumber);


    ClientDTO clientDTO = new ClientDTO();clientDTO.setName(clientExampleName);
    clientDTO.setCheckOutTime(LocalDateTime.now());
    clientDTO.setClientTable(new ClientTable()); clientDTO.getClientTable().setStatus(CLOSED);
    clientDTO.setOrder(new ClientOrder()); clientDTO.getOrder().setStatus(CLOSED);

    given(clientService.checkoutClient(clientExampleName, tableNumber)).willReturn(clientDTO);

    //when and then
    mockMvc.perform(put(BASE_URL + "/checkout")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(form)))
                .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", equalTo(CLIENT_EXAMPLE_NAME)))
        /** {@link clientService} must implement the following: */
        .andExpect(jsonPath("$.checkOutTime", notNullValue()))
        .andExpect(jsonPath("$.order.status", equalTo("CLOSED")))
        .andExpect(jsonPath("$.clientTable.status", equalTo("CLOSED")));
  }
  @Test
  void billViewByActiveTableAndClientAndOrder_returnsListOfConsumption() throws Exception {
    //given
    double meal_value = 10.05; double beverage_value = 5.45;
    Meal meal1 = new Meal();meal1.setMeal(MEAL_EXAMPLE); meal1.setValue(meal_value);
    Beverage beverage1 = new Beverage(); beverage1.setBeverage(BEVERAGE_EXAMPLE); beverage1.setValue(beverage_value);
    ClientOrder order1 = new ClientOrder(); order1.setStatus(OPEN);
    meal1.setOrder(new HashSet<>(Set.of(order1))); beverage1.setOrder((new HashSet<>(Set.of(order1))));
    Client client = new Client(); client.setOrder(order1); client.setName(CLIENT_EXAMPLE_NAME);

    ClientNameAndTableNumberForm form = new ClientNameAndTableNumberForm(
        CLIENT_EXAMPLE_NAME,1);

    given(clientService.getListOfConsumption(CLIENT_EXAMPLE_NAME, 1))
        .willReturn(new ConsumptionListDTO(client,new ArrayList<>(List.of(meal1)),
            new ArrayList<>(List.of(beverage1)), meal_value+beverage_value));

    //when and then
    mockMvc.perform(get(BASE_URL + "/consumption")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(form)))
                .andExpect(status().isOk())
        .andExpect(jsonPath("$.client.name", equalTo(CLIENT_EXAMPLE_NAME)))
        .andExpect(jsonPath("$.client.order.status", equalTo(OPEN.toString())))
        .andExpect(jsonPath("$.meals[0].meal", equalTo(MEAL_EXAMPLE)))
        .andExpect(jsonPath("$.beverages[0].beverage", equalTo(BEVERAGE_EXAMPLE)))
        .andExpect(jsonPath("$.totalCost", equalTo(meal_value+beverage_value)));
  }
}