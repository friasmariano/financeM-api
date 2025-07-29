package com.finance.manager.exceptions;

public class BudgetNameAlreadyExistsForUser extends RuntimeException {
    public BudgetNameAlreadyExistsForUser(String message) {
        super(message);
    }
}
