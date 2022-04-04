package com.renault.restaurantbackend.repositories;

import com.renault.restaurantbackend.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
  Menu findByName(String menuName);
}
