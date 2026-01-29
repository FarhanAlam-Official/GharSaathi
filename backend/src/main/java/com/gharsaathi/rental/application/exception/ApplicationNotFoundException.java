package com.gharsaathi.rental.application.exception;

/**
 * Exception thrown when a rental application is not found
 */
public class ApplicationNotFoundException extends RuntimeException {
    
    public ApplicationNotFoundException(Long id) {
        super("Application not found with id: " + id);
    }

    public ApplicationNotFoundException(String message) {
        super(message);
    }
}
