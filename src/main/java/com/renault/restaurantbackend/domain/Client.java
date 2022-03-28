package com.renault.restaurantbackend.domain;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Client {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private LocalDateTime checkInTime;
  private LocalDateTime checkOutTime;

  @OneToOne
  @JoinColumn(name = "order_id")
  private ClientOrder order;

  @OneToOne
  @JoinColumn(name = "client_table_id")
  private ClientTable table;

}
