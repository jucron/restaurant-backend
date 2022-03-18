package com.renault.restaurantbackend.services;

import com.renault.restaurantbackend.api.v1.mapper.ClientMapper;
import com.renault.restaurantbackend.api.v1.model.ClientDTO;
import com.renault.restaurantbackend.api.v1.model.ClientListDTO;
import com.renault.restaurantbackend.domain.Client;
import com.renault.restaurantbackend.repositories.ClientRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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

  @Captor
  ArgumentCaptor<Client> clientCaptor;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    clientService = new ClientServiceImpl(clientMapper,clientRepository);
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
  void createANewClientWithAGivenNameAndReturnsDTO() {
    //given
    String clientExampleName = "clientExampleName";
    given(clientMapper.clientToClientDTO(any(Client.class))).willReturn(new ClientDTO());
    //when
    ClientDTO clientDTO = clientService.createClient(clientExampleName);
    //then
    verify(clientRepository).save(clientCaptor.capture()); //capture client saved
    verify(clientMapper).clientToClientDTO(any(Client.class));

    Client capturedClient = clientCaptor.getValue();
    assertEquals(clientExampleName,capturedClient.getName()); //check name assignment
    assertNotNull(capturedClient.getCheckInTime()); //check time assignment
  }
}