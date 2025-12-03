package com.example.demo;

import com.example.demo.dto.OrderRequest;
import com.example.demo.dto.OrderResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "订单管理", description = "处理下单、支付、查询等操作")
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "创建新订单", description = "扣减库存并生成未支付订单")
    @PostMapping
    public OrderResponse placeOrder(@Valid @RequestBody OrderRequest request) {
        return orderService.createOrder(request);
    }

    @Operation(summary = "支付订单", description = "将订单状态改为已支付")
    @PostMapping("/{id}/pay")
    public OrderResponse payOrder(@PathVariable Long id) {
        return orderService.payOrder(id);
    }

    @Operation(summary = "查询客户订单", description = "根据客户ID获取历史订单列表")
    @GetMapping("/customer/{customerId}")
    public List<OrderResponse> getCustomerOrders(@PathVariable Long customerId) {
        return orderService.findOrdersByCustomer(customerId);
    }
}
