package com.renault.restaurantbackend.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Consumption {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private int quantity;

  private LocalDateTime lastUpdated;

  @ManyToOne
  @JoinColumn(name = "consumable_id")
  private Consumable consumable;

  @ManyToOne
  @JoinColumn(name = "order_id")
  private ClientOrder order;
}
