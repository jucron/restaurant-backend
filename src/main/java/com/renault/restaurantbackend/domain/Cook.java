package com.renault.restaurantbackend.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Cook {
  @Id @Column(name = "id", nullable = false) private Long id;

  private String name;

  @OneToOne
  @JoinColumn(name = "login_id")
  private LoginStaff loginStaff;
}
