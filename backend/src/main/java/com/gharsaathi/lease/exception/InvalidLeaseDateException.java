package com.gharsaathi.lease.exception;

import java.time.LocalDate;

/**
 * Exception thrown when lease dates are invalid
 */
public class InvalidLeaseDateException extends RuntimeException {
    
    public InvalidLeaseDateException(LocalDate startDate, LocalDate endDate) {
        super("Invalid lease dates: start date (" + startDate + 
              ") must be before end date (" + endDate + ")");
    }
    
    public InvalidLeaseDateException(String message) {
        super(message);
    }
}
