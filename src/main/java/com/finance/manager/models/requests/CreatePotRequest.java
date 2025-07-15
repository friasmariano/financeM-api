package com.finance.manager.models.requests;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class CreatePotRequest {

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;

    @NotNull(message = "Goal amount is required")
    @Digits(integer = 8, fraction = 2, message = "Goal amount must be a number with up to 8 digits and 2 decimal places")
    @DecimalMax(value = "99999999.99", message = "Goal amount must not exceed 99,999,999.99")
    private BigDecimal goalAmount;

    @NotNull(message = "Current amount is required")
    @Digits(integer = 8, fraction = 2, message = "Current amount must be a number with up to 8 digits and 2 decimal places")
    @DecimalMax(value = "99999999.99", message = "Current amount must not exceed 99,999,999.99")
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
