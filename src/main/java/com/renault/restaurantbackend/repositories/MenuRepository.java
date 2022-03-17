package main.java.com.renault.restaurantbackend.repositories;

import main.java.com.renault.restaurantbackend.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
