package com.renault.restaurantbackend.services;

import com.renault.restaurantbackend.api.v1.mapper.MenuMapper;
import com.renault.restaurantbackend.api.v1.model.MenuDTO;
import com.renault.restaurantbackend.domain.Menu;
import com.renault.restaurantbackend.repositories.MenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
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
    assertEquals(menuName,newMenu.getName());
  }
}