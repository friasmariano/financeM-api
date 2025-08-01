
package com.finance.manager.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@Table(name = "pots",
       uniqueConstraints = @UniqueConstraint(name = "uc_pots_userid_name", columnNames = {"user_id", "name"}))

public class Pot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @Column(precision = 10, scale = 2)
    private BigDecimal goalAmount;

    @NotNull
    @Column(precision = 10, scale = 2)
    private BigDecimal currentAmount;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(optional = true)
    @JoinColumn(name = "budget_id", nullable = true)
    private Budget budget;


    public Pot() {

    }

    public Pot(Long id, String name, BigDecimal goalAmount, BigDecimal currentAmount, User user, Budget budget) {
        this.id = id;
        this.name = name;
        this.goalAmount = goalAmount;
        this.currentAmount = currentAmount;
        this.user = user;
        this.budget = budget;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }
}
