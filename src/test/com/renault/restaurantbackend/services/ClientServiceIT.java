package com.renault.restaurantbackend.services;

import com.renault.restaurantbackend.api.v1.model.ClientDTO;
import com.renault.restaurantbackend.api.v1.model.ClientListDTO;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
  @Autowired
  private OrderRepository orderRepository;
  @Autowired
  private MealRepository mealRepository;
  @Autowired
  private BeverageRepository beverageRepository;

  private final String CLIENT_NAME_1 = "clientName1";
  private final String CLIENT_NAME_2 = "clientName2";
  private final int CLIENT_TABLE_NUMBER_1 = 1;
  private final int CLIENT_TABLE_NUMBER_2 = 2;
  private final String MEAL_EXAMPLE_1 = "mealExample1";
  private final String MEAL_EXAMPLE_2 = "mealExample2";
  private final String BEVERAGE_EXAMPLE_1 = "beverageExample1";
  private final String BEVERAGE_EXAMPLE_2 = "beverageExample2";

  @BeforeEach
  void setUp() {
    this.loadData();
  }
  private void loadData() {
    if (clientRepository.findAll().size()==0) {
      //create a client with table and order
      Client clientExample1 = new Client(); clientExample1.setName(CLIENT_NAME_1);
      Client clientExample2 = new Client(); clientExample2.setName(CLIENT_NAME_2);
      ClientTable clientTable1 = new ClientTable(); clientTable1.setNumber(CLIENT_TABLE_NUMBER_1);
      ClientTable clientTable2 = new ClientTable(); clientTable2.setNumber(CLIENT_TABLE_NUMBER_2);
      //persisting and generating ID with table and client
      clientTableRepository.save(clientTable1); clientTableRepository.save(clientTable2);
      clientExample1.setClientTable(clientTable1); clientExample2.setClientTable(clientTable2);
      clientRepository.save(clientExample1); clientRepository.save(clientExample2);
      //creating, persisting and gen.Id with Order
      ClientOrder order1 = new ClientOrder(); order1.setStatus(Status.OPEN);
      orderRepository.save(order1);
      //associating order with client
      clientExample1.setOrder(order1); clientRepository.save(clientExample1);
      //create meals and beverages
      double meal_value = 10.05; double beverage_value = 5.45;
      Meal meal1 = new Meal();meal1.setMeal(MEAL_EXAMPLE_1); meal1.setValue(meal_value);
      Meal meal2 = new Meal();meal2.setMeal(MEAL_EXAMPLE_2); meal2.setValue(meal_value/2);
      Beverage beverage1 = new Beverage(); beverage1.setBeverage(BEVERAGE_EXAMPLE_1); beverage1.setValue(beverage_value);
      Beverage beverage2 = new Beverage(); beverage2.setBeverage(BEVERAGE_EXAMPLE_2); beverage2.setValue(beverage_value/2);
      //associate meals and beverages with Order
      meal1.setOrder(new HashSet<>(Set.of(order1))); meal2.setOrder(new HashSet<>(Set.of(order1)));
      beverage1.setOrder((new HashSet<>(Set.of(order1)))); beverage2.setOrder((new HashSet<>(Set.of(order1))));
      mealRepository.saveAll(List.of(meal1,meal2)); beverageRepository.saveAll(List.of(beverage1,beverage2));

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
  @Test
  void findAllMealsByOrderId() {
    //given
    long orderId = 1L;
    //when
    List<Meal> meals = mealRepository.findAllByOrderId(orderId);
    //then
    assertEquals(2,meals.size());
    assertEquals(1,meals.get(0).getOrder().size());
    assertEquals(Status.OPEN,meals.get(0).getOrder().iterator().next().getStatus());
  }
  @Test
  void findAllBeveragesByOrderId() {
    //given
    long orderId = 1L;
    //when
    List<Beverage> beverages = beverageRepository.findAllByOrderId(orderId);
    //then
    assertEquals(2,beverages.size());
    assertEquals(1,beverages.get(0).getOrder().size());
    assertEquals(Status.OPEN,beverages.get(0).getOrder().iterator().next().getStatus());
  }
}