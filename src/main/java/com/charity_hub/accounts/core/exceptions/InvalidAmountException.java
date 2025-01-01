package com.charity_hub.accounts.core.exceptions;

public class InvalidAmountException extends DomainException {
    
    public InvalidAmountException() {
        super("");
    }

    public InvalidAmountException(String message) {
        super(message);
    }
}