package com.renault.restaurantbackend.services;

import com.renault.restaurantbackend.api.v1.model.ClientDTO;
import com.renault.restaurantbackend.api.v1.model.ClientListDTO;

public interface ClientService {
  ClientListDTO getAllClientsWithStatus(String status);

  ClientDTO createClient(String name, int tableNumber);

  ClientDTO checkoutClient(String clientExampleName, int tableNumber);
}
