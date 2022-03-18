package com.renault.restaurantbackend.domain;

import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Client {
  @Id @Column(name = "id", nullable = false)
  private Long id;

  private String name;
  private LocalDateTime checkInTime;
  private LocalDateTime checkOutTime;

  @OneToOne
  @JoinColumn(name = "order_id")
  private ClientOrder order;

  @OneToOne
  @JoinColumn(name = "client_table_id")
  private ClientTable clientTable;

}
