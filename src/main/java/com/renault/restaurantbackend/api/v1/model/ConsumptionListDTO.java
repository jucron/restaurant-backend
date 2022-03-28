package com.renault.restaurantbackend.api.v1.model;

import com.renault.restaurantbackend.domain.Beverage;
import com.renault.restaurantbackend.domain.Client;
import com.renault.restaurantbackend.domain.Meal;
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
  List<MealDTO> meals;
  List<BeverageDTO> beverages;
  double totalCost;
}
