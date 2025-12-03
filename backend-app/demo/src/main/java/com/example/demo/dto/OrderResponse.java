package com.example.demo.dto;

import com.example.demo.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderResponse(
    Long orderId,
    String description,
    BigDecimal amount,
    OrderStatus status,
    String customerName,
    LocalDateTime createdAt
) {}
