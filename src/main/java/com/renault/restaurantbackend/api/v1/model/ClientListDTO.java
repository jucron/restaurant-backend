package com.renault.restaurantbackend.api.v1.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class ClientListDTO {

  List<ClientDTO> clients;
}
