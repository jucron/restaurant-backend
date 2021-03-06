package com.renault.restaurantbackend.controllers;

import com.renault.restaurantbackend.api.v1.model.ClientTableDTO;
import com.renault.restaurantbackend.api.v1.model.lists.ClientTableListDTO;
import com.renault.restaurantbackend.services.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(TableController.BASE_URL)
@RequiredArgsConstructor
public class TableController {
  public static final String BASE_URL = "/api/v1/tables";
  private final TableService tableService;

  @PostMapping({"/create"})
  @ResponseStatus(HttpStatus.OK)
  public ClientTableDTO createTable() {
    return tableService.createTable();
  }
  @GetMapping({"/get"})
  @ResponseStatus(HttpStatus.OK)
  public ClientTableListDTO getAllTables() {
    return tableService.getAllTables();
  }
  @PostMapping({"/{tableNumber}/{waiterId}/waiter"})
  @ResponseStatus(HttpStatus.OK)
  public ClientTableDTO assignWaiterToATable(@PathVariable int tableNumber, @PathVariable long waiterId) {
    return tableService.assignWaiterToTable(tableNumber, waiterId);
  }
}
