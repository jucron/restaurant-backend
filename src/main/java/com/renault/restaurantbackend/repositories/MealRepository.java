package com.renault.restaurantbackend.repositories;

import com.renault.restaurantbackend.domain.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MealRepository extends JpaRepository<Meal, Long> {
}
