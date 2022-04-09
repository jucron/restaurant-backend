package com.renault.restaurantbackend.services;

import com.renault.restaurantbackend.api.v1.mapper.ClientMapper;
import com.renault.restaurantbackend.api.v1.mapper.ConsumableMapper;
import com.renault.restaurantbackend.api.v1.model.ClientDTO;
import com.renault.restaurantbackend.api.v1.model.lists.ClientListDTO;
import com.renault.restaurantbackend.domain.Client;
import com.renault.restaurantbackend.domain.ClientOrder;
import com.renault.restaurantbackend.domain.ClientTable;
import com.renault.restaurantbackend.repositories.ClientRepository;
import com.renault.restaurantbackend.repositories.ClientTableRepository;
import com.renault.restaurantbackend.repositories.ConsumableRepository;
import com.renault.restaurantbackend.repositories.OrderRepository;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

;

class ClientServiceImplTest {

  private ClientService clientService;

  @Mock
  private ClientMapper clientMapper;
  @Mock
  private ClientRepository clientRepository;
  @Mock
  private ClientTableRepository clientTableRepository;
  @Mock
  private ConsumableRepository mealRepository;
  @Mock
  private OrderRepository orderRepository;
  @Mock
  private ConsumableMapper mealMapper;
  @Captor
  private ArgumentCaptor<Client> clientCaptor;

  private static final String CLIENT_EXAMPLE_NAME_1 = "clientExampleName1";
  private static final String CLIENT_EXAMPLE_NAME_2 = "clientExampleName2";
  private static final int TABLE_NUMBER = 1;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    clientService = new ClientServiceImpl(clientMapper,clientRepository, clientTableRepository,
        mealRepository, orderRepository, mealMapper);
  }

  @Test
  void getOPENClientsFromRepoAndReturnsDTOList() {
    //given Client_1 is CLOSED = has a checkout not-null (has left)
    String status = "open";
    Client client_example_1 = new Client();
    client_example_1.setCheckOutTime(LocalDateTime.now()); client_example_1.setName(CLIENT_EXAMPLE_NAME_1);
    Client client_example_2 = new Client(); client_example_2.setName(CLIENT_EXAMPLE_NAME_2);
    List<Client> clients = Arrays.asList(client_example_1, client_example_2);

    given(clientRepository.findAll()).willReturn(clients);
    given(clientMapper.clientToClientDTO(client_example_1)).willReturn(new ClientDTO().withName(CLIENT_EXAMPLE_NAME_1));
    given(clientMapper.clientToClientDTO(client_example_2)).willReturn(new ClientDTO().withName(CLIENT_EXAMPLE_NAME_2));

    //when
    ClientListDTO clientListDTO = clientService.getAllClientsWithStatus(status);
    //then
    verify(clientRepository).findAll();
    verify(clientMapper,times(1)).clientToClientDTO(any(Client.class));
    assertEquals(1,clientListDTO.getClients().size());
    assertEquals(CLIENT_EXAMPLE_NAME_2,clientListDTO.getClients().get(0).getName());
  }
  @Test
  void getCLOSEDClientsFromRepoAndReturnsDTOList() {
    //given Client_1 is CLOSED = has a checkout not-null (has left)
    String status = "closed";
    Client client_example_1 = new Client();
    client_example_1.setCheckOutTime(LocalDateTime.now()); client_example_1.setName(CLIENT_EXAMPLE_NAME_1);
    Client client_example_2 = new Client(); client_example_2.setName(CLIENT_EXAMPLE_NAME_2);
    List<Client> clients = Arrays.asList(client_example_1, client_example_2);

    given(clientRepository.findAll()).willReturn(clients);
    given(clientMapper.clientToClientDTO(client_example_1)).willReturn(new ClientDTO().withName(CLIENT_EXAMPLE_NAME_1));
    given(clientMapper.clientToClientDTO(client_example_2)).willReturn(new ClientDTO().withName(CLIENT_EXAMPLE_NAME_2));
    //when
    ClientListDTO clientListDTO = clientService.getAllClientsWithStatus(status);
    //then
    verify(clientRepository).findAll();
    verify(clientMapper,times(1)).clientToClientDTO(any(Client.class));
    assertEquals(1,clientListDTO.getClients().size());
    assertEquals(CLIENT_EXAMPLE_NAME_1,clientListDTO.getClients().get(0).getName());
  }
  @Test
  void createANewClientWithAGivenNameAndTableNumber_returnsDTOWithNewOrderAndTable() {
    //given
    ClientTable table = new ClientTable(); table.setStatus(CLOSED); table.setNumber(TABLE_NUMBER);
    given(clientTableRepository.findByNumber(TABLE_NUMBER)).willReturn(table);
    given(clientMapper.clientToClientDTO(any(Client.class))).willReturn(new ClientDTO());
    //when
    ClientDTO clientDTO = clientService.createClient(CLIENT_EXAMPLE_NAME_1, TABLE_NUMBER);
    //then
    verify(clientMapper).clientToClientDTO(any(Client.class)); //mapping used
    verify(orderRepository).save(any(ClientOrder.class)); //save new order
    verify(clientTableRepository).save(any(ClientTable.class)); //save new table
    verify(clientRepository).save(clientCaptor.capture()); //capture client saved

    Client capturedClient = clientCaptor.getValue();
    assertEquals(CLIENT_EXAMPLE_NAME_1,capturedClient.getName()); //check name assignment
    assertNotNull(capturedClient.getCheckInTime()); //check time assignment
    assertEquals(TABLE_NUMBER,capturedClient.getTable().getNumber()); //tableNumber assigned
    assertEquals(OPEN,capturedClient.getTable().getStatus()); //check table status change
    assertEquals(OPEN,capturedClient.getOrder().getStatus()); //check order assign and status change
  }
  @Test
  void findANonCheckedOutClientWithNameAndTable_AssignACheckoutValue() {
    //given
    Client clientExample = new Client(); clientExample.setName(CLIENT_EXAMPLE_NAME_1);
    ClientTable tableExample = new ClientTable(); tableExample.setNumber(TABLE_NUMBER); tableExample.setStatus(OPEN);
    ClientOrder order = new ClientOrder(); order.setStatus(OPEN);
    clientExample.setTable(tableExample); clientExample.setOrder(order);

    given(clientTableRepository.findByNumber(TABLE_NUMBER)).willReturn(tableExample);
    given(clientRepository.findByNameAndTableAndCheckOutTime(
        CLIENT_EXAMPLE_NAME_1,tableExample,null)).willReturn(clientExample);
    given(clientMapper.clientToClientDTO(any(Client.class))).willReturn(new ClientDTO());
    //when
    ClientDTO clientDTO = clientService.checkoutClient(CLIENT_EXAMPLE_NAME_1, TABLE_NUMBER);
    //then
    verify(clientRepository).save(clientCaptor.capture()); //capture client saved
    verify(clientMapper).clientToClientDTO(any(Client.class));
    verify(clientTableRepository).save(any(ClientTable.class));
    verify(orderRepository).save(any(ClientOrder.class));

    Client capturedClient = clientCaptor.getValue();
    assertEquals(CLIENT_EXAMPLE_NAME_1,capturedClient.getName()); //check name equality
    assertEquals(CLOSED,capturedClient.getTable().getStatus()); //check table status change
    assertEquals(CLOSED,capturedClient.getOrder().getStatus()); //check order status change
    assertNotNull(capturedClient.getCheckOutTime()); //check time assignment
  }
}