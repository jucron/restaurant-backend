package com.renault.restaurantbackend.repositories;

import com.renault.restaurantbackend.domain.Consumption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsumptionRepository extends JpaRepository<Consumption, Long> {
}
