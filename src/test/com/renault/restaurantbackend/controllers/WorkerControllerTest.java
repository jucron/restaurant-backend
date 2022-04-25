package com.renault.restaurantbackend.controllers;

import com.renault.restaurantbackend.api.v1.mapper.AbstractRestControllerTest;
import com.renault.restaurantbackend.api.v1.model.WorkerDTO;
import com.renault.restaurantbackend.controllers.forms.WorkerForm;
import com.renault.restaurantbackend.services.WorkerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.renault.restaurantbackend.domain.enums.WorkerType.WAITER;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class WorkerControllerTest extends AbstractRestControllerTest {
  /*
   * Todo: Create a worker, with type (Waiter, Cook, etc.), and associate with a Login account
   * Todo: Deactivate a worker
   */

  @InjectMocks
  WorkerController workerController;

  @Mock WorkerService workerService;

  MockMvc mockMvc;

  private final String BASE_URL = WorkerController.BASE_URL;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(workerController)
        .build();
  }
  @Test
  void createAWorkerGivenAWorkerForm_returnsDTO() throws Exception {
    //given
    String workerName = "workerName";
    String username = "username";
    String password = "password";
    WorkerForm workerForm = new WorkerForm()
        .withName(workerName)
        .withWorkerType(WAITER)
        .withUsername(username)
        .withPassword(password);

    WorkerDTO workerDTO = new WorkerDTO(); workerDTO.setActive(true);

    given(workerService.createWorker(workerForm)).willReturn(workerDTO);

    //when and then
    mockMvc.perform(post(BASE_URL + "/create")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(workerForm)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.active", equalTo(true)));
  }
}