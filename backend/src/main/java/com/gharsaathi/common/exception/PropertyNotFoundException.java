package com.gharsaathi.common.exception;

/**
 * Exception thrown when a requested property is not found
 */
public class PropertyNotFoundException extends RuntimeException {
    
    public PropertyNotFoundException(Long id) {
        super("Property not found with id: " + id);
    }
    
    public PropertyNotFoundException(String message) {
        super(message);
    }
}
