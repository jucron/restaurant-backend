package com.renault.restaurantbackend.repositories;

import com.renault.restaurantbackend.domain.Client;
import com.renault.restaurantbackend.domain.ClientTable;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ClientRepository extends JpaRepository<Client, Long> {
  Client findByNameAndClientTableAndCheckOutTime(String name, ClientTable clientTable, LocalDateTime checkOutTime);
}
