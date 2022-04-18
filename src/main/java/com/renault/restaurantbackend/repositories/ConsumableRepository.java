package com.renault.restaurantbackend.repositories;

import com.renault.restaurantbackend.domain.Consumable;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsumableRepository extends JpaRepository<Consumable, Long> {
  Optional<Consumable> findByConsumable(String consumable);
}
