package com.renault.restaurantbackend.services;

import com.renault.restaurantbackend.api.v1.mapper.WorkerMapper;
import com.renault.restaurantbackend.api.v1.model.WorkerDTO;
import com.renault.restaurantbackend.controllers.forms.WorkerForm;
import com.renault.restaurantbackend.domain.LoginStaff;
import com.renault.restaurantbackend.domain.Worker;
import com.renault.restaurantbackend.repositories.LoginStaffRepository;
import com.renault.restaurantbackend.repositories.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkerServiceImpl implements WorkerService {
  private final LoginStaffRepository loginStaffRepository;
  private final WorkerRepository workerRepository;
  private final WorkerMapper workerMapper;

  @Override public WorkerDTO createWorker(WorkerForm workerForm) {
    //create Login account, assign values and persist
    LoginStaff newLogin = new LoginStaff(); newLogin.setUsername(workerForm.getUsername());
    newLogin.setPassword(workerForm.getPassword()); loginStaffRepository.save(newLogin);
    //Create worker, assign values, associate with Login and persist it
    Worker newWorker = new Worker(); newWorker.setWorkerType(workerForm.getWorkerType());
    newWorker.setName(workerForm.getName()); newWorker.setActive(true); newWorker.setLogin(newLogin);
    workerRepository.save(newWorker);
    return workerMapper.workerToWorkerDTO(newWorker);
  }
}
