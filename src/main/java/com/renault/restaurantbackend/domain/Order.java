package main.java.com.renault.restaurantbackend.domain;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Order {
  @Id @Column(name = "id", nullable = false) private Long id;

  private Status status;
  private Date lastUpdated;

  @ManyToOne
  private Cook cook;
  @ManyToOne
  private Waiter waiter;

}
