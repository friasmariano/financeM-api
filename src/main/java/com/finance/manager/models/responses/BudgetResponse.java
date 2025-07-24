package com.finance.manager.models.responses;

import com.finance.manager.models.Budget;

import java.math.BigDecimal;

public class BudgetResponse {
    private Long id;
    private Long categoryId;
    private String categoryName;
    private BigDecimal limitAmount;
    private Long userId;

    public BudgetResponse() {
    }

    public BudgetResponse(Long id, Long categoryId, String categoryName, BigDecimal limitAmount, Long userId) {
        this.id = id;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.limitAmount = limitAmount;
        this.userId = userId;
    }

    public BudgetResponse(Budget budget) {
        this.id = budget.getId();
        this.categoryId = budget.getCategory().getId();
        this.categoryName = budget.getCategory().getName();
        this.limitAmount = budget.getLimitAmount();
    }

    public Long getId() {
        return id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public BigDecimal getLimitAmount() {
        return limitAmount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setLimitAmount(BigDecimal limitAmount) {
        this.limitAmount = limitAmount;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
