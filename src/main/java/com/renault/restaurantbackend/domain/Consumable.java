package com.renault.restaurantbackend.domain;

import com.renault.restaurantbackend.domain.enums.ConsumableType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Consumable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String consumable;

  @Enumerated(EnumType.STRING)
  private ConsumableType consumableType;

  @Column(nullable = false)
  private double value;
}
