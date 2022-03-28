package com.renault.restaurantbackend.services;

import com.renault.restaurantbackend.api.v1.mapper.BeverageMapper;
import com.renault.restaurantbackend.api.v1.mapper.ClientMapper;
import com.renault.restaurantbackend.api.v1.mapper.MealMapper;
import com.renault.restaurantbackend.api.v1.model.BeverageDTO;
import com.renault.restaurantbackend.api.v1.model.ClientDTO;
import com.renault.restaurantbackend.api.v1.model.ClientListDTO;
import com.renault.restaurantbackend.api.v1.model.ConsumptionListDTO;
import com.renault.restaurantbackend.api.v1.model.MealDTO;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.renault.restaurantbackend.domain.Status.CLOSED;
import static com.renault.restaurantbackend.domain.Status.OPEN;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
  private final ClientMapper clientMapper;
  private final ClientRepository clientRepository;
  private final ClientTableRepository clientTableRepository;
  private final MealRepository mealRepository;
  private final BeverageRepository beverageRepository;
  private final OrderRepository orderRepository;
  private final MealMapper mealMapper;
  private final BeverageMapper beverageMapper;

  @Override
  public ClientListDTO getAllClients() {
    List<Client> clientList = clientRepository.findAll();
    ClientListDTO clientListDTO = new ClientListDTO(new ArrayList<>());
    for (Client client : clientList) {
      clientListDTO.getClients().add(clientMapper.clientToClientDTO(client));
    }
    return clientListDTO;
  }

  @Override
  public ClientDTO createClient(String name, int tableNumber) {
    //Validation: Check if TableNumber is being used (status==OPEN)
    if (clientTableRepository.findByNumberAndStatus(tableNumber,OPEN)!=null) {
     return null;
    }
    //Operation: create new client, table and order
    Client newClient = new Client();
    newClient.setName(name); newClient.setCheckInTime(LocalDateTime.now());
    ClientTable newClientTable = new ClientTable(); newClientTable.setNumber(tableNumber); newClientTable.setStatus(OPEN);
    ClientOrder newOrder = new ClientOrder(); newOrder.setStatus(OPEN);

    clientTableRepository.save(newClientTable); orderRepository.save(newOrder);
    newClient.setTable(newClientTable); newClient.setOrder(newOrder);
    clientRepository.save(newClient);
    return clientMapper.clientToClientDTO(newClient);
  }

  @Override
  public ClientDTO checkoutClient(String clientName, int tableNumber) {
    ClientTable clientTable = clientTableRepository.findByNumberAndStatus(tableNumber,OPEN);
    //Validation: Check if Table have CLOSED status (not in use)
    if (clientTable.getStatus()==CLOSED) {
      return null;
    }
    //fetching client by parameters
    Client clientFetched = clientRepository.findByNameAndTableAndCheckOutTime(
        clientName,clientTable,null);
    //validation of query
    if (clientFetched == null) { return null; }
    //Assigning a checkout time to this client
    clientFetched.setCheckOutTime(LocalDateTime.now());
    clientRepository.save(clientFetched);
    //Assigning status CLOSED to Table and Order
    ClientTable table = clientFetched.getTable(); ClientOrder order = clientFetched.getOrder();
    table.setStatus(CLOSED); order.setStatus(CLOSED);
    clientTableRepository.save(table); orderRepository.save(order);

    return clientMapper.clientToClientDTO(clientFetched);
  }

  @Override
  public ConsumptionListDTO getListOfConsumption(String clientName, int tableNumber) {
    //fetching client by parameters
    ClientTable clientTable = clientTableRepository.findByNumberAndStatus(tableNumber,OPEN);
    Client clientFetched = clientRepository.findByNameAndTableAndCheckOutTime(
        clientName,clientTable,null);
    //validation of query: client not found or order closed
    if (clientFetched == null) { return null; }
    ClientOrder order = clientFetched.getOrder();
    if (order.getStatus()== CLOSED) { return null; }
    //add meals and beverages associated with this order
    List<MealDTO> mealsDTO = this.convertMealListToMealDTOList(order.getId());
    List<BeverageDTO> beveragesDTO = this.convertBeverageListToBeverageDTOList(order.getId());
    //Creating a ConsumptionList and adding all values fetched
    ConsumptionListDTO consumptionListDTO = new ConsumptionListDTO();
    consumptionListDTO.setClientDTO(clientMapper.clientToClientDTO(clientFetched));
    consumptionListDTO.setMealsDTO(mealsDTO); consumptionListDTO.setBeveragesDTO(beveragesDTO);
    consumptionListDTO.setTotalCost(calculateMealTotal(mealsDTO)+calculateBeverageTotal(beveragesDTO));
    return consumptionListDTO;
  }
  private double calculateMealTotal(List<MealDTO> list) {
    double totalCost = 0;
    for (MealDTO item : list) {
      totalCost=+item.getValue();
    }
    return totalCost;
  }
  private double calculateBeverageTotal(List<BeverageDTO> list) {
    double totalCost = 0;
    for (BeverageDTO item : list) {
      totalCost=+item.getValue();
    }
    return totalCost;
  }
  private List<MealDTO> convertMealListToMealDTOList(long orderId) {
    List<Meal> meals = new ArrayList<>(mealRepository.findAllByOrdersId(orderId));
    List<MealDTO> mealsDTO = new ArrayList<>();
      for (Meal meal : meals) {
        mealsDTO.add(mealMapper.MealToMealDTO(meal));
      }
      return mealsDTO;
  }
  private List<BeverageDTO> convertBeverageListToBeverageDTOList(long orderId) {
    List<Beverage> beverages = new ArrayList<>(beverageRepository.findAllByOrdersId(orderId));
    List<BeverageDTO> beveragesDTO = new ArrayList<>();
    for (Beverage beverage : beverages) {
      beveragesDTO.add(beverageMapper.beverageToBeverageDTO(beverage)); //todo
    }
    return beveragesDTO;
  }
}
