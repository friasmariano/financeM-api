package com.finance.manager.exceptions;

public class PotLimitException extends RuntimeException {
    public PotLimitException(String message) {
        super(message);
    }
}
