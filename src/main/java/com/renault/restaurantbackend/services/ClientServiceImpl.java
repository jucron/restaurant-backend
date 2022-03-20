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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
  private final ClientMapper clientMapper;
  private final ClientRepository clientRepository;
  private final ClientTableRepository clientTableRepository;
  private final MealRepository mealRepository;
  private final BeverageRepository beverageRepository;

  @Override
  public ClientListDTO getAllClients() {
    List<Client> clientList = clientRepository.findAll();
    ClientListDTO clientListDTO = new ClientListDTO(new ArrayList<>());
    for (Client client : clientList) {
      clientListDTO.getClients().add(clientMapper.clientToClientDTO(client));
    }
    return clientListDTO;
  }

  @Override public ClientDTO createClient(String name) {
    Client newClient = new Client();
    newClient.setName(name); newClient.setCheckInTime(LocalDateTime.now());
    clientRepository.save(newClient);
    return clientMapper.clientToClientDTO(newClient);
  }

  @Override
  public ClientDTO checkoutClient(String clientName, int tableNumber) {
    //fetching client by parameters
    ClientTable clientTable = clientTableRepository.findByNumber(tableNumber);
    Client clientFetched = clientRepository.findByNameAndClientTableAndCheckOutTime(
        clientName,clientTable,null);
    //validation of query
    if (clientFetched == null) { return null; }
    //Assigning a checkout time to this client
    clientFetched.setCheckOutTime(LocalDateTime.now());
    clientRepository.save(clientFetched);

    return clientMapper.clientToClientDTO(clientFetched);
  }

  @Override
  public ConsumptionListDTO getListOfConsumption(String clientName, int tableNumber) {
    //fetching client by parameters
    ClientTable clientTable = clientTableRepository.findByNumber(tableNumber);
    Client clientFetched = clientRepository.findByNameAndClientTableAndCheckOutTime(
        clientName,clientTable,null);
    //validation of query: client not found or order closed
    if (clientFetched == null) { return null; }
    ClientOrder order = clientFetched.getOrder();
    if (order.getStatus()== Status.CLOSED) { return null; }
    //add meals and beverages associated with this order
    List<Meal> meals = new ArrayList<>(mealRepository.findAllByOrderId(order.getId()));
    List<Beverage> beverages = new ArrayList<>(beverageRepository.findAllByOrderId(order.getId()));
    //Creating a ConsumptionList and adding all values fetched
    ConsumptionListDTO consumptionListDTO = new ConsumptionListDTO();
    consumptionListDTO.setClient(clientFetched); consumptionListDTO.setMeals(meals); consumptionListDTO.setBeverages(beverages);
    consumptionListDTO.setTotalCost(calculateMealTotal(meals)+calculateBeverageTotal(beverages));
    return consumptionListDTO;
  }
  private double calculateMealTotal(List<Meal> list) {
    double totalCost = 0;
    for (Meal item : list) {
      totalCost=+item.getValue();
    }
    return totalCost;
  }
  private double calculateBeverageTotal(List<Beverage> list) {
    double totalCost = 0;
    for (Beverage item : list) {
      totalCost=+item.getValue();
    }
    return totalCost;
  }
}
