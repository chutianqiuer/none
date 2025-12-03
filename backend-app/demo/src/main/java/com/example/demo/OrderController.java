package com.example.demo;

import com.example.demo.dto.OrderRequest;
import com.example.demo.dto.OrderResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // 下单接口
    @PostMapping
    public OrderResponse placeOrder(@Valid @RequestBody OrderRequest request) {
        return orderService.createOrder(request);
    }

    // 支付接口
    @PostMapping("/{id}/pay")
    public OrderResponse payOrder(@PathVariable Long id) {
        return orderService.payOrder(id);
    }

    // 查询某客户的所有订单
    @GetMapping("/customer/{customerId}")
    public List<OrderResponse> getCustomerOrders(@PathVariable Long customerId) {
        return orderService.findOrdersByCustomer(customerId);
    }
}
