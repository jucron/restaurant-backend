package com.renault.restaurantbackend.domain;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import lombok.Data;

@Entity
@Data
public class Beverage {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String beverage;
  private double value;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "rt_beverage_order",
      joinColumns = @JoinColumn(name = "beverage_id"),
      inverseJoinColumns = @JoinColumn(name = "order_id"))
  private Set<ClientOrder> order;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "rt_beverage_menu",
      joinColumns = @JoinColumn(name = "beverage_id"),
      inverseJoinColumns = @JoinColumn(name = "menu_id"))
  private Set<Menu> menu;
}
