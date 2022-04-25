package com.renault.restaurantbackend.services;

import com.renault.restaurantbackend.api.v1.mapper.ClientOrderMapper;
import com.renault.restaurantbackend.api.v1.model.ClientOrderDTO;
import com.renault.restaurantbackend.domain.Client;
import com.renault.restaurantbackend.domain.ClientOrder;
import com.renault.restaurantbackend.domain.Worker;
import com.renault.restaurantbackend.repositories.ClientRepository;
import com.renault.restaurantbackend.repositories.OrderRepository;
import com.renault.restaurantbackend.repositories.WorkerRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.renault.restaurantbackend.domain.enums.WorkerType.WAITER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class OrderServiceImplTest {

  private OrderService orderService;

  @Mock
  private ClientOrderMapper clientOrderMapper;
  @Mock
  private OrderRepository orderRepository;
  @Mock
  private ClientRepository clientRepository;
  @Mock
  private WorkerRepository workerRepository;
  @Captor
  private ArgumentCaptor<ClientOrder> orderArgumentCaptor;

  private final long CLIENT_ID = 1;
  private final long ORDER_ID = 10;
  private final long WAITER_ID = 100;
  private final long COOK_ID = 1000;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    orderService = new OrderServiceImpl(
        orderRepository,clientRepository,clientOrderMapper, workerRepository);
  }

  @Test
  void fetchOrder_givenClientID_returnsOrderDTO() {
    //given
    ClientOrderDTO orderDTO = new ClientOrderDTO(); orderDTO.setId(ORDER_ID);
    Client client = new Client(); client.setOrder(new ClientOrder());

    given(clientRepository.findById(CLIENT_ID)).willReturn(Optional.of(client));
    given(clientOrderMapper.orderToOrderDTO(any(ClientOrder.class))).willReturn(orderDTO);

    //when
    ClientOrderDTO fetchedOrderDTO = orderService.getOrdersByClientId(CLIENT_ID);

    //then
    verify(clientRepository).findById(CLIENT_ID);
    verify(clientOrderMapper).orderToOrderDTO(any(ClientOrder.class));
    assertNotNull(fetchedOrderDTO.getId());
  }
  @Test
  void assignAWaiterToAnOrder_givenOrderIdAndWaiterId_returnsOrderDTO () {
    //given
    ClientOrder order = new ClientOrder(); order.setId(ORDER_ID);
    Worker waiter = new Worker(); waiter.setId(WAITER_ID); waiter.setWorkerType(WAITER);

    given(clientOrderMapper.orderToOrderDTO(any(ClientOrder.class))).willReturn(new ClientOrderDTO());
    given(orderRepository.findById(ORDER_ID)).willReturn(Optional.of(order));
    given(workerRepository.findById(WAITER_ID)).willReturn(Optional.of(waiter));
    //when
    ClientOrderDTO orderDTO = orderService.assignWaiterToOrder(ORDER_ID,WAITER_ID);

    //then
    verify(orderRepository).findById(ORDER_ID);
    verify(workerRepository).findById(WAITER_ID);
    verify(orderRepository).save(any());
    verify(clientOrderMapper).orderToOrderDTO(orderArgumentCaptor.capture());

    ClientOrder orderCaptured = orderArgumentCaptor.getValue();
    assertEquals((int)ORDER_ID,orderCaptured.getId());
    assertEquals((int)WAITER_ID,orderCaptured.getWaiter().getId());
  }
  @Test
  void assignACookToAnOrder_givenOrderIdAndCookId_returnsOrderDTO () {
    //given
    ClientOrder order = new ClientOrder(); order.setId(ORDER_ID);
    Worker cook = new Worker(); cook.setId(COOK_ID);

    given(clientOrderMapper.orderToOrderDTO(any(ClientOrder.class))).willReturn(new ClientOrderDTO());
    given(orderRepository.findById(ORDER_ID)).willReturn(Optional.of(order));
    given(workerRepository.findById(COOK_ID)).willReturn(Optional.of(cook));
    //when
    ClientOrderDTO orderDTO = orderService.assignCookToOrder(ORDER_ID,COOK_ID);

    //then
    verify(orderRepository).findById(ORDER_ID);
    verify(workerRepository).findById(COOK_ID);
    verify(orderRepository).save(any());
    verify(clientOrderMapper).orderToOrderDTO(orderArgumentCaptor.capture());

    ClientOrder orderCaptured = orderArgumentCaptor.getValue();
    assertEquals((int)ORDER_ID,orderCaptured.getId());
    assertEquals((int)COOK_ID,orderCaptured.getCook().getId());
  }
}