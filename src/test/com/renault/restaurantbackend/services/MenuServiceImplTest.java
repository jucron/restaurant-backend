package com.renault.restaurantbackend.services;

import com.renault.restaurantbackend.api.v1.mapper.MenuMapper;
import com.renault.restaurantbackend.api.v1.model.MenuDTO;
import com.renault.restaurantbackend.api.v1.model.lists.MenuListDTO;
import com.renault.restaurantbackend.domain.Menu;
import com.renault.restaurantbackend.repositories.MenuRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class MenuServiceImplTest {

  MenuService menuService;

  @Mock MenuRepository menuRepository;
  @Mock MenuMapper menuMapper;

  @Captor
  ArgumentCaptor<Menu> menuArgumentCaptor;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    menuService = new MenuServiceImpl(menuRepository,menuMapper);
  }

  @Test
  void createANewMenuByGivingAName_returnsDTO() {
    //when
    String menuName = "menu_name";
    MenuDTO menuDTO = new MenuDTO().withName(menuName);

    given(menuMapper.MenuToMenuDTO(any(Menu.class))).willReturn(menuDTO);
    //when
    MenuDTO menuDTOCreated = menuService.createMenu(menuName);
    //then
    verify(menuRepository).save(any(Menu.class)); //confirm persisting data
    verify(menuMapper).MenuToMenuDTO(menuArgumentCaptor.capture()); //confirm mapping to DTO and capture

    Menu newMenu = menuArgumentCaptor.getValue();
    assertEquals(menuName,newMenu.getName()); //check name assignment
    assertNotNull(newMenu.getConsumables());
  }
  @Test
  void getListOfMenus_returnsListDTO() {
    //given
    List<Menu> menus = new ArrayList<>(List.of(new Menu(), new Menu()));

    given(menuRepository.findAll()).willReturn(menus);
    given(menuMapper.MenuToMenuDTO(any(Menu.class))).willReturn(new MenuDTO());
    //when
    MenuListDTO fetchMenuListDTO = menuService.getListMenu();
    //then
    verify(menuRepository).findAll(); //check repo fetch
    verify(menuMapper,times(menus.size())).MenuToMenuDTO(any(Menu.class)); //check mapper use equal to the Menu-list size
    assertEquals(menus.size(),fetchMenuListDTO.getMenuDTOS().size()); //check DTO_list size equal to the Menu-list size
  }
  @Test
  void getMenuByGivingItsName_returnsDTO() {
    //given
    int count = 1;
    String menuName1 = "menu_name"+count++;
    String menuName2 = "menu_name"+count;

    Menu menu1 = new Menu(); menu1.setName(menuName1);
    Menu menu2 = new Menu(); menu2.setName(menuName2);

    given(menuRepository.findByName(menuName1)).willReturn(menu1);
    given(menuRepository.findByName(menuName2)).willReturn(menu2);

    given(menuMapper.MenuToMenuDTO(menu1)).willReturn(new MenuDTO().withName(menuName1));
    given(menuMapper.MenuToMenuDTO(menu2)).willReturn(new MenuDTO().withName(menuName2));
    //when
    MenuDTO menuDTOFetched1 = menuService.getMenuByName(menuName1);
    MenuDTO menuDTOFetched2 = menuService.getMenuByName(menuName2);

    //then
    verify(menuRepository,times(count)).findByName(anyString());
    verify(menuMapper,times(count)).MenuToMenuDTO(any(Menu.class));
    assertEquals(menuName1,menuDTOFetched1.getName());
    assertEquals(menuName2,menuDTOFetched2.getName());
  }
}