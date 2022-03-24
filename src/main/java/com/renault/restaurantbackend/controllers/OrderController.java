package com.renault.restaurantbackend.controllers;

import com.renault.restaurantbackend.api.v1.model.ClientListDTO;
import com.renault.restaurantbackend.api.v1.model.ClientOrderDTO;
import com.renault.restaurantbackend.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OrderController.BASE_URL)
@RequiredArgsConstructor
public class OrderController {

  public static final String BASE_URL = "/api/v1/orders";

  private OrderService orderService;

  @GetMapping({"/{clientId}/get"})
  @ResponseStatus(HttpStatus.OK)
  public ClientOrderDTO getOrderOfAClient(@PathVariable long clientId) {
    return orderService.getOrdersByClientId(clientId);
  }
}
