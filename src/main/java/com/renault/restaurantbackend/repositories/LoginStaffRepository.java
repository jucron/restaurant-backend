package com.renault.restaurantbackend.repositories;

import com.renault.restaurantbackend.domain.LoginStaff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginStaffRepository extends JpaRepository<LoginStaff, Long> {
}
