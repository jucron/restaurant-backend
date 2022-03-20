package com.renault.restaurantbackend.services;

import com.renault.restaurantbackend.api.v1.model.ClientDTO;
import com.renault.restaurantbackend.api.v1.model.ClientListDTO;
import com.renault.restaurantbackend.api.v1.model.ConsumptionListDTO;

public interface ClientService {
  ClientListDTO getAllClients();

  ClientDTO createClient(String name, int tableNumber);

  ClientDTO checkoutClient(String clientExampleName, int tableNumber);

  ConsumptionListDTO getListOfConsumption(String clientName, int tableNumber);
}
