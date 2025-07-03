package com.finance.manager.exceptions;

public class PotNameAlreadyUsedException extends RuntimeException {
    public PotNameAlreadyUsedException(String message) {
        super(message);
    }
}
