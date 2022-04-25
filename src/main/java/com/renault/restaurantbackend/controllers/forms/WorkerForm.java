package com.renault.restaurantbackend.controllers.forms;

import com.renault.restaurantbackend.domain.enums.WorkerType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@AllArgsConstructor
@NoArgsConstructor
@With
public class WorkerForm {
  private String name;
  private WorkerType workerType;
  private String username;
  private String password;
}
