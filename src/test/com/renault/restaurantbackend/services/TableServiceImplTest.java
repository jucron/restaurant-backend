package com.renault.restaurantbackend.services;

import com.renault.restaurantbackend.api.v1.mapper.ClientTableMapper;
import com.renault.restaurantbackend.api.v1.model.ClientTableDTO;
import com.renault.restaurantbackend.domain.ClientTable;
import com.renault.restaurantbackend.domain.Status;
import com.renault.restaurantbackend.repositories.ClientTableRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.renault.restaurantbackend.domain.Status.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class TableServiceImplTest {
  private TableService tableService;

  @Mock
  private ClientTableMapper tableMapper;
  @Mock
  private ClientTableRepository tableRepository;

  @Captor
  private ArgumentCaptor<ClientTable> tableArgumentCaptor;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    tableService = new TableServiceImpl(tableRepository,tableMapper);
  }

  @Test
  void createTableWithNoRepositoryData_returnsDTO() {
    //given
    given(tableMapper.clientTableToClientTableDTO(any(ClientTable.class))).willReturn(new ClientTableDTO());
    given(tableRepository.findAll()).willReturn(new ArrayList<>());
    //when
    ClientTableDTO newTableDTO = tableService.createTable();
    //then
    assertNotNull(newTableDTO);
    verify(tableRepository).save(tableArgumentCaptor.capture()); //check table is saved
    verify(tableRepository).findAll(); //check the latestTableNumber method called

    ClientTable tableCaptured = tableArgumentCaptor.getValue();
    assertEquals(1,tableCaptured.getNumber());
    assertEquals(CLOSED,tableCaptured.getStatus());
  }
  @Test
  void createTableWithRepositoryData_returnsDTO() {
    //given
    int tableNumberCount = 1;
    ClientTable table1 = new ClientTable(); table1.setNumber(tableNumberCount++);
    ClientTable table2 = new ClientTable(); table2.setNumber(tableNumberCount++);
    List<ClientTable> fakeTableRepoList = new ArrayList<>(List.of(table1,table2));

    given(tableMapper.clientTableToClientTableDTO(any(ClientTable.class))).willReturn(new ClientTableDTO());
    given(tableRepository.findAll()).willReturn(fakeTableRepoList);
    //when
    ClientTableDTO newTableDTO = tableService.createTable();
    //then
    assertNotNull(newTableDTO);
    verify(tableRepository).save(tableArgumentCaptor.capture()); //check table is saved
    verify(tableRepository).findAll(); //check the latestTableNumber method called

    ClientTable tableCaptured = tableArgumentCaptor.getValue();
    assertEquals(tableNumberCount,tableCaptured.getNumber());
    assertEquals(CLOSED,tableCaptured.getStatus());
  }
}