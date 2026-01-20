package com.gharsaathi.property.model;

/**
 * Enum representing the current status of a property
 */
public enum PropertyStatus {
    /**
     * Property is available for rent
     */
    AVAILABLE("Available"),
    
    /**
     * Property is currently rented
     */
    RENTED("Rented"),
    
    /**
     * Rental application is pending approval
     */
    PENDING("Pending"),
    
    /**
     * Property is under maintenance
     */
    MAINTENANCE("Maintenance"),
    
    /**
     * Property is temporarily unavailable
     */
    UNAVAILABLE("Unavailable");
    
    private final String displayName;
    
    PropertyStatus(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}
