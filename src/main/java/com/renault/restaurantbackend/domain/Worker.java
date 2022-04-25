package com.renault.restaurantbackend.domain;

import com.renault.restaurantbackend.domain.enums.WorkerType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Worker {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Enumerated(EnumType.STRING)
  private WorkerType workerType;

  private boolean active;

  @OneToOne
  @JoinColumn(name = "login_id")
  private LoginStaff login;
}
