package com.finance.manager.exceptions;

public class PotNotFoundException extends RuntimeException {
    public PotNotFoundException(String message) {
        super(message);
    }
}
