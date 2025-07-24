package com.finance.manager.exceptions;

public class BudgetCategoryAlreadyExists extends RuntimeException {
    public BudgetCategoryAlreadyExists(String message) {
        super(message);
    }
}
