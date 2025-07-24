package com.finance.manager.exceptions;

public class BudgetCategoryNotFoundException extends RuntimeException {
    public BudgetCategoryNotFoundException(String message) {
        super(message);
    }
}
