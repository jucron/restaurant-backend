package com.renault.restaurantbackend.services;

import com.renault.restaurantbackend.api.v1.mapper.ClientMapper;
import com.renault.restaurantbackend.api.v1.model.ClientDTO;
import com.renault.restaurantbackend.api.v1.model.ClientListDTO;
import com.renault.restaurantbackend.api.v1.model.ConsumptionListDTO;
import com.renault.restaurantbackend.domain.Beverage;
import com.renault.restaurantbackend.domain.Client;
import com.renault.restaurantbackend.domain.ClientOrder;
import com.renault.restaurantbackend.domain.ClientTable;
import com.renault.restaurantbackend.domain.Meal;
import com.renault.restaurantbackend.domain.Status;
import com.renault.restaurantbackend.repositories.BeverageRepository;
import com.renault.restaurantbackend.repositories.ClientRepository;
import com.renault.restaurantbackend.repositories.ClientTableRepository;
import com.renault.restaurantbackend.repositories.MealRepository;
import com.renault.restaurantbackend.repositories.OrderRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.renault.restaurantbackend.domain.Status.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class ClientServiceImplTest {

  ClientService clientService;

  @Mock
  ClientMapper clientMapper;
  @Mock
  ClientRepository clientRepository;
  @Mock
  ClientTableRepository clientTableRepository;
  @Mock
  MealRepository mealRepository;
  @Mock
  BeverageRepository beverageRepository;
  @Mock
  OrderRepository orderRepository;
  @Captor
  ArgumentCaptor<Client> clientCaptor;

  private static final String CLIENT_EXAMPLE_NAME = "clientExampleName";
  private static final int TABLE_NUMBER = 1;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    clientService = new ClientServiceImpl(clientMapper,clientRepository, clientTableRepository,
        mealRepository, beverageRepository, orderRepository);
  }

  @Test
  void getClientsFromRepoAndReturnsDTOList() {
    //given
    Client client_example_1 = new Client();
    Client client_example_2 = new Client();
    List<Client> clients = Arrays.asList(client_example_1, client_example_2);

    given(clientRepository.findAll()).willReturn(clients);
    given(clientMapper.clientToClientDTO(any(Client.class))).willReturn(new ClientDTO());
    //when
    ClientListDTO clientListDTO = clientService.getAllClients();
    //then
    verify(clientRepository).findAll();
    verify(clientMapper,times(2)).clientToClientDTO(any(Client.class));
    assertEquals(2,clientListDTO.getClients().size());
  }
  @Test
  void createANewClientWithAGivenNameAndTableNumber_returnsDTOWithNewOrderAndTable() {
    //given
    given(clientTableRepository.findByNumberAndStatus(TABLE_NUMBER,OPEN)).willReturn(null);
    given(clientMapper.clientToClientDTO(any(Client.class))).willReturn(new ClientDTO());
    //when
    ClientDTO clientDTO = clientService.createClient(CLIENT_EXAMPLE_NAME, TABLE_NUMBER);
    //then
    verify(clientMapper).clientToClientDTO(any(Client.class)); //mapping used
    verify(orderRepository).save(any(ClientOrder.class)); //save new order
    verify(clientTableRepository).save(any(ClientTable.class)); //save new table
    verify(clientRepository).save(clientCaptor.capture()); //capture client saved

    Client capturedClient = clientCaptor.getValue();
    assertEquals(CLIENT_EXAMPLE_NAME,capturedClient.getName()); //check name assignment
    assertNotNull(capturedClient.getCheckInTime()); //check time assignment
    assertEquals(TABLE_NUMBER,capturedClient.getClientTable().getNumber()); //tableNumber assigned
    assertEquals(OPEN,capturedClient.getClientTable().getStatus()); //check table status change
    assertEquals(OPEN,capturedClient.getOrder().getStatus()); //check order assign and status change
  }
  @Test
  void findANonCheckedOutClientWithNameAndTable_AssignACheckoutValue() {
    //given
    Client clientExample = new Client(); clientExample.setName(CLIENT_EXAMPLE_NAME);
    ClientTable tableExample = new ClientTable(); tableExample.setNumber(TABLE_NUMBER); tableExample.setStatus(OPEN);
    ClientOrder order = new ClientOrder(); order.setStatus(OPEN);
    clientExample.setClientTable(tableExample); clientExample.setOrder(order);

    given(clientTableRepository.findByNumberAndStatus(TABLE_NUMBER,OPEN)).willReturn(tableExample);
    given(clientRepository.findByNameAndClientTableAndCheckOutTime(
        CLIENT_EXAMPLE_NAME,tableExample,null)).willReturn(clientExample);
    given(clientMapper.clientToClientDTO(any(Client.class))).willReturn(new ClientDTO());
    //when
    ClientDTO clientDTO = clientService.checkoutClient(CLIENT_EXAMPLE_NAME, TABLE_NUMBER);
    //then
    verify(clientRepository).save(clientCaptor.capture()); //capture client saved
    verify(clientMapper).clientToClientDTO(any(Client.class));
    verify(clientTableRepository).save(any(ClientTable.class));
    verify(orderRepository).save(any(ClientOrder.class));

    Client capturedClient = clientCaptor.getValue();
    assertEquals(CLIENT_EXAMPLE_NAME,capturedClient.getName()); //check name equality
    assertEquals(CLOSED,capturedClient.getClientTable().getStatus()); //check table status change
    assertEquals(CLOSED,capturedClient.getOrder().getStatus()); //check order status change
    assertNotNull(capturedClient.getCheckOutTime()); //check time assignment
  }
  @Test
  void findANonCheckedOutClientWithNameAndTable_fetchConsumptionList() {
    //given
    long order_id = 1L;
    Client clientExample = new Client(); clientExample.setName(CLIENT_EXAMPLE_NAME);
    ClientTable clientTableExample = new ClientTable(); clientTableExample.setNumber(TABLE_NUMBER);
    ClientOrder order = new ClientOrder(); order.setId(order_id); order.setStatus(OPEN);
    clientExample.setClientTable(clientTableExample);  clientExample.setOrder(order);
    List<Meal> meals = new ArrayList<>(List.of(new Meal()));
    List<Beverage> beverages = new ArrayList<>(List.of(new Beverage()));

    given(clientTableRepository.findByNumberAndStatus(TABLE_NUMBER,OPEN)).willReturn(clientTableExample);
    given(clientRepository.findByNameAndClientTableAndCheckOutTime(
        CLIENT_EXAMPLE_NAME,clientTableExample,null)).willReturn(clientExample);
    given(mealRepository.findAllByOrderId(order.getId())).willReturn(meals);
    given(beverageRepository.findAllByOrderId(order.getId())).willReturn(beverages);
    //when
    ConsumptionListDTO consumptionListDTO = clientService.
        getListOfConsumption(CLIENT_EXAMPLE_NAME, TABLE_NUMBER);
    //then
    assertEquals(CLIENT_EXAMPLE_NAME,consumptionListDTO.getClient().getName());
    assertEquals(TABLE_NUMBER,consumptionListDTO.getClient().getClientTable().getNumber());
    assertEquals(meals,consumptionListDTO.getMeals());
    assertEquals(beverages,consumptionListDTO.getBeverages());
  }
}