package com.renault.restaurantbackend.services;

import com.renault.restaurantbackend.api.v1.mapper.ConsumableMapper;
import com.renault.restaurantbackend.api.v1.model.ConsumableDTO;
import com.renault.restaurantbackend.domain.Consumable;
import com.renault.restaurantbackend.domain.Menu;
import com.renault.restaurantbackend.repositories.ConsumableRepository;
import com.renault.restaurantbackend.repositories.MenuRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.renault.restaurantbackend.domain.enums.ConsumableType.MEAL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class ConsumableServiceImplTest {

  private ConsumableServiceImpl consumableService;

  @Mock ConsumableMapper consumableMapper;
  @Mock ConsumableRepository consumableRepository;
  @Mock MenuRepository menuRepository;

  @Captor ArgumentCaptor<Consumable> consumableArgumentCaptor;
  @Captor ArgumentCaptor<Menu> menuArgumentCaptor;

  private final String MENU_NAME = "Menu_name";
  private final double VALUE_EXAMPLE = 10;
  private final String CONSUMABLE_EXAMPLE = "Consumable_example";

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    consumableService = new ConsumableServiceImpl(consumableMapper,consumableRepository, menuRepository);
  }
  @Test
  void createConsumableByGivingDTOAndMenuName_returnsDTO() {
    ConsumableDTO consumableDTO = createConsumableDTO();
    Menu menu = new Menu(); menu.setConsumables(new HashSet<>());

    given(menuRepository.findByName(MENU_NAME)).willReturn(menu);
    given(consumableMapper.consumableToDTO(any(Consumable.class))).willReturn(new ConsumableDTO());
    //when
    ConsumableDTO consumableDTOCreated = consumableService.createConsumable(consumableDTO,MENU_NAME);

    //then
    verify(menuRepository).findByName(MENU_NAME); //Menu is fetched by name
    verify(menuRepository).save(menuArgumentCaptor.capture()); //Menu is persisted - captured for more asserts
    verify(consumableRepository).save(consumableArgumentCaptor.capture()); //Consumable is persisted -  - captured for more asserts

    Menu menuSaved = menuArgumentCaptor.getValue();
    assertEquals(1,menuSaved.getConsumables().size()); //Menu has added the new Consumable

    //Consumable saved have same properties as the DTO sent
    Consumable consumableSaved = consumableArgumentCaptor.getValue();
    assertEquals(consumableDTO.getConsumable(),consumableSaved.getConsumable());
    assertEquals(consumableDTO.getConsumableType(),consumableSaved.getConsumableType());
    assertEquals(consumableDTO.getValue(),consumableSaved.getValue());
  }
  @Test
  void deleteAConsumableByGivingItsName() {
    //when: only menu1 have the consumable
    Consumable consumable = createConsumable();
    Menu menu1 = new Menu(); menu1.setConsumables(new HashSet<>());
    menu1.getConsumables().add(consumable);
    Menu menu2 = new Menu(); menu2.setConsumables(new HashSet<>());

    given(consumableRepository.findByConsumable(CONSUMABLE_EXAMPLE)).willReturn(
        Optional.of(consumable));
    given(menuRepository.findAll()).willReturn(List.of(menu1,menu2));
    //when
    consumableService.deleteConsumable(CONSUMABLE_EXAMPLE);

    //then
    verify(consumableRepository).findByConsumable(CONSUMABLE_EXAMPLE); //OK: IT-test
    verify(menuRepository).findAll();
    verify(menuRepository).save(menu1);
    verify(consumableRepository).delete(consumable);

  }
  ConsumableDTO createConsumableDTO() {
    ConsumableDTO consumable = new ConsumableDTO(); consumable.setConsumable(CONSUMABLE_EXAMPLE);
    consumable.setConsumableType(MEAL); consumable.setValue(VALUE_EXAMPLE);
    return consumable;
  }
  Consumable createConsumable() {
    Consumable consumable = new Consumable(); consumable.setConsumable(CONSUMABLE_EXAMPLE);
    consumable.setConsumableType(MEAL); consumable.setValue(VALUE_EXAMPLE);
    return consumable;
  }

}