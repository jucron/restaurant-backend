package com.renault.restaurantbackend.domain;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Meal {
  @Id @Column(name = "id", nullable = false) private Long id;

  private String meal;

  @ManyToMany
  @JoinTable(name = "rt_meal_order",
      joinColumns = @JoinColumn(name = "meal_id"),
      inverseJoinColumns = @JoinColumn(name = "order_id"))
  private Set<ClientOrder> order;

  @ManyToMany
  @JoinTable(name = "rt_meal_menu",
      joinColumns = @JoinColumn(name = "meal_id"),
      inverseJoinColumns = @JoinColumn(name = "menu_id"))
  private Set<Menu> menu;
}
