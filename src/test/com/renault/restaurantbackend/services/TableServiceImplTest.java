package com.renault.restaurantbackend.services;

import com.renault.restaurantbackend.api.v1.mapper.ClientTableMapper;
import com.renault.restaurantbackend.api.v1.model.ClientTableDTO;
import com.renault.restaurantbackend.api.v1.model.lists.ClientTableListDTO;
import com.renault.restaurantbackend.domain.ClientTable;
import com.renault.restaurantbackend.domain.Worker;
import com.renault.restaurantbackend.repositories.ClientTableRepository;
import com.renault.restaurantbackend.repositories.WorkerRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.renault.restaurantbackend.domain.enums.Status.CLOSED;
import static com.renault.restaurantbackend.domain.enums.WorkerType.WAITER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class TableServiceImplTest {
  private TableService tableService;

  @Mock
  private ClientTableMapper tableMapper;
  @Mock
  private ClientTableRepository tableRepository;
  @Mock
  private WorkerRepository workerRepository;

  @Captor
  private ArgumentCaptor<ClientTable> tableArgumentCaptor;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    tableService = new TableServiceImpl(tableRepository,tableMapper, workerRepository);
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
  @Test
  void getAListOfTables_returnsAListDTO() {
    //given
    ClientTable table1 = new ClientTable(); ClientTable table2 = new ClientTable();
    given(tableRepository.findAll()).willReturn(new ArrayList<>(List.of(table1,table2)));
    given(tableMapper.clientTableToClientTableDTO(any(ClientTable.class))).willReturn(new ClientTableDTO());
    //when
    ClientTableListDTO tableListDTO = tableService.getAllTables();
    //then
    assertEquals(2,tableListDTO.getTables().size());
  }
  @Test
  void assignWaiterToATableByGivingTableNumberWaiterId_returnsTableDTO() {
    //given
    int tableNumber = 1; ClientTable table = new ClientTable(); table.setNumber(tableNumber);
    long waiterId = 10L; Worker waiter = new Worker(); waiter.setId(waiterId); waiter.setWorkerType(WAITER);

    given(tableRepository.findByNumber(tableNumber)).willReturn(table);
    given(workerRepository.findById(waiterId)).willReturn(Optional.of(waiter));
    given(tableMapper.clientTableToClientTableDTO(any())).willReturn(new ClientTableDTO());
    //when
    ClientTableDTO tableDTO = tableService.assignWaiterToTable(tableNumber,waiterId);
    //then
    assertNotNull(tableDTO);
    verify(tableRepository).save(tableArgumentCaptor.capture()); //check table is saved

    ClientTable tableCaptured = tableArgumentCaptor.getValue();
    assertEquals(waiterId,tableCaptured.getWaiter().getId());
    assertEquals(WAITER,tableCaptured.getWaiter().getWorkerType());
    assertEquals(tableNumber,tableCaptured.getNumber());
  }
}