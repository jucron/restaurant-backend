package main.java.com.renault.restaurantbackend.repositories;

import main.java.com.renault.restaurantbackend.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
