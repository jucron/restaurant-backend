package com.renault.restaurantbackend.api.v1.model.lists;

import com.renault.restaurantbackend.api.v1.model.MenuDTO;
import java.util.List;
import lombok.Data;

@Data
public class MenuListDTO {

  private List<MenuDTO> menuDTOS;
}
