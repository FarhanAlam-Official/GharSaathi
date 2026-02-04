package com.gharsaathi.payment.exception;

/**
 * Exception thrown when payment generation fails
 */
public class PaymentGenerationException extends RuntimeException {
    public PaymentGenerationException(String message) {
        super(message);
    }
    
    public PaymentGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}
