package com.gharsaathi.common.exception;

/**
 * Exception thrown when attempting to modify a property that is already rented
 */
public class PropertyAlreadyRentedException extends RuntimeException {
    
    public PropertyAlreadyRentedException(Long propertyId) {
        super("Property with id " + propertyId + " is already rented and cannot be modified");
    }
    
    public PropertyAlreadyRentedException(String message) {
        super(message);
    }
}
