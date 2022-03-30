package com.renault.restaurantbackend.domain;

import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "client_order")
public class ClientOrder {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private LocalDateTime lastUpdated;

  @Enumerated(EnumType.STRING)
  private Status status;

  @ManyToOne
  @JoinColumn(name = "cook_id")
  private Cook cook;

  @ManyToOne
  @JoinColumn(name = "waiter_id")
  private Waiter waiter;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "rt_order_meal",
      joinColumns = @JoinColumn(name = "order_id"),
      inverseJoinColumns = @JoinColumn(name = "meal_id"))
  private Set<Meal> meals;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "rt_order_beverage",
      joinColumns = @JoinColumn(name = "order_id"),
      inverseJoinColumns = @JoinColumn(name = "beverage_id"))
  private Set<Beverage> beverages;

}
