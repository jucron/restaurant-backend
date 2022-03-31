package com.renault.restaurantbackend.services;

import com.renault.restaurantbackend.api.v1.mapper.ClientTableMapper;
import com.renault.restaurantbackend.api.v1.model.ClientTableDTO;
import com.renault.restaurantbackend.api.v1.model.ClientTableListDTO;
import com.renault.restaurantbackend.domain.ClientTable;
import com.renault.restaurantbackend.domain.Waiter;
import com.renault.restaurantbackend.domain.enums.Status;
import com.renault.restaurantbackend.repositories.ClientTableRepository;
import com.renault.restaurantbackend.repositories.WaiterRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TableServiceImpl implements TableService {
  private final ClientTableRepository tableRepository;
  private final ClientTableMapper tableMapper;
  private final WaiterRepository waiterRepository;


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

  @Override
  public ClientTableListDTO getAllTables() {
    List<ClientTable> tableList = tableRepository.findAll();
    List<ClientTableDTO> tableDTOList = new ArrayList<>();
    for (ClientTable table : tableList) {
      tableDTOList.add(tableMapper.clientTableToClientTableDTO(table));
    }
    return new ClientTableListDTO(tableDTOList);
  }

  @Override
  public ClientTableDTO assignWaiterToTable(int tableNumber, long waiterId) {
    //Find table in repo (Table must be OPEN, meaning in use by Client)
    ClientTable tableFetched = tableRepository.findByNumberAndStatus(tableNumber,Status.OPEN);
    if (tableFetched==null) {return null;}
    //table found, find waiter in repo
    Optional<Waiter> waiterFetchedOptional = waiterRepository.findById(waiterId);
    if (waiterFetchedOptional.isEmpty()) {return null;}
    //table and waiter exists, assign them and save
    tableFetched.setWaiter(waiterFetchedOptional.get());
    tableRepository.save(tableFetched);
    return tableMapper.clientTableToClientTableDTO(tableFetched);
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
