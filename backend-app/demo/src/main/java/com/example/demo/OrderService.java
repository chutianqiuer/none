package com.example.demo;

import com.example.demo.dto.OrderRequest;
import com.example.demo.dto.OrderResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
    }

    // 下单业务：带有事务，要么全成，要么全败
    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        // 1. 校验客户是否存在
        Customer customer = customerRepository.findById(request.customerId())
                .orElseThrow(() -> new CustomerNotFoundException(request.customerId()));

        // 2. 创建订单实体
        Order order = new Order(request.description(), request.amount(), customer);

        // 3. 保存
        Order savedOrder = orderRepository.save(order);

        // 4. 转换为 DTO 返回
        return toResponse(savedOrder);
    }

    // 支付业务
    @Transactional
    public OrderResponse payOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getStatus() == OrderStatus.PAID) {
            throw new RuntimeException("Order already paid");
        }

        order.setStatus(OrderStatus.PAID);
        // JPA 的脏检查机制会自动更新数据库，不需要显式调用 save

        return toResponse(order);
    }

    public List<OrderResponse> findOrdersByCustomer(Long customerId) {
        return orderRepository.findByCustomerId(customerId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // 辅助方法：Entity -> DTO
    private OrderResponse toResponse(Order order) {
        return new OrderResponse(
            order.getId(),
            order.getDescription(),
            order.getAmount(),
            order.getStatus(),
            order.getCustomer().getFirstName() + " " + order.getCustomer().getLastName(),
            order.getCreatedAt()
        );
    }
}
