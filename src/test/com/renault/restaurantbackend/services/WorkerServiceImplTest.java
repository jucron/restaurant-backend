package com.renault.restaurantbackend.services;

import com.renault.restaurantbackend.api.v1.mapper.WorkerMapper;
import com.renault.restaurantbackend.api.v1.model.WorkerDTO;
import com.renault.restaurantbackend.controllers.forms.WorkerForm;
import com.renault.restaurantbackend.domain.LoginStaff;
import com.renault.restaurantbackend.domain.Worker;
import com.renault.restaurantbackend.repositories.LoginStaffRepository;
import com.renault.restaurantbackend.repositories.WorkerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.renault.restaurantbackend.domain.enums.WorkerType.WAITER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

class WorkerServiceImplTest {

  private WorkerService workerService;
  @Mock private LoginStaffRepository loginStaffRepository;
  @Mock private WorkerRepository workerRepository;
  @Mock private WorkerMapper workerMapper;

  @Captor private ArgumentCaptor<LoginStaff> loginArgumentCaptor;
  @Captor private ArgumentCaptor<Worker> workerArgumentCaptor;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    workerService = new WorkerServiceImpl(loginStaffRepository,workerRepository,workerMapper);
  }

  @Test
  void createWorkerGivenAWorkerForm_returnsDTO() {
    String workerName = "workerName";
    String username = "username";
    String password = "password";
    WorkerForm workerForm = new WorkerForm()
        .withName(workerName)
        .withWorkerType(WAITER)
        .withUsername(username)
        .withPassword(password);

    //when
    WorkerDTO workerDTO = workerService.createWorker(workerForm);

    //then
    verify(loginStaffRepository).save(loginArgumentCaptor.capture()); //login is persisted
    verify(workerRepository).save(workerArgumentCaptor.capture()); //worker is persisted
    //Login values are assigned before persisting
    LoginStaff loginCaptured = loginArgumentCaptor.getValue();
    assertEquals(username,loginCaptured.getUsername());
    assertEquals(password,loginCaptured.getPassword());
    //Worker values are assigned before persisting
    Worker workerCaptured = workerArgumentCaptor.getValue();
    assertEquals(workerName,workerCaptured.getName());
    assertTrue(workerCaptured.isActive());
    assertEquals(WAITER,workerCaptured.getWorkerType());
    assertEquals(loginCaptured,workerCaptured.getLogin());
  }
}