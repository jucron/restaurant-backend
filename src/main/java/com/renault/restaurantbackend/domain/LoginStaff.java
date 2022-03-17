package com.renault.restaurantbackend.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import lombok.Data;

@Entity
@Data
public class LoginStaff {
  @Id @Column(name = "id", nullable = false)
  private Long id;

  private String username;
  private String password;

  @Enumerated(EnumType.STRING)
  private WorkerType workerType;
}
