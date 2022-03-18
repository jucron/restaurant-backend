package com.renault.restaurantbackend.api.v1.mapper;

import com.renault.restaurantbackend.api.v1.model.ClientDTO;
import com.renault.restaurantbackend.domain.Client;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientMapperTest {
  private final long clientExampleId = 1L;
  private final String clientExampleName = "clientExampleName";
  ClientMapper clientMapper = ClientMapper.INSTANCE;

  @Test
  void convertClientToClientDTO() {
    //given
    Client clientExample = new Client();
    clientExample.setId(clientExampleId);clientExample.setName(clientExampleName);
    //when
    ClientDTO clientExampleDTO = clientMapper.clientToClientDTO(clientExample);
    //then
    assertEquals(clientExampleId,clientExampleDTO.getId());
    assertEquals(clientExampleName,clientExampleDTO.getName());

  }
}