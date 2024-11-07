package ca.gbc.orderservice.service;

import ca.gbc.orderservice.dto.OrderRequest;
import ca.gbc.orderservice.model.Order;
import ca.gbc.orderservice.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    //take access from repo using @slf4j
    private final OrderRepository orderRepository;

    @Override
    public void placeOrder(OrderRequest orderRequest) {

        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .price(orderRequest.price())
                .skuCode(orderRequest.skuCode())
                .quantity(orderRequest.quantity())
                .build();

        orderRepository.save(order);

    }
}
