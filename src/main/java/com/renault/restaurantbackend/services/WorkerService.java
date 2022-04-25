package com.renault.restaurantbackend.services;

import com.renault.restaurantbackend.api.v1.model.WorkerDTO;
import com.renault.restaurantbackend.controllers.forms.WorkerForm;

public interface WorkerService {
  WorkerDTO createWorker(WorkerForm workerForm);
}
