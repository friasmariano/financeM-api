package com.finance.manager.models.requests;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class BudgetRequest {

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    @NotNull
    @Size(max = 100)
    private String name;

    @NotNull(message = "Limit amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Limit amount must be greater than zero")
    private BigDecimal limitAmount;


    public BudgetRequest() {
    }

    public BudgetRequest(Long categoryId, BigDecimal limitAmount, Long userId, String name) {
        this.categoryId = categoryId;
        this.limitAmount = limitAmount;
        this.name = name;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public BigDecimal getLimitAmount() {
        return limitAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
