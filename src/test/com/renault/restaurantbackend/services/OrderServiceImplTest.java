package com.renault.restaurantbackend.services;

import com.renault.restaurantbackend.api.v1.mapper.ClientOrderMapper;
import com.renault.restaurantbackend.api.v1.model.ClientOrderDTO;
import com.renault.restaurantbackend.domain.Client;
import com.renault.restaurantbackend.domain.ClientOrder;
import com.renault.restaurantbackend.repositories.ClientRepository;
import com.renault.restaurantbackend.repositories.OrderRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class OrderServiceImplTest {

  OrderService orderService;

  @Mock
  ClientOrderMapper clientOrderMapper;
  @Mock
  OrderRepository orderRepository;
  @Mock
  ClientRepository clientRepository;

  private final long CLIENT_ID=1L;
  private final long ORDER_ID = 10L;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    orderService = new OrderServiceImpl(orderRepository,clientRepository,clientOrderMapper);
  }

  @Test
  void fetchOrderByGivingAClientID() {
    //given
    ClientOrderDTO orderDTO = new ClientOrderDTO(); orderDTO.setId(ORDER_ID);
    Client client = new Client(); client.setOrder(new ClientOrder());

    given(clientRepository.findById(CLIENT_ID)).willReturn(Optional.of(client));
    given(clientOrderMapper.clientOrderToClientOrderDTO(any(ClientOrder.class))).willReturn(orderDTO);

    //when
    ClientOrderDTO fetchedOrderDTO = orderService.getOrdersByClientId(CLIENT_ID);

    //then
    verify(clientRepository).findById(CLIENT_ID);
    verify(clientOrderMapper).clientOrderToClientOrderDTO(any(ClientOrder.class));
    assertNotNull(fetchedOrderDTO.getId());
  }
}