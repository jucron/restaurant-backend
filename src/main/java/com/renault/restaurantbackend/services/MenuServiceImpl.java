package com.renault.restaurantbackend.services;

import com.renault.restaurantbackend.api.v1.mapper.MenuMapper;
import com.renault.restaurantbackend.api.v1.model.MenuDTO;
import com.renault.restaurantbackend.api.v1.model.lists.MenuListDTO;
import com.renault.restaurantbackend.domain.Menu;
import com.renault.restaurantbackend.repositories.MenuRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

  private final MenuRepository menuRepository;
  private final MenuMapper menuMapper;

  @Override public MenuDTO createMenu(String menuName) {
    Menu newMenu = new Menu(); newMenu.setName(menuName);
    newMenu.setLastUpdated(LocalDateTime.now());
    newMenu.setConsumables(new HashSet<>());
    menuRepository.save(newMenu);
    return menuMapper.MenuToMenuDTO(newMenu);
  }

  @Override public MenuListDTO getListMenu() {
    List<Menu> menuList = menuRepository.findAll();
    MenuListDTO menuListDTO = new MenuListDTO(); menuListDTO.setMenuDTOS(new ArrayList<>());
    for (Menu menu : menuList) {
      menuListDTO.getMenuDTOS().add(menuMapper.MenuToMenuDTO(menu));
    }
    return menuListDTO;
  }

  @Override public MenuDTO getMenuByName(String menuName) {
    Menu menuFetched = menuRepository.findByName(menuName); //OK: IT-test
    return menuMapper.MenuToMenuDTO(menuFetched);
  }
}
