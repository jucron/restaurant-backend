package com.renault.restaurantbackend.api.v1.model;

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
  List<MealDTO> mealDTOS;
  List<BeverageDTO> beverageDTOS;
  double totalCost;
}
