package com.renault.restaurantbackend.services;

import com.renault.restaurantbackend.api.v1.model.ClientTableDTO;
import com.renault.restaurantbackend.api.v1.model.lists.ClientTableListDTO;

public interface TableService {
  ClientTableDTO createTable();

  ClientTableListDTO getAllTables();

  ClientTableDTO assignWaiterToTable(int tableNumber, long waiterId);
}
