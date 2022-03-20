package com.renault.restaurantbackend.repositories;

import com.renault.restaurantbackend.domain.Beverage;
import com.renault.restaurantbackend.domain.Meal;
import com.renault.restaurantbackend.domain.Menu;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeverageRepository extends JpaRepository<Beverage, Long> {
  List<Beverage> findAllByOrderId(long orderId);
}
