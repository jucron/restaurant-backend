package com.renault.restaurantbackend.repositories;

import com.renault.restaurantbackend.domain.Client;
import com.renault.restaurantbackend.domain.ClientOrder;
import com.renault.restaurantbackend.domain.ClientTable;
import com.renault.restaurantbackend.domain.Consumable;
import com.renault.restaurantbackend.domain.Consumption;
import com.renault.restaurantbackend.domain.Menu;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.renault.restaurantbackend.domain.enums.ConsumableType.BEVERAGE;
import static com.renault.restaurantbackend.domain.enums.ConsumableType.MEAL;
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
  private ClientTableRepository tableRepository;
  @Autowired
  private OrderRepository orderRepository;
  @Autowired
  private ConsumableRepository consumableRepository;
  @Autowired
  private MenuRepository menuRepository;
  @Autowired
  private ConsumptionRepository consumptionRepository;

  private final String CLIENT_NAME_1 = "clientName1";
  private final String CLIENT_NAME_2 = "clientName2";
  private final int CLIENT_TABLE_NUMBER_1 = 1;
  private final int CLIENT_TABLE_NUMBER_2 = 2;
  private final String MEAL_EXAMPLE_1 = "mealExample1";
  private final String MEAL_EXAMPLE_2 = "mealExample2";
  private final String BEVERAGE_EXAMPLE_1 = "beverageExample1";
  private final String BEVERAGE_EXAMPLE_2 = "beverageExample2";
  private final String MENU_EXAMPLE = "menu_example";
  private final int CONSUMPTION_QUANTITY_1 = 5;
  private final int CONSUMPTION_QUANTITY_2 = 10;

  private static boolean dataLoaded = false;

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
      ClientTable table1 = new ClientTable(); table1.setNumber(CLIENT_TABLE_NUMBER_1);
      ClientTable table2 = new ClientTable(); table2.setNumber(CLIENT_TABLE_NUMBER_2);
      table1.setStatus(OPEN); table2.setStatus(CLOSED);
      ClientOrder order1 = new ClientOrder(); order1.setStatus(OPEN);
      ClientOrder order2 = new ClientOrder(); order2.setStatus(OPEN);
      //persisting and generating ID with Table and Order
      tableRepository.saveAll(List.of(table1,table2));
      orderRepository.saveAll(List.of(order1,order2));
      //associating Order and Table to the Client and persisting Client
      clientExample1.setTable(table1); clientExample2.setTable(table2);
      clientExample1.setOrder(order1); clientExample2.setOrder(order2);
      clientRepository.saveAll(List.of(clientExample1,clientExample2));
      //create Consumables: meals and beverages
      double meal_value = 10.05; double beverage_value = 5.45;
      Consumable consumable1 = new Consumable(); consumable1.setConsumableType(MEAL);
      consumable1.setConsumable(MEAL_EXAMPLE_1); consumable1.setValue(meal_value);
      Consumable consumable2 = new Consumable(); consumable2.setConsumableType(MEAL);
      consumable2.setConsumable(MEAL_EXAMPLE_2); consumable2.setValue(meal_value/2);
      Consumable consumable3 = new Consumable(); consumable3.setConsumableType(BEVERAGE);
      consumable3.setConsumable(BEVERAGE_EXAMPLE_1); consumable3.setValue(beverage_value);
      Consumable consumable4 = new Consumable(); consumable4.setConsumableType(BEVERAGE);
      consumable4.setConsumable(BEVERAGE_EXAMPLE_2); consumable4.setValue(beverage_value/2);
      Set<Consumable> consumableList = Set.of(consumable1, consumable2, consumable3, consumable4);
      consumableRepository.saveAll(consumableList);
      //Creating and persisting a Menu with those consumables:
      Menu menu = new Menu(); menu.setName(MENU_EXAMPLE); menu.setLastUpdated(LocalDateTime.now());
      menu.setConsumables(consumableList);
      menuRepository.save(menu);
      //Creating Consumables, associating with Order and persisting them
      Consumption consumption1 = new Consumption(); consumption1.setQuantity(CONSUMPTION_QUANTITY_1);
      consumption1.setOrder(order1); consumption1.setConsumable(consumable1);
      Consumption consumption2 = new Consumption(); consumption2.setQuantity(CONSUMPTION_QUANTITY_2);
      consumption2.setOrder(order1); consumption2.setConsumable(consumable2);
      consumptionRepository.saveAll(List.of(consumption1,consumption2));
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
  void findTableByTableNumber() {
    //given
    //when
    ClientTable existingClientTable = tableRepository.findByNumber(CLIENT_TABLE_NUMBER_1);
    ClientTable nonExistingClientTable = tableRepository.findByNumber(CLIENT_TABLE_NUMBER_2+1);

    //then
    assertNotNull(existingClientTable.getId());
    assertEquals(CLIENT_TABLE_NUMBER_1,existingClientTable.getNumber());
    assertNull(nonExistingClientTable);
  }
  @Test
  void findClientByNameAndClientTableAndCheckOutTimeNull() {
    //given
    ClientTable clientTable = tableRepository.findByNumber(CLIENT_TABLE_NUMBER_1);
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
  @Test
  void findConsumptionsByOrderId() {
    //given
    Consumption consumptionFetched = consumptionRepository.findAll().get(0);
    long orderId = consumptionFetched.getOrder().getId();
    //when
    List<Consumption> existingConsumptions = consumptionRepository.findByOrderId(orderId);
    List<Consumption> nonExistingConsumptions = consumptionRepository.findByOrderId(orderId+100);
    //then
    assertEquals(0,nonExistingConsumptions.size());
    assertEquals(2,existingConsumptions.size());
  }
  @Test
  void findConsumableByGivingItsName() {
    //given

    //when
    Consumable consumable1 = consumableRepository.findByConsumable(MEAL_EXAMPLE_1).get();
    Consumable consumable2 = consumableRepository.findByConsumable(BEVERAGE_EXAMPLE_1).get();

    //then
    assertEquals(MEAL,consumable1.getConsumableType());
    assertEquals(MEAL_EXAMPLE_1,consumable1.getConsumable());
    assertEquals(BEVERAGE,consumable2.getConsumableType());
    assertEquals(BEVERAGE_EXAMPLE_1,consumable2.getConsumable());
  }
}