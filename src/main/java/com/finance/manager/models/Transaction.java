package com.finance.manager.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String description;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private Instant date;

    @OneToOne
    @JoinColumn(name = "user_id")
    public User user;

    @OneToOne
    @JoinColumn(name = "budget_id")
    public Budget budget;

    @OneToOne
    @JoinColumn(name = "pot_id")
    public Pot pot;

    public Transaction() {}

    public Transaction(Long id, String description, BigDecimal amount, Instant date, User user, Budget budget, Pot pot) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.user = user;
        this.budget = budget;
        this.pot = pot;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
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

    public Pot getPot() {
        return pot;
    }

    public void setPot(Pot pot) {
        this.pot = pot;
    }
}
