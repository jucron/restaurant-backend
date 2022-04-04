package com.renault.restaurantbackend.services;

import com.renault.restaurantbackend.api.v1.mapper.MenuMapper;
import com.renault.restaurantbackend.api.v1.model.MenuDTO;
import com.renault.restaurantbackend.domain.Menu;
import com.renault.restaurantbackend.repositories.MenuRepository;
import java.time.LocalDateTime;
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
    menuRepository.save(newMenu);
    return menuMapper.MenuToMenuDTO(newMenu);
  }
}
