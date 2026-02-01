package com.gharsaathi.lease.exception;

/**
 * Exception thrown when a lease is not found
 */
public class LeaseNotFoundException extends RuntimeException {
    
    public LeaseNotFoundException(Long leaseId) {
        super("Lease not found with id: " + leaseId);
    }
    
    public LeaseNotFoundException(String message) {
        super(message);
    }
}
