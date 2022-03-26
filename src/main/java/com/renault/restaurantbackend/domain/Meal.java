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
import javax.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Meal {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String meal;

  private double value;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "rt_meal_order",
      joinColumns = @JoinColumn(name = "meal_id"),
      inverseJoinColumns = @JoinColumn(name = "order_id"))
  private Set<ClientOrder> order;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "rt_meal_menu",
      joinColumns = @JoinColumn(name = "meal_id"),
      inverseJoinColumns = @JoinColumn(name = "menu_id"))
  private Set<Menu> menu;
}
