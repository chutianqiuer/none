package com.example.demo.dto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderRequest(
    @NotNull Long customerId,
    @NotNull Long productId,
    @Positive @NotNull Integer quantity
) {}
