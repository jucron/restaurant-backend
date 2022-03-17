package main.java.com.renault.restaurantbackend.repositories;

import main.java.com.renault.restaurantbackend.domain.LoginStaff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginStaffRepository extends JpaRepository<LoginStaff, Long> {
}
