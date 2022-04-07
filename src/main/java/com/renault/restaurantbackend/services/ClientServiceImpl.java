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
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.renault.restaurantbackend.domain.enums.Status.CLOSED;
import static com.renault.restaurantbackend.domain.enums.Status.OPEN;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
  private final ClientMapper clientMapper;
  private final ClientRepository clientRepository;
  private final ClientTableRepository tableRepository;
  private final ConsumableRepository mealRepository;
  private final OrderRepository orderRepository;
  private final ConsumableMapper mealMapper;

  @Override
  public ClientListDTO getAllClientsWithStatus(String status) {
    List<Client> clientList = clientRepository.findAll();
    ClientListDTO clientListDTO = new ClientListDTO(new ArrayList<>());
    //case1: status=OPEN - get clients with null 'checkoutTime' (not left)
    //case2: status=CLOSED - get clients with not-null 'checkoutTime' (already left)
    //case3: status!=OPEN/CLOSED - error, return null
    switch (status.toUpperCase()) {
      case "OPEN": //case 1
        for (Client client : clientList) {
          if (client.getCheckOutTime()==null) {
            clientListDTO.getClients().add(clientMapper.clientToClientDTO(client));
          }
        }
        return clientListDTO;
      case "CLOSED": //case 2
        for (Client client : clientList) {
          if (client.getCheckOutTime()!=null) {
            clientListDTO.getClients().add(clientMapper.clientToClientDTO(client));
          }
        }
        return clientListDTO;
    }
    return null; //case 3
  }

  @Override
  public ClientDTO createClient(String name, int tableNumber) {
    //Validation: Check if TableNumber is being used (status==OPEN)
    ClientTable existentTable = tableRepository.findByNumber(tableNumber);
    if (existentTable.getStatus()==OPEN) {
     return null;
    }
    //Operation 1/3: Change STATUS in existing table and persist
    existentTable.setStatus(OPEN);
    tableRepository.save(existentTable);
    //Operation 2/3: create new order, change STATUS and persist
    ClientOrder newOrder = new ClientOrder(); newOrder.setStatus(OPEN);
    orderRepository.save(newOrder);
    //Operation 3/3: create new client with Name And CheckInTime, assign newOrder and assign existing table. Persist.
    Client newClient = new Client(); newClient.setName(name); newClient.setCheckInTime(LocalDateTime.now());
    newClient.setTable(existentTable); newClient.setOrder(newOrder);
    clientRepository.save(newClient);
    return clientMapper.clientToClientDTO(newClient);
  }

  @Override
  public ClientDTO checkoutClient(String clientName, int tableNumber) {
    ClientTable clientTable = tableRepository.findByNumber(tableNumber);
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
    tableRepository.save(table); orderRepository.save(order);

    return clientMapper.clientToClientDTO(clientFetched);
  }
}
