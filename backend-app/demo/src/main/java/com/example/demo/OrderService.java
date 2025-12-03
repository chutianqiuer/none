package com.example.demo;

import com.example.demo.dto.OrderRequest;
import com.example.demo.dto.OrderResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepo;
    private final CustomerRepository customerRepo;
    private final ProductRepository productRepo;

    public OrderService(OrderRepository orderRepo, CustomerRepository customerRepo, ProductRepository productRepo) {
        this.orderRepo = orderRepo;
        this.customerRepo = customerRepo;
        this.productRepo = productRepo;
    }

    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        Customer customer = customerRepo.findById(request.customerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Product product = productRepo.findById(request.productId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // 1. 扣减库存 (如果库存不足会抛异常，如果并发冲突乐观锁会抛异常)
        product.decreaseStock(request.quantity());
        productRepo.save(product); 

        // 2. 创建订单
        Order order = new Order(customer, product, request.quantity());
        Order savedOrder = orderRepo.save(order);

        return toResponse(savedOrder);
    }

    // 支付
    @Transactional
    public OrderResponse payOrder(Long orderId) {
        Order order = orderRepo.findById(orderId).orElseThrow();
        if (order.getStatus() != OrderStatus.PENDING) throw new RuntimeException("Invalid order status");

        order.setStatus(OrderStatus.PAID);
        return toResponse(order);
    }

    // 取消订单 (并回滚库存)
    @Transactional
    public void cancelOrder(Order order) {
        order.setStatus(OrderStatus.CANCELLED);
        orderRepo.save(order);

        // 恢复库存
        Product product = order.getProduct();
        product.increaseStock(order.getQuantity());
        productRepo.save(product);

        System.out.println("订单 " + order.getId() + " 已取消，库存已恢复。");
    }

    public List<OrderResponse> findOrdersByCustomer(Long customerId) {
        return orderRepo.findByCustomerId(customerId).stream().map(this::toResponse).collect(Collectors.toList());
    }

    private OrderResponse toResponse(Order o) {
        return new OrderResponse(o.getId(), o.getCustomer().getFirstName(), o.getProduct().getName(),
                o.getQuantity(), o.getProduct().getPrice().multiply(java.math.BigDecimal.valueOf(o.getQuantity())),
                o.getStatus(), o.getCreatedAt());
    }
}
