package com.gharsaathi.rental.application.exception;

/**
 * Exception thrown when a user tries to access an application they don't have permission to view
 */
public class ApplicationAccessDeniedException extends RuntimeException {
    
    public ApplicationAccessDeniedException(String message) {
        super(message);
    }

    public ApplicationAccessDeniedException(Long userId, Long applicationId) {
        super(String.format("User with id %d does not have access to application with id %d", 
            userId, applicationId));
    }
}
