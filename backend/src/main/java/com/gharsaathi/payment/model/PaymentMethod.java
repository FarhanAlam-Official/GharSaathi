package com.gharsaathi.payment.model;

/**
 * Enum representing payment methods available in Nepal
 */
public enum PaymentMethod {
    /**
     * Cash payment
     */
    CASH,
    
    /**
     * Bank transfer/deposit
     */
    BANK_TRANSFER,
    
    /**
     * eSewa digital wallet
     */
    ESEWA,
    
    /**
     * Khalti digital wallet
     */
    KHALTI,
    
    /**
     * IME Pay digital wallet
     */
    IME_PAY,
    
    /**
     * ConnectIPS inter-bank payment
     */
    CONNECT_IPS,
    
    /**
     * Other payment methods
     */
    OTHER
}
