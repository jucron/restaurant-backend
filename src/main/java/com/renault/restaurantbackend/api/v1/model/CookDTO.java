package com.renault.restaurantbackend.api.v1.model;

import lombok.Data;

@Data
public class CookDTO {
  private Long id;
  private String name;
  private LoginStaffDTO loginDTO;

}
