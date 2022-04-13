package com.renault.restaurantbackend.api.v1.model.lists;

import com.renault.restaurantbackend.api.v1.model.ConsumptionDTO;
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
  List<ConsumptionDTO> consumptionDTOS;
  double totalCost;
}
