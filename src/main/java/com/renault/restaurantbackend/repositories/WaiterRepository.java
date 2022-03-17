package main.java.com.renault.restaurantbackend.repositories;

import main.java.com.renault.restaurantbackend.domain.Waiter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WaiterRepository extends JpaRepository<Waiter, Long> {
}
