package com.finance.manager.models.responses;

import java.math.BigDecimal;

public class PotResponse {

    private Long id;
    private String name;
    private BigDecimal goalAmount;
    private BigDecimal currentAmount;
    private Long userId;

    public PotResponse() {
    }

    public PotResponse(Long id, String name, BigDecimal goalAmount, BigDecimal currentAmount, Long userId) {
        this.id = id;
        this.name = name;
        this.goalAmount = goalAmount;
        this.currentAmount = currentAmount;
        this.userId = userId;
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
}
