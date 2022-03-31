package com.renault.restaurantbackend.repositories;

import com.renault.restaurantbackend.domain.ClientTable;
import com.renault.restaurantbackend.domain.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientTableRepository extends JpaRepository<ClientTable, Long> {
  ClientTable findByNumberAndStatus(int tableNumber, Status status);
}
