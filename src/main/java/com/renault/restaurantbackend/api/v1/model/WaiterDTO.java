package com.renault.restaurantbackend.api.v1.model;

import lombok.Data;

@Data
public class WaiterDTO {
  private Long id;
  private String name;
  private LoginStaffDTO loginDTO;
}
