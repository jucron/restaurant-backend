package com.renault.restaurantbackend.controllers;

import com.renault.restaurantbackend.api.v1.model.MenuDTO;
import com.renault.restaurantbackend.api.v1.model.lists.MenuListDTO;
import com.renault.restaurantbackend.services.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(MenuController.BASE_URL)
@RequiredArgsConstructor
public class MenuController {
  public static final String BASE_URL = "/api/v1/menus";

  private MenuService menuService;


  @PostMapping({"/{menuName}/create"})
  @ResponseStatus(HttpStatus.OK)
  public MenuDTO createMenu(@PathVariable String menuName) {
    return menuService.createMenu(menuName);
  }
  @GetMapping({"/list"})
  @ResponseStatus(HttpStatus.OK)
  public MenuListDTO getListMenu() {
    return menuService.getListMenu();
  }
  @GetMapping({"/{menuName}/get"})
  @ResponseStatus(HttpStatus.OK)
  public MenuDTO getMenu(@PathVariable String menuName) {
    return menuService.getMenuByName(menuName);
  }

}
