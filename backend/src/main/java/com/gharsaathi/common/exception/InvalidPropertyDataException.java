package com.gharsaathi.common.exception;

/**
 * Exception thrown when property data validation fails
 */
public class InvalidPropertyDataException extends RuntimeException {
    
    public InvalidPropertyDataException(String message) {
        super(message);
    }
    
    public InvalidPropertyDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
