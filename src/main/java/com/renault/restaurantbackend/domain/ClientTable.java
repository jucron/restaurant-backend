package main.java.com.renault.restaurantbackend.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class ClientTable {
  @Id @Column(name = "id", nullable = false)
  private Long id;

  private int number;
  private Status status;
  @ManyToOne
  private Waiter waiter;
}
