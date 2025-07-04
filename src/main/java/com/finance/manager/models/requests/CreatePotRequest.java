package com.finance.manager.models.requests;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class CreatePotRequest {

    @NotNull
    private String name;

    @NotNull
    private BigDecimal goalAmount;

    @NotNull
    private BigDecimal currentAmount;

    public CreatePotRequest() {
    }

    public CreatePotRequest(String name, BigDecimal goalAmount, BigDecimal currentAmount) {
        this.name = name;
        this.goalAmount = goalAmount;
        this.currentAmount = currentAmount;
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
}
