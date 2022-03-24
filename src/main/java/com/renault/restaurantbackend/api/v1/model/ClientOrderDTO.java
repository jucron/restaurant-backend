package com.renault.restaurantbackend.api.v1.model;

import com.renault.restaurantbackend.domain.Cook;
import com.renault.restaurantbackend.domain.Status;
import com.renault.restaurantbackend.domain.Waiter;
import java.sql.Date;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ClientOrderDTO {
  private Long id;
  private LocalDateTime lastUpdated;
  private Status status;
  private Cook cook;
  private Waiter waiter;
}
