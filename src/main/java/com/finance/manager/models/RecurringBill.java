package com.finance.manager.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "recurringBills")
public class RecurringBill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private BigDecimal amount;

    @NotNull
    @Future(message = "Date must be in the future")
    private LocalDateTime dueDay;

    @NotNull
    private Boolean paidThisMonth;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    public RecurringBill() {
    }

    public RecurringBill(Long id, String name, BigDecimal amount, LocalDateTime dueDay, Boolean paidThisMonth, User user) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.dueDay = dueDay;
        this.paidThisMonth = paidThisMonth;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getDueDay() {
        return dueDay;
    }

    public void setDueDay(LocalDateTime dueDay) {
        this.dueDay = dueDay;
    }

    public Boolean getPaidThisMonth() {
        return paidThisMonth;
    }

    public void setPaidThisMonth(Boolean paidThisMonth) {
        this.paidThisMonth = paidThisMonth;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
