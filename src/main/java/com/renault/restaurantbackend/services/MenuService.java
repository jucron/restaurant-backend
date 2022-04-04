package com.renault.restaurantbackend.services;

import com.renault.restaurantbackend.api.v1.model.MenuDTO;

public interface MenuService {
  MenuDTO createMenu(String menuName);
}
