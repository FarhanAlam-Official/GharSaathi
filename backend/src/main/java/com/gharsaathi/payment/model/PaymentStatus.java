package com.gharsaathi.payment.model;

/**
 * Enum representing the various states of a payment
 */
public enum PaymentStatus {
    /**
     * Payment is due but not yet paid
     */
    PENDING,
    
    /**
     * Tenant has marked payment as paid
     */
    PAID,
    
    /**
     * Landlord has confirmed payment received
     */
    CONFIRMED,
    
    /**
     * Payment is past due date and not paid
     */
    OVERDUE,
    
    /**
     * Partial payment received (future feature)
     */
    PARTIALLY_PAID,
    
    /**
     * Payment cancelled (e.g., lease terminated)
     */
    CANCELLED
}
