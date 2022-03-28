package com.renault.restaurantbackend.api.v1.model;

import com.renault.restaurantbackend.domain.LoginStaff;
import lombok.Data;

@Data
public class CookDTO {
  private Long id;
  private String name;
  private LoginStaffDTO loginStaffDTO;

}
