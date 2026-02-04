package com.gharsaathi.payment.exception;

/**
 * Exception thrown when payment operation is invalid
 */
public class InvalidPaymentOperationException extends RuntimeException {
    public InvalidPaymentOperationException(String message) {
        super(message);
    }
}
