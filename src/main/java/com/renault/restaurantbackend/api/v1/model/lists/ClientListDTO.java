package com.renault.restaurantbackend.api.v1.model.lists;

import com.renault.restaurantbackend.api.v1.model.ClientDTO;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClientListDTO {

  List<ClientDTO> clients;
}
