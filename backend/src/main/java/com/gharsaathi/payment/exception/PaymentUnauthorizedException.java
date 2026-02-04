package com.gharsaathi.payment.exception;

/**
 * Exception thrown when user is unauthorized to perform payment operation
 */
public class PaymentUnauthorizedException extends RuntimeException {
    public PaymentUnauthorizedException(String message) {
        super(message);
    }
}
