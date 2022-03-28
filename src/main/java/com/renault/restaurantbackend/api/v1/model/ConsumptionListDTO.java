package com.renault.restaurantbackend.api.v1.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConsumptionListDTO {
  ClientDTO clientDTO;
  List<MealDTO> mealsDTO;
  List<BeverageDTO> beveragesDTO;
  double totalCost;
}
