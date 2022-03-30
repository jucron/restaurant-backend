package com.renault.restaurantbackend.repositories;

import com.renault.restaurantbackend.domain.Beverage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeverageRepository extends JpaRepository<Beverage, Long> {
}
