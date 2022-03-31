package com.renault.restaurantbackend.services;

import com.renault.restaurantbackend.api.v1.mapper.ClientMapper;
import com.renault.restaurantbackend.api.v1.mapper.ConsumableMapper;
import com.renault.restaurantbackend.api.v1.model.ClientDTO;
import com.renault.restaurantbackend.api.v1.model.ClientListDTO;
import com.renault.restaurantbackend.api.v1.model.ClientOrderDTO;
import com.renault.restaurantbackend.api.v1.model.ClientTableDTO;
import com.renault.restaurantbackend.api.v1.model.ConsumableDTO;
import com.renault.restaurantbackend.domain.Client;
import com.renault.restaurantbackend.domain.ClientOrder;
import com.renault.restaurantbackend.domain.ClientTable;
import com.renault.restaurantbackend.domain.Consumable;
import com.renault.restaurantbackend.repositories.ClientRepository;
import com.renault.restaurantbackend.repositories.ClientTableRepository;
import com.renault.restaurantbackend.repositories.ConsumableRepository;
import com.renault.restaurantbackend.repositories.OrderRepository;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.renault.restaurantbackend.domain.enums.Status.CLOSED;
import static com.renault.restaurantbackend.domain.enums.Status.OPEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

;

class ClientServiceImplTest {

  ClientService clientService;

  @Mock
  ClientMapper clientMapper;
  @Mock
  ClientRepository clientRepository;
  @Mock
  ClientTableRepository clientTableRepository;
  @Mock
  ConsumableRepository mealRepository;
  @Mock
  OrderRepository orderRepository;
  @Mock
  ConsumableMapper mealMapper;
  @Captor
  ArgumentCaptor<Client> clientCaptor;

  private static final String CLIENT_EXAMPLE_NAME = "clientExampleName";
  private static final int TABLE_NUMBER = 1;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    clientService = new ClientServiceImpl(clientMapper,clientRepository, clientTableRepository,
        mealRepository, orderRepository, mealMapper);
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
    assertEquals(TABLE_NUMBER,capturedClient.getTable().getNumber()); //tableNumber assigned
    assertEquals(OPEN,capturedClient.getTable().getStatus()); //check table status change
    assertEquals(OPEN,capturedClient.getOrder().getStatus()); //check order assign and status change
  }
  @Test
  void findANonCheckedOutClientWithNameAndTable_AssignACheckoutValue() {
    //given
    Client clientExample = new Client(); clientExample.setName(CLIENT_EXAMPLE_NAME);
    ClientTable tableExample = new ClientTable(); tableExample.setNumber(TABLE_NUMBER); tableExample.setStatus(OPEN);
    ClientOrder order = new ClientOrder(); order.setStatus(OPEN);
    clientExample.setTable(tableExample); clientExample.setOrder(order);

    given(clientTableRepository.findByNumberAndStatus(TABLE_NUMBER,OPEN)).willReturn(tableExample);
    given(clientRepository.findByNameAndTableAndCheckOutTime(
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
    assertEquals(CLOSED,capturedClient.getTable().getStatus()); //check table status change
    assertEquals(CLOSED,capturedClient.getOrder().getStatus()); //check order status change
    assertNotNull(capturedClient.getCheckOutTime()); //check time assignment
  }
  @Test
  void findANonCheckedOutClientWithNameAndTable_fetchConsumptionList() {
    //given
    long order_id = 1L;
    double mealValue = 10;
    double beverageValue = 30;
    //create a Client with Order and Table
    Client clientExample = new Client(); clientExample.setName(CLIENT_EXAMPLE_NAME);
    ClientTable tableExample = new ClientTable(); tableExample.setNumber(TABLE_NUMBER);
    ClientOrder order = new ClientOrder(); order.setId(order_id); order.setStatus(OPEN);
    clientExample.setTable(tableExample); clientExample.setOrder(order);
    //create a ClientDTO with OrderDTO and TableDTO
    ClientDTO clientExampleDTO = new ClientDTO(); clientExampleDTO.setName(CLIENT_EXAMPLE_NAME);
    ClientTableDTO tableExampleDTO = new ClientTableDTO(); tableExampleDTO.setNumber(TABLE_NUMBER);
    ClientOrderDTO orderDTO = new ClientOrderDTO(); orderDTO.setId(order_id); orderDTO.setStatus(OPEN);
    clientExampleDTO.setTableDTO(tableExampleDTO);  clientExampleDTO.setOrderDTO(orderDTO);
    //create list of Meals and Beverages of the Order
    Set<Consumable> consumables = new HashSet<>(List.of(new Consumable()));
    ConsumableDTO mealDTOWithValue = new ConsumableDTO(); mealDTOWithValue.setValue(mealValue);

    //order.setMeals(meals); order.setBeverages(beverages);

    given(clientTableRepository.findByNumberAndStatus(TABLE_NUMBER,OPEN)).willReturn(tableExample);
    given(clientRepository.findByNameAndTableAndCheckOutTime(
        CLIENT_EXAMPLE_NAME,tableExample,null)).willReturn(clientExample);
    given(clientMapper.clientToClientDTO(clientExample)).willReturn(clientExampleDTO);

    given(mealMapper.consumableToDTO(any(Consumable.class))).willReturn(mealDTOWithValue);
    //given(beverageMapper.beverageToBeverageDTO(any(Beverage.class))).willReturn(beverageDTOWithValue);
    //when
    //ConsumptionListDTO consumptionListDTO = clientService.
        //getListOfConsumption(CLIENT_EXAMPLE_NAME, TABLE_NUMBER);
    //then
    verify(clientTableRepository).findByNumberAndStatus(anyInt(),any());
    verify(clientRepository).findByNameAndTableAndCheckOutTime(any(),any(),any());
    verify(clientMapper).clientToClientDTO(any());
    verify(mealMapper).consumableToDTO(any());
    //verify(beverageMapper).beverageToBeverageDTO(any());

    //assertEquals(CLIENT_EXAMPLE_NAME,consumptionListDTO.getClientDTO().getName());
    //assertEquals(TABLE_NUMBER,consumptionListDTO.getClientDTO().getTableDTO().getNumber());
    //assertEquals(1,consumptionListDTO.getConsumableDTOS().size());
    //assertEquals(1,consumptionListDTO.getBeverageDTOS().size());
    //assertEquals((mealValue+beverageValue),consumptionListDTO.getTotalCost());
  }
}