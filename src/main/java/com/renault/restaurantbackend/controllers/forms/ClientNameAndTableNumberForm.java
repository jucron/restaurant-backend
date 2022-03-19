package com.renault.restaurantbackend.controllers.forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientNameAndTableNumberForm {
  private String name;
  private int tableNumber;
}
