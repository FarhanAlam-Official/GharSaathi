package com.gharsaathi.rental.application.exception;

/**
 * Exception thrown when an operation is attempted on an application in an invalid state
 */
public class InvalidApplicationStateException extends RuntimeException {
    
    public InvalidApplicationStateException(String message) {
        super(message);
    }

    public InvalidApplicationStateException(String currentState, String requiredState) {
        super(String.format("Invalid application state. Current: %s, Required: %s", 
            currentState, requiredState));
    }
}
