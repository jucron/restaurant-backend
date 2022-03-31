package com.renault.restaurantbackend.services;

import com.renault.restaurantbackend.api.v1.mapper.ClientOrderMapper;
import com.renault.restaurantbackend.api.v1.model.ClientOrderDTO;
import com.renault.restaurantbackend.domain.Client;
import com.renault.restaurantbackend.domain.ClientOrder;
import com.renault.restaurantbackend.domain.Cook;
import com.renault.restaurantbackend.domain.Waiter;
import com.renault.restaurantbackend.repositories.ClientRepository;
import com.renault.restaurantbackend.repositories.CookRepository;
import com.renault.restaurantbackend.repositories.OrderRepository;
import com.renault.restaurantbackend.repositories.WaiterRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;
  private final ClientRepository clientRepository;
  private final ClientOrderMapper clientOrderMapper;
  private final WaiterRepository waiterRepository;
  private final CookRepository cookRepository;

  @Override
  public ClientOrderDTO getOrdersByClientId(long clientId) {
    Optional<Client> clientOptional = clientRepository.findById(clientId);
    if (clientOptional.isEmpty()) {return null;}
    ClientOrder fetchOrder = clientOptional.get().getOrder();
    return clientOrderMapper.orderToOrderDTO(fetchOrder);
  }

  @Override public ClientOrderDTO assignWaiterToOrder(long orderId, long waiterId) {
    Optional<ClientOrder> orderOptional = orderRepository.findById(orderId);
    Optional<Waiter> waiterOptional = waiterRepository.findById(waiterId);
    if (orderOptional.isEmpty() || waiterOptional.isEmpty()) {return null;}
    orderOptional.get().setWaiter(waiterOptional.get());
    orderRepository.save(orderOptional.get());
    return clientOrderMapper.orderToOrderDTO(orderOptional.get());
  }

  @Override public ClientOrderDTO assignCookToOrder(long orderId, long cookId) {
    Optional<ClientOrder> orderOptional = orderRepository.findById(orderId);
    Optional<Cook> cookOptional = cookRepository.findById(cookId);
    if (orderOptional.isEmpty() || cookOptional.isEmpty()) {return null;}
    orderOptional.get().setCook(cookOptional.get());
    orderRepository.save(orderOptional.get());
    return clientOrderMapper.orderToOrderDTO(orderOptional.get());
  }
}
