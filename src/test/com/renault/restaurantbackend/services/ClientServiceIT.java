package com.renault.restaurantbackend.services;

import com.renault.restaurantbackend.domain.Beverage;
import com.renault.restaurantbackend.domain.Client;
import com.renault.restaurantbackend.domain.ClientOrder;
import com.renault.restaurantbackend.domain.ClientTable;
import com.renault.restaurantbackend.domain.Meal;
import com.renault.restaurantbackend.repositories.BeverageRepository;
import com.renault.restaurantbackend.repositories.ClientRepository;
import com.renault.restaurantbackend.repositories.ClientTableRepository;
import com.renault.restaurantbackend.repositories.MealRepository;
import com.renault.restaurantbackend.repositories.OrderRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.renault.restaurantbackend.domain.Status.CLOSED;
import static com.renault.restaurantbackend.domain.Status.OPEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
class ClientServiceIT {
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
      //create meals and beverages
      double meal_value = 10.05; double beverage_value = 5.45;
      Meal meal1 = new Meal();meal1.setMeal(MEAL_EXAMPLE_1); meal1.setValue(meal_value);
      Meal meal2 = new Meal();meal2.setMeal(MEAL_EXAMPLE_2); meal2.setValue(meal_value/2);
      Beverage beverage1 = new Beverage(); beverage1.setBeverage(BEVERAGE_EXAMPLE_1); beverage1.setValue(beverage_value);
      Beverage beverage2 = new Beverage(); beverage2.setBeverage(BEVERAGE_EXAMPLE_2); beverage2.setValue(beverage_value/2);
      mealRepository.saveAll(List.of(meal1,meal2)); beverageRepository.saveAll(List.of(beverage1,beverage2));
      //associate meals and beverages with Order
      order1.setMeals(new HashSet<>(List.of(meal1,meal2))); order1.setBeverages(new HashSet<>(List.of(beverage1,beverage2)));
      orderRepository.save(order1);
    }
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
}