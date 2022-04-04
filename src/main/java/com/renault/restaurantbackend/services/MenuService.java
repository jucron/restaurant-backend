package com.renault.restaurantbackend.services;

import com.renault.restaurantbackend.api.v1.model.MenuDTO;
import com.renault.restaurantbackend.api.v1.model.lists.MenuListDTO;

public interface MenuService {
  MenuDTO createMenu(String menuName);

  MenuListDTO getListMenu();

  MenuDTO getMenuByName(String menuName);
}
