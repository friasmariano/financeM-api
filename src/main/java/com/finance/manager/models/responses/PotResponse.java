package com.finance.manager.models.responses;

import java.math.BigDecimal;

public class PotResponse {

    private Long id;
    private String name;
    private BigDecimal goalAmount;
    private BigDecimal currentAmount;
    private Long userId;

    private Long budgetId;

    public PotResponse() {
    }

    public PotResponse(Long id, String name, BigDecimal goalAmount, BigDecimal currentAmount, Long userId, Long budgetId) {
        this.id = id;
        this.name = name;
        this.goalAmount = goalAmount;
        this.currentAmount = currentAmount;
        this.userId = userId;
        this.budgetId = budgetId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getGoalAmount() {
        return goalAmount;
    }

    public void setGoalAmount(BigDecimal goalAmount) {
        this.goalAmount = goalAmount;
    }

    public BigDecimal getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(BigDecimal currentAmount) {
        this.currentAmount = currentAmount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(Long budgetId) {
        this.budgetId = budgetId;
    }
}
