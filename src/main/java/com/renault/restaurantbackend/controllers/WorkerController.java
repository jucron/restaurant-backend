package com.renault.restaurantbackend.controllers;

import com.renault.restaurantbackend.api.v1.model.WorkerDTO;
import com.renault.restaurantbackend.controllers.forms.WorkerForm;
import com.renault.restaurantbackend.services.WorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(WorkerController.BASE_URL)
@RequiredArgsConstructor
public class WorkerController {
  public static final String BASE_URL = "/api/v1/workers";

  private final WorkerService workerService;

  @PostMapping("/create")
  @ResponseStatus(HttpStatus.CREATED)
  public WorkerDTO createWorker(@RequestBody WorkerForm workerForm) {
    return workerService.createWorker(workerForm);
  }
}
