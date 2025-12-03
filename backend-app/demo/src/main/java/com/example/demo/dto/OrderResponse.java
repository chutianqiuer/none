package com.example.demo.dto;
import com.example.demo.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderResponse(
    Long orderId,
    String customerName,
    String productName,
    Integer quantity,
    BigDecimal totalPrice,
    OrderStatus status,
    LocalDateTime createdAt
) {}
