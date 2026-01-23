package com.gharsaathi.common.exception;

/**
 * Exception thrown when a user attempts to access or modify a property they don't own
 */
public class PropertyAccessDeniedException extends RuntimeException {
    
    public PropertyAccessDeniedException(Long propertyId, Long userId) {
        super("User with id " + userId + " does not have access to property with id " + propertyId);
    }
    
    public PropertyAccessDeniedException(String message) {
        super(message);
    }
}
