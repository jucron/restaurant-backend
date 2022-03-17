package main.java.com.renault.restaurantbackend.repositories;

import main.java.com.renault.restaurantbackend.domain.Cook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CookRepository extends JpaRepository<Cook, Long> {
}
