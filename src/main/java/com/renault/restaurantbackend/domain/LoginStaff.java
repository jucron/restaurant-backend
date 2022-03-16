package main.java.com.renault.restaurantbackend.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;

@Entity
@Data
public class LoginStaff {
  @Id @Column(name = "id", nullable = false)
  private Long id;

  private String username;
  private String password;

  private WorkerType workerType;
}
