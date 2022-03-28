package com.renault.restaurantbackend.api.v1.mapper;

import com.renault.restaurantbackend.api.v1.model.ClientDTO;
import com.renault.restaurantbackend.domain.Client;
import com.renault.restaurantbackend.domain.ClientOrder;
import com.renault.restaurantbackend.domain.ClientTable;
import com.renault.restaurantbackend.domain.Status;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientMapperTest {
  private final long clientExampleId = 1L;
  private final String clientExampleName = "clientExampleName";
  private final LocalDateTime checkInTime = LocalDateTime.now();
  private final int TABLE_NUMBER = 10;
  com.renault.restaurantbackend.api.v1.mapper.ClientMapper clientMapper = com.renault.restaurantbackend.api.v1.mapper.ClientMapper.INSTANCE;

  @Test
  void convertClientToClientDTO() {
    //given
    Client clientExample = new Client();
    clientExample.setId(clientExampleId);clientExample.setName(clientExampleName);
    clientExample.setCheckInTime(checkInTime);
    ClientOrder order = new ClientOrder(); order.setStatus(Status.OPEN);
    ClientTable table = new ClientTable(); table.setNumber(TABLE_NUMBER);
    clientExample.setOrder(order); clientExample.setTable(table);
    //when
    ClientDTO clientExampleDTO = clientMapper.clientToClientDTO(clientExample);
    //then
    assertEquals(clientExampleId,clientExampleDTO.getId());
    assertEquals(clientExampleName,clientExampleDTO.getName());
    assertEquals(checkInTime,clientExampleDTO.getCheckInTime());
    assertEquals(Status.OPEN,clientExampleDTO.getOrderDTO().getStatus());
    assertEquals(TABLE_NUMBER,clientExampleDTO.getTableDTO().getNumber());

  }
}