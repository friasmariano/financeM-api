package com.finance.manager.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@Table(name = "pots")
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

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Pot() {

    }

    public Pot(Long id, String name, BigDecimal goalAmount, BigDecimal currentAmount, User user) {
        this.id = id;
        this.name = name;
        this.goalAmount = goalAmount;
        this.currentAmount = currentAmount;
        this.user = user;
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
}
