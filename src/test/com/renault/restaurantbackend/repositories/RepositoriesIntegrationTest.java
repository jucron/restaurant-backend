package com.renault.restaurantbackend.repositories;

import com.renault.restaurantbackend.domain.Client;
import com.renault.restaurantbackend.domain.ClientOrder;
import com.renault.restaurantbackend.domain.ClientTable;
import com.renault.restaurantbackend.domain.Menu;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.renault.restaurantbackend.domain.enums.Status.CLOSED;
import static com.renault.restaurantbackend.domain.enums.Status.OPEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class RepositoriesIntegrationTest {
/*
Testing repository fetch methods
 */
  @Autowired
  private ClientRepository clientRepository;
  @Autowired
  private ClientTableRepository clientTableRepository;
  @Autowired
  private OrderRepository orderRepository;
  @Autowired
  private ConsumableRepository consumableRepository;
  @Autowired
  private MenuRepository menuRepository;

  private final String CLIENT_NAME_1 = "clientName1";
  private final String CLIENT_NAME_2 = "clientName2";
  private final int CLIENT_TABLE_NUMBER_1 = 1;
  private final int CLIENT_TABLE_NUMBER_2 = 2;
  private final String MEAL_EXAMPLE_1 = "mealExample1";
  private final String MEAL_EXAMPLE_2 = "mealExample2";
  private final String BEVERAGE_EXAMPLE_1 = "beverageExample1";
  private final String BEVERAGE_EXAMPLE_2 = "beverageExample2";
  private final String MENU_EXAMPLE = "menu_example";

  private boolean dataLoaded = false;

  @BeforeEach
  void setUp() {
    if (!dataLoaded) {
      log.info("Data was not loaded yet, bootstrapping now.");
      this.loadData();
      dataLoaded=true;
    }
  }
  private void loadData() {
      //create a client with table and order
      Client clientExample1 = new Client(); clientExample1.setName(CLIENT_NAME_1);
      Client clientExample2 = new Client(); clientExample2.setName(CLIENT_NAME_2);
      ClientTable clientTable1 = new ClientTable(); clientTable1.setNumber(CLIENT_TABLE_NUMBER_1);
      ClientTable clientTable2 = new ClientTable(); clientTable2.setNumber(CLIENT_TABLE_NUMBER_2);
      clientTable1.setStatus(OPEN); clientTable2.setStatus(CLOSED);
      //persisting and generating ID with table and client
      clientTableRepository.save(clientTable1); clientTableRepository.save(clientTable2);
      clientExample1.setTable(clientTable1); clientExample2.setTable(clientTable2);
      clientRepository.save(clientExample1); clientRepository.save(clientExample2);
      //creating, persisting and gen.Id with Order
      ClientOrder order1 = new ClientOrder(); order1.setStatus(OPEN);
      orderRepository.save(order1);
      //associating order with client
      clientExample1.setOrder(order1); clientRepository.save(clientExample1);
      //Creating and persisting a Menu to be fetched:
      Menu menu = new Menu(); menu.setName(MENU_EXAMPLE);
      menuRepository.save(menu);

      /*create meals and beverages todo
      double meal_value = 10.05; double beverage_value = 5.45;
      Consumable consumable1 = new Consumable();
      consumable1.setConsumable(MEAL_EXAMPLE_1); consumable1.setValue(meal_value);
      Consumable consumable2 = new Consumable();
      consumable2.setConsumable(MEAL_EXAMPLE_2); consumable2.setValue(meal_value/2);

      consumableRepository.saveAll(List.of(consumable1, consumable2));
      //associate meals and beverages with Order
      //order1.setMeals(new HashSet<>(List.of(meal1,meal2))); order1.setBeverages(new HashSet<>(List.of(beverage1,beverage2)));
      orderRepository.save(order1);
      */
  }
  @Test
  void fetchAllClientsFromRepo() {
    //given
    //(data given at setUp)
    //when
    List<Client> clientList = clientRepository.findAll();
    //then
    assertEquals(2,clientList.size());
  }
  @Test
  void findTableByTableNumberAndStatus() {
    //given
    //when
    ClientTable existingClientTable = clientTableRepository.findByNumberAndStatus(CLIENT_TABLE_NUMBER_1,OPEN);
    ClientTable nonExistingClientTable = clientTableRepository.findByNumberAndStatus(CLIENT_TABLE_NUMBER_2,OPEN);

    //then
    assertNotNull(existingClientTable.getId());
    assertEquals(CLIENT_TABLE_NUMBER_1,existingClientTable.getNumber());
    assertNull(nonExistingClientTable);
  }
  @Test
  void findClientByNameAndClientTableAndCheckOutTimeNull() {
    //given
    ClientTable clientTable = clientTableRepository.findByNumberAndStatus(CLIENT_TABLE_NUMBER_1,OPEN);
    //when
    Client clientFetched = clientRepository.findByNameAndTableAndCheckOutTime(
        CLIENT_NAME_1,clientTable,null);
    //then
    assertNotNull(clientFetched.getId());
    assertEquals(CLIENT_NAME_1,clientFetched.getName());
    assertEquals(CLIENT_TABLE_NUMBER_1,clientTable.getNumber());
  }
  @Test
  void findClientById() {
    //given
    long clientId = 1L;
    //when
    Optional<Client> clientOptional = clientRepository.findById(clientId);
    //then
    assertTrue(clientOptional.isPresent());
    assertEquals(clientId,clientOptional.get().getId());
  }
  @Test
  void findMenuByName() {
    //given
    //when
    Menu menuFetched = menuRepository.findByName(MENU_EXAMPLE);
    //then
    assertNotNull(menuFetched.getId()); //confirm ID generation
    assertEquals(MENU_EXAMPLE,menuFetched.getName()); //confirm fetch of client by name
  }
}