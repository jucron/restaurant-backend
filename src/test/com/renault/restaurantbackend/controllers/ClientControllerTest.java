package com.renault.restaurantbackend.controllers;

import com.renault.restaurantbackend.api.v1.mapper.AbstractRestControllerTest;
import com.renault.restaurantbackend.api.v1.model.ClientDTO;
import com.renault.restaurantbackend.api.v1.model.ClientListDTO;
import com.renault.restaurantbackend.api.v1.model.ClientOrderDTO;
import com.renault.restaurantbackend.api.v1.model.ClientTableDTO;
import com.renault.restaurantbackend.controllers.forms.ClientNameAndTableNumberForm;
import com.renault.restaurantbackend.services.ClientService;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.renault.restaurantbackend.domain.enums.Status.CLOSED;
import static com.renault.restaurantbackend.domain.enums.Status.OPEN;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ClientControllerTest extends AbstractRestControllerTest {
  /*Expected behavior of this class:
   * OK: View list of all clients with the chose Status (OPEN/CLOSED)
   * OK: Check-in a new client by assigning an existing Table and a new Order
   * OK: Check-out a Client by assigning a checkout-time and changing the Table, Order status
   */

  @InjectMocks
  ClientController clientController;

  @Mock
  ClientService clientService;

  MockMvc mockMvc;

  private final String BASE_URL = ClientController.BASE_URL;

  private final String CLIENT_EXAMPLE_1 = "clientExampleName1";
  private final String CLIENT_EXAMPLE_2 = "clientExampleName2";
  private final String MEAL_EXAMPLE = "mealExample";
  private final String BEVERAGE_EXAMPLE = "beverageExample";

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(clientController)
        .build();
  }

  @Test
  void getListOfOPENClientsDTO() throws Exception {
    //given - Only client1 is CLOSED: Has a checkoutTime not null
    String status = "open";
    ClientDTO clientDTO_example_1 = new ClientDTO();
    clientDTO_example_1.setCheckOutTime(LocalDateTime.now()); clientDTO_example_1.setName(CLIENT_EXAMPLE_1);
    ClientDTO clientDTO_example_2 = new ClientDTO(); clientDTO_example_2.setName(CLIENT_EXAMPLE_2);
    List<ClientDTO> clientsOPEN = List.of(clientDTO_example_2); List<ClientDTO> clientsCLOSED = List.of(clientDTO_example_1);
    given(clientService.getAllClientsWithStatus("open")).willReturn(new ClientListDTO(clientsOPEN));
    given(clientService.getAllClientsWithStatus("closed")).willReturn(new ClientListDTO(clientsCLOSED));

    //when and then
    mockMvc.perform(get(BASE_URL + "/"+status)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.clients[0].name", equalTo(CLIENT_EXAMPLE_2)))
        .andExpect(jsonPath("$.clients[0].checkOutTime", equalTo(null)))
        .andExpect(jsonPath("$.clients", hasSize(1)));
  }
  @Test
  void getListOfCLOSEDClientsDTO() throws Exception {
    //given - Only client1 is CLOSED: Has a checkoutTime not null
    String status = "closed";
    ClientDTO clientDTO_example_1 = new ClientDTO();
    clientDTO_example_1.setCheckOutTime(LocalDateTime.now()); clientDTO_example_1.setName(CLIENT_EXAMPLE_1);
    ClientDTO clientDTO_example_2 = new ClientDTO(); clientDTO_example_2.setName(CLIENT_EXAMPLE_2);
    List<ClientDTO> clientsOPEN = List.of(clientDTO_example_2); List<ClientDTO> clientsCLOSED = List.of(clientDTO_example_1);
    given(clientService.getAllClientsWithStatus("open")).willReturn(new ClientListDTO(clientsOPEN));
    given(clientService.getAllClientsWithStatus("closed")).willReturn(new ClientListDTO(clientsCLOSED));

    //when and then
    mockMvc.perform(get(BASE_URL + "/"+status)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.clients[0].name", equalTo(CLIENT_EXAMPLE_1)))
        .andExpect(jsonPath("$.clients[0].checkOutTime", notNullValue()))
        .andExpect(jsonPath("$.clients", hasSize(1)));
  }
  @Test
  void createANewClientAndReturnsDTOWithOrderAndTable() throws Exception {
    //given
    String clientExampleName = CLIENT_EXAMPLE_1;
    int tableNumber = 1;
    ClientDTO clientDTO = new ClientDTO(); clientDTO.setName(clientExampleName); clientDTO.setCheckInTime(LocalDateTime.now());
    ClientTableDTO tableDTO = new ClientTableDTO(); tableDTO.setNumber(tableNumber); tableDTO.setStatus(OPEN);
    ClientOrderDTO orderDTO = new ClientOrderDTO(); orderDTO.setStatus(OPEN);
    clientDTO.setOrderDTO(orderDTO); clientDTO.setTableDTO(tableDTO);

    given(clientService.createClient(clientExampleName, tableNumber)).willReturn(clientDTO);
    //when and then
    mockMvc.perform(post(BASE_URL + "/create/"+clientExampleName+"/"+tableNumber)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name", equalTo(clientExampleName)))
        /** {@link clientService} must implement the following: */
        .andExpect(jsonPath("$.checkInTime", notNullValue()))
        .andExpect(jsonPath("$.orderDTO.status", equalTo("OPEN")))
        .andExpect(jsonPath("$.tableDTO.status", equalTo("OPEN")))
        .andExpect(jsonPath("$.tableDTO.number", equalTo(tableNumber)));
  }
  @Test
  void checkoutAClientByGivingClientsTableAndName() throws Exception {
    //given
    String clientExampleName = CLIENT_EXAMPLE_1;
    int tableNumber = 1;
    ClientNameAndTableNumberForm form = new ClientNameAndTableNumberForm(
        clientExampleName,tableNumber);

    ClientDTO clientDTO = new ClientDTO();clientDTO.setName(clientExampleName);
    clientDTO.setCheckOutTime(LocalDateTime.now());
    clientDTO.setTableDTO(new ClientTableDTO()); clientDTO.getTableDTO().setStatus(CLOSED);
    clientDTO.setOrderDTO(new ClientOrderDTO()); clientDTO.getOrderDTO().setStatus(CLOSED);

    given(clientService.checkoutClient(clientExampleName, tableNumber)).willReturn(clientDTO);

    //when and then
    mockMvc.perform(put(BASE_URL + "/checkout")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(form)))
                .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", equalTo(CLIENT_EXAMPLE_1)))
        /** {@link clientService} must implement the following: */
        .andExpect(jsonPath("$.checkOutTime", notNullValue()))
        .andExpect(jsonPath("$.orderDTO.status", equalTo("CLOSED")))
        .andExpect(jsonPath("$.tableDTO.status", equalTo("CLOSED")));
  }

}