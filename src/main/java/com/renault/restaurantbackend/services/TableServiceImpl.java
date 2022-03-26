package com.renault.restaurantbackend.services;

import com.renault.restaurantbackend.api.v1.mapper.ClientTableMapper;
import com.renault.restaurantbackend.api.v1.model.ClientTableDTO;
import com.renault.restaurantbackend.domain.ClientTable;
import com.renault.restaurantbackend.domain.Status;
import com.renault.restaurantbackend.repositories.ClientTableRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TableServiceImpl implements TableService {
  private final ClientTableRepository tableRepository;
  private final ClientTableMapper tableMapper;


  @Override
  public ClientTableDTO createTable() {
    //get latest tableNumber
    int latestTableNumber = getLatestTableNumber();
    //create table with CLOSED and next tableNumber-> save in repository
    ClientTable newTable = new ClientTable(); newTable.setStatus(Status.CLOSED);
    newTable.setNumber(latestTableNumber+1);
    tableRepository.save(newTable);
    return tableMapper.clientTableToClientTableDTO(newTable);
  }

  private int getLatestTableNumber() {
    List<ClientTable> tables = tableRepository.findAll();
    int latestTableNumber = 0;
    for (ClientTable table : tables) {
      if (table.getNumber()>latestTableNumber) {
        latestTableNumber=table.getNumber();
      }
    }
    return latestTableNumber;
  }
}
