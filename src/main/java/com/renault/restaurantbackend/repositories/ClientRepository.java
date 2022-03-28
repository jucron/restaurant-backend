package com.renault.restaurantbackend.repositories;

import com.renault.restaurantbackend.domain.Client;
import com.renault.restaurantbackend.domain.ClientTable;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
  Client findByNameAndTableAndCheckOutTime(String name, ClientTable clientTable, LocalDateTime checkOutTime);
}
