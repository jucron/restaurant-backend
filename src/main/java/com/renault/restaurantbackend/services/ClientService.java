package com.renault.restaurantbackend.services;

import com.renault.restaurantbackend.api.v1.model.ClientDTO;
import com.renault.restaurantbackend.api.v1.model.ClientListDTO;
import java.util.List;
import java.util.Set;

public interface ClientService {
  ClientListDTO getAllClients();

  ClientDTO createClient(String name);

  ClientDTO checkoutClient(String clientExampleName, int tableNumber);
}
