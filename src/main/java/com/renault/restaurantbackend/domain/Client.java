package main.java.com.renault.restaurantbackend.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Client {
  @Id @Column(name = "id", nullable = false)
  private Long id;

  private String name;

  @OneToOne
  private Order order;
  @OneToOne
  private ClientTable clientTable;

}
