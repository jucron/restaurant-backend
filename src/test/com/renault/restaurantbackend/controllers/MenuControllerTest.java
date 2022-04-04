package com.renault.restaurantbackend.controllers;

import com.renault.restaurantbackend.api.v1.model.MenuDTO;
import com.renault.restaurantbackend.api.v1.model.lists.MenuListDTO;
import com.renault.restaurantbackend.services.MenuService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MenuControllerTest {
  /* Expected behaviour:
   * OK: Create a new Menu with a name (for example: "dinner menu")
   * OK: View list of Menu's
   * OK: Fetch a Menu by its name
   */
  private final String BASE_URL = MenuController.BASE_URL;

  @InjectMocks
  MenuController menuController;
  MockMvc mockMvc;

  @Mock MenuService menuService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(menuController)
        .build();
  }

  @Test
  void createANewMenu () throws Exception {
    //given
    String menuName = "menu_name";
    MenuDTO menuDTO = new MenuDTO().withName(menuName);

    given(menuService.createMenu(menuName)).willReturn(menuDTO);

    //when and then
    mockMvc.perform(post(BASE_URL + "/"+menuName+"/create")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", equalTo(menuName)));
  }
  @Test
  void getListOfMenu () throws Exception {
    //given
    List<MenuDTO> menuDTOS = new ArrayList<>(List.of(
        new MenuDTO(), new MenuDTO()
    ));
    MenuListDTO menuListDTO = new MenuListDTO(); menuListDTO.setMenuDTOS(menuDTOS);

    given(menuService.getListMenu()).willReturn(menuListDTO);

    //when and then
    mockMvc.perform(get(BASE_URL + "/list")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.menuDTOS", hasSize(2)));
  }
  @Test
  void getMenuByName () throws Exception {
    //given
    int count = 1;
    String menuName1 = "menu_name"+count++;
    String menuName2 = "menu_name"+count;

    MenuDTO menuDTO1 = new MenuDTO().withName(menuName1);
    MenuDTO menuDTO2 = new MenuDTO().withName(menuName2);

    given(menuService.getMenuByName(menuName1)).willReturn(menuDTO1);
    given(menuService.getMenuByName(menuName2)).willReturn(menuDTO2);

    //when and then
    mockMvc.perform(get(BASE_URL +"/"+menuName1+ "/get")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", equalTo(menuName1)));
  }

}