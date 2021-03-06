package com.renault.restaurantbackend.services;

import com.renault.restaurantbackend.api.v1.mapper.ConsumableMapper;
import com.renault.restaurantbackend.api.v1.model.ConsumableDTO;
import com.renault.restaurantbackend.domain.Consumable;
import com.renault.restaurantbackend.domain.Menu;
import com.renault.restaurantbackend.repositories.ConsumableRepository;
import com.renault.restaurantbackend.repositories.MenuRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsumableServiceImpl implements ConsumableService {

  private final ConsumableMapper consumableMapper;
  private final ConsumableRepository consumableRepository;
  private final MenuRepository menuRepository;

  @Override
  public ConsumableDTO createConsumable(ConsumableDTO consumableDTO, String menuName) {
    //Create new Consumable and persist:
    Consumable newConsumable = new Consumable(); newConsumable.setConsumable(consumableDTO.getConsumable());
    newConsumable.setConsumableType(consumableDTO.getConsumableType()); newConsumable.setValue(consumableDTO.getValue());
    consumableRepository.save(newConsumable);
    //Fetch Menu by name and persist it with new Consumable
    Menu menuFetched = menuRepository.findByName(menuName);
    menuFetched.getConsumables().add(newConsumable);
    menuRepository.save(menuFetched);
    return consumableMapper.consumableToDTO(newConsumable);
  }

  @Override public void deleteConsumable(String consumableName) {
    //Fetch Consumable:
    Optional<Consumable> optionalConsumable = consumableRepository.findByConsumable(consumableName);
    if (optionalConsumable.isPresent()) {

      Consumable consumable = optionalConsumable.get();
      //Remove from Menus, and persist the Menus in which was removed:
      List<Menu> menuList = menuRepository.findAll();
      for (Menu menu : menuList) {
        if (menu.getConsumables().remove(consumable)) {
          menuRepository.save(menu);
        }
      }
      //Delete Consumable
      consumableRepository.delete(consumable);

    } else  {
      //todo: throw ExceptionNotFound
    }
  }
}
