package com.renault.restaurantbackend.services;

import com.renault.restaurantbackend.api.v1.model.ClientTableDTO;
import com.renault.restaurantbackend.api.v1.model.ClientTableListDTO;

public interface TableService {
  ClientTableDTO createTable();

  ClientTableListDTO getAllTables();
}
