package com.renault.restaurantbackend.api.v1.model.lists;

import com.renault.restaurantbackend.api.v1.model.ClientDTO;
import com.renault.restaurantbackend.api.v1.model.ConsumableDTO;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@AllArgsConstructor
@NoArgsConstructor
@With
public class ConsumptionListDTO {
  ClientDTO clientDTO;
  List<ConsumableDTO> consumableDTOS;
  double totalCost;
}
