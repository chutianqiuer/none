package com.example.demo;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class OrderScheduler {

    private final OrderRepository orderRepository;
    private final OrderService orderService;

    public OrderScheduler(OrderRepository orderRepository, OrderService orderService) {
        this.orderRepository = orderRepository;
        this.orderService = orderService;
    }

    // 每 10 秒运行一次 (为了演示方便，实际可能几分钟一次)
    @Scheduled(fixedRate = 10000)
    public void cancelUnpaidOrders() {
        // 定义超时规则：30秒没支付就算超时 (为了演示方便)
        LocalDateTime cutoffTime = LocalDateTime.now().minusSeconds(30);

        List<Order> expiredOrders = orderRepository.findByStatusAndCreatedAtBefore(OrderStatus.PENDING, cutoffTime);

        if (!expiredOrders.isEmpty()) {
            System.out.println("监控到 " + expiredOrders.size() + " 个超时未支付订单，正在取消...");
            for (Order order : expiredOrders) {
                try {
                    orderService.cancelOrder(order);
                } catch (Exception e) {
                    System.err.println("取消订单失败: " + order.getId());
                }
            }
        }
    }
}
