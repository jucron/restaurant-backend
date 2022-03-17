package com.renault.restaurantbackend.domain;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Menu {
  @Id @Column(name = "id", nullable = false) private Long id;

  private String name;
  private Date lastUpdated;

}
