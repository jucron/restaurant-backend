package com.renault.restaurantbackend.repositories;

import com.renault.restaurantbackend.domain.ClientTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientTableRepository extends JpaRepository<ClientTable, Long> {
  ClientTable findByNumber(int tableNumber);
}
