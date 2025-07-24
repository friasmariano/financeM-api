package com.finance.manager.models.requests;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class BudgetRequest {

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    @NotNull(message = "Limit amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Limit amount must be greater than zero")
    private BigDecimal limitAmount;

    @NotNull(message = "User ID is required")
    private Long userId;

    public BudgetRequest() {
    }

    public BudgetRequest(Long categoryId, BigDecimal limitAmount, Long userId) {
        this.categoryId = categoryId;
        this.limitAmount = limitAmount;
        this.userId = userId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public BigDecimal getLimitAmount() {
        return limitAmount;
    }

    public Long getUserId() {
        return userId;
    }
}
