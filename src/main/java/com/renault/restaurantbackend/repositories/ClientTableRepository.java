package main.java.com.renault.restaurantbackend.repositories;

import main.java.com.renault.restaurantbackend.domain.ClientTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientTableRepository extends JpaRepository<ClientTable, Long> {
}
