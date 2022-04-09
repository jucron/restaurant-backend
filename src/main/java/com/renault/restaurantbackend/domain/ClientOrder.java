package com.renault.restaurantbackend.domain;

import com.renault.restaurantbackend.domain.enums.Status;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "client_order")
public class ClientOrder {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  private Status status;

  @ManyToOne
  @JoinColumn(name = "cook_id")
  private Cook cook;

  @ManyToOne
  @JoinColumn(name = "waiter_id")
  private Waiter waiter;
}
