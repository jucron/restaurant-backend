package com.renault.restaurantbackend.repositories;

import com.renault.restaurantbackend.domain.Consumable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsumableRepository extends JpaRepository<Consumable, Long> {
}
