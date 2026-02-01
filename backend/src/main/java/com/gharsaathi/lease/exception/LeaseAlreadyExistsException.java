package com.gharsaathi.lease.exception;

/**
 * Exception thrown when attempting to create a lease that already exists
 */
public class LeaseAlreadyExistsException extends RuntimeException {
    
    public LeaseAlreadyExistsException(Long propertyId) {
        super("An active lease already exists for property with id: " + propertyId);
    }
    
    public LeaseAlreadyExistsException(String message) {
        super(message);
    }
}
