package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record OrderRequest(
    @NotNull Long customerId,
    String description,
    @Positive(message = "Amount must be greater than 0") BigDecimal amount
) {}
