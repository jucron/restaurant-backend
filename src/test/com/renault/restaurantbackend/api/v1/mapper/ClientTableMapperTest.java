package com.renault.restaurantbackend.api.v1.mapper;

import com.renault.restaurantbackend.api.v1.model.ClientTableDTO;
import com.renault.restaurantbackend.domain.ClientTable;
import com.renault.restaurantbackend.domain.Worker;
import com.renault.restaurantbackend.domain.enums.Status;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ClientTableMapperTest {

  ClientTableMapper clientTableMapper = ClientTableMapper.INSTANCE;

  @Test
  void clientTableToClientTableDTO() {
    //given
    int tableNumber = 1;
    long tableId = 1L;
    ClientTable clientTable = new ClientTable(); clientTable.setStatus(Status.OPEN);
    clientTable.setNumber(tableNumber); clientTable.setId(tableId);
    clientTable.setWaiter(new Worker());
    //when
    ClientTableDTO clientTableDTO = clientTableMapper.clientTableToClientTableDTO(clientTable);
    //then
    assertEquals(tableNumber,clientTableDTO.getNumber());
    assertEquals(Status.OPEN,clientTableDTO.getStatus());
    assertNotNull(clientTableDTO.getWaiterDTO());
  }
}