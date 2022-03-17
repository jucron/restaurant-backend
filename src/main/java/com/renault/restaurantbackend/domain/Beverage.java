package com.renault.restaurantbackend.domain;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import lombok.Data;

@Entity
@Data
public class Beverage {
  @Id @Column(name = "id", nullable = false) private Long id;

  private String beverage;

  @ManyToMany
  @JoinTable(name = "beverage_order",
      joinColumns = @JoinColumn(name = "beverage_id"),
      inverseJoinColumns = @JoinColumn(name = "order_id"))
  private Set<ClientOrder> order;

  @ManyToMany
  @JoinTable(name = "beverage_menu",
      joinColumns = @JoinColumn(name = "beverage_id"),
      inverseJoinColumns = @JoinColumn(name = "menu_id"))
  private Set<Menu> menu;
}
