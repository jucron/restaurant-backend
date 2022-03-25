package com.renault.restaurantbackend.services;

import com.renault.restaurantbackend.api.v1.model.ClientOrderDTO;

public interface OrderService {
  ClientOrderDTO getOrdersByClientId(long clientId);

  ClientOrderDTO assignWaiterToOrder(long orderId, long waiterId);

  ClientOrderDTO assignCookToOrder(long orderId, long cookId);
}
