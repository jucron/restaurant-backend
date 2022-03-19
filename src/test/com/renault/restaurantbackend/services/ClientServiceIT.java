package com.renault.restaurantbackend.services;

import com.renault.restaurantbackend.api.v1.model.ClientDTO;
import com.renault.restaurantbackend.api.v1.model.ClientListDTO;
import com.renault.restaurantbackend.domain.Client;
import com.renault.restaurantbackend.domain.ClientTable;
import com.renault.restaurantbackend.repositories.ClientRepository;
import com.renault.restaurantbackend.repositories.ClientTableRepository;
import javax.transaction.Transactional;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
class ClientServiceIT {
/*
Testing repository fetch methods
 */
  @Autowired
  private ClientService clientService;
  @Autowired
  private ClientRepository clientRepository;
  @Autowired
  private ClientTableRepository clientTableRepository;

  private final String CLIENT_NAME_1 = "clientName1";
  private final String CLIENT_NAME_2 = "clientName2";
  private final int CLIENT_TABLE_NUMBER_1 = 1;
  private final int CLIENT_TABLE_NUMBER_2 = 2;

  @BeforeEach
  void setUp() {
    this.loadData();
  }
  private void loadData() {
    if (clientRepository.findAll().size()==0) {
      Client clientExample1 = new Client(); clientExample1.setName(CLIENT_NAME_1);
      Client clientExample2 = new Client(); clientExample2.setName(CLIENT_NAME_2);
      ClientTable clientTable1 = new ClientTable(); clientTable1.setNumber(CLIENT_TABLE_NUMBER_1);
      ClientTable clientTable2 = new ClientTable(); clientTable2.setNumber(CLIENT_TABLE_NUMBER_2);
      clientTableRepository.save(clientTable1); clientTableRepository.save(clientTable2);
      clientExample1.setClientTable(clientTable1); clientExample2.setClientTable(clientTable2);
      clientRepository.save(clientExample1); clientRepository.save(clientExample2);
    }
  }
  @Test
  void fetchAllClientsFromRepo() {
    //given
    //(data given at setUp)
    //when
    ClientListDTO clientListDTO = clientService.getAllClients();
    //then
    assertEquals(2,clientListDTO.getClients().size());
  }

  @Test
  @Transactional
  void createANewClient() {
    //given
    String clientExampleName = "clientExampleName";

    //when
    ClientDTO clientDTO = clientService.createClient(clientExampleName);
    //then
    assertEquals(clientExampleName,clientDTO.getName()); //check name assignment
    assertNotNull(clientDTO.getCheckInTime()); //check time assignment
    assertNotNull(clientDTO.getId()); //check ID assignment by saving method
  }
  @Test
  void findTableByTableNumber() {
    //given
    int tableNumber = CLIENT_TABLE_NUMBER_1;
    //when
    ClientTable clientTable = clientTableRepository.findByNumber(tableNumber);
    //then
    assertNotNull(clientTable.getId());
    assertEquals(tableNumber,clientTable.getNumber());
  }
  @Test
  void findClientByNameAndClientTableAndCheckOutTimeNull() {
    //given
    String clientExampleName = CLIENT_NAME_1;
    int tableNumber = CLIENT_TABLE_NUMBER_1;
    ClientTable clientTable = clientTableRepository.findByNumber(tableNumber);
    //when
    Client clientFetched = clientRepository.findByNameAndClientTableAndCheckOutTime(
        clientExampleName,clientTable,null);
    //then
    assertNotNull(clientFetched.getId());
    System.out.println(clientFetched);
  }


}