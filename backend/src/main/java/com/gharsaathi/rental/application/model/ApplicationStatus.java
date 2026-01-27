package com.gharsaathi.rental.application.model;

/**
 * Enum representing the status of a rental application
 */
public enum ApplicationStatus {
    /**
     * Application has been submitted and is awaiting landlord review
     */
    PENDING,
    
    /**
     * Application has been approved by the landlord
     */
    APPROVED,
    
    /**
     * Application has been rejected by the landlord
     */
    REJECTED,
    
    /**
     * Application has been withdrawn by the tenant
     */
    WITHDRAWN,
    
    /**
     * Application has been cancelled (system or admin action)
     */
    CANCELLED
}
