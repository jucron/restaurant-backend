package com.renault.restaurantbackend.repositories;

import com.renault.restaurantbackend.domain.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerRepository extends JpaRepository<Worker, Long> {
}
