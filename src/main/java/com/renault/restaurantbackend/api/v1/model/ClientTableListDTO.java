package com.renault.restaurantbackend.api.v1.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClientTableListDTO {
  List<ClientTableDTO> tables;
}
