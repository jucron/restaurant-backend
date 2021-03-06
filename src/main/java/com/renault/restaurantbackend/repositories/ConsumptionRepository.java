package com.renault.restaurantbackend.repositories;

import com.renault.restaurantbackend.domain.Consumption;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsumptionRepository extends JpaRepository<Consumption, Long> {

  List<Consumption> findByOrderId (long orderId);
}
