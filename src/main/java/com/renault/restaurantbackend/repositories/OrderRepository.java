package com.renault.restaurantbackend.repositories;

import com.renault.restaurantbackend.domain.ClientOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<ClientOrder, Long> {
}
