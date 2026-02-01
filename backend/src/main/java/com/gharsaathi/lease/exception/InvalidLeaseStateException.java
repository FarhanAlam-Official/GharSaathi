package com.gharsaathi.lease.exception;

import com.gharsaathi.lease.model.LeaseStatus;

/**
 * Exception thrown when attempting an operation on a lease in an invalid state
 */
public class InvalidLeaseStateException extends RuntimeException {
    
    public InvalidLeaseStateException(Long leaseId, LeaseStatus currentStatus, String operation) {
        super("Cannot perform '" + operation + "' on lease " + leaseId + 
              " because it is in status: " + currentStatus);
    }
    
    public InvalidLeaseStateException(String message) {
        super(message);
    }
}
