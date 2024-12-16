package com.charity_hub.core.exceptions;

/**
 * Base class for domain exceptions.
 */
public abstract class DomainException extends RuntimeException {
    
    protected DomainException(String message) {
        super(message);
    }
}