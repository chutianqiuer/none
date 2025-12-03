package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "下单请求参数")
public record OrderRequest(
    @Schema(description = "客户ID", example = "1")
    @NotNull Long customerId,

    @Schema(description = "商品ID", example = "1")
    @NotNull Long productId,

    @Schema(description = "购买数量", example = "2")
    @Positive @NotNull Integer quantity
) {}
