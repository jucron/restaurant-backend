package com.renault.restaurantbackend.domain;

import java.time.LocalDateTime;
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
public class Menu {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String name;

  private LocalDateTime lastUpdated;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "rt_menu_meal",
      joinColumns = @JoinColumn(name = "menu_id"),
      inverseJoinColumns = @JoinColumn(name = "meal_id"))
  private Set<Meal> meals;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "rt_menu_beverage",
      joinColumns = @JoinColumn(name = "menu_id"),
      inverseJoinColumns = @JoinColumn(name = "beverage_id"))
  private Set<Beverage> beverages;

}
