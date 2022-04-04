package com.renault.restaurantbackend.controllers;

import com.renault.restaurantbackend.api.v1.model.MenuDTO;
import com.renault.restaurantbackend.services.MenuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MenuControllerTest {
  /* Expected behaviour:
   * OK: Create a new Menu with a name (for example: "dinner menu")
   * todo: View list of Menu's
   * todo: Fetch a Menu by its name
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

}