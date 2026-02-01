package com.gharsaathi.lease.exception;

/**
 * Exception thrown when a user attempts to access or modify a lease they don't have permission for
 */
public class LeaseAccessDeniedException extends RuntimeException {
    
    public LeaseAccessDeniedException(Long leaseId, Long userId) {
        super("User with id " + userId + " does not have access to lease with id: " + leaseId);
    }
    
    public LeaseAccessDeniedException(String message) {
        super(message);
    }
}
