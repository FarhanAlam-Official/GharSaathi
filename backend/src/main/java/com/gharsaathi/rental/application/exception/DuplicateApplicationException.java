package com.gharsaathi.rental.application.exception;

/**
 * Exception thrown when a user tries to submit a duplicate application
 */
public class DuplicateApplicationException extends RuntimeException {
    
    public DuplicateApplicationException(String message) {
        super(message);
    }

    public DuplicateApplicationException(Long tenantId, Long propertyId) {
        super(String.format("Tenant with id %d already has an active application for property with id %d", 
            tenantId, propertyId));
    }
}
