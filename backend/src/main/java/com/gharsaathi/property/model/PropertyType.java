package com.gharsaathi.property.model;

/**
 * Enum representing different types of properties available for rent
 */
public enum PropertyType {
    /**
     * Multi-unit residential building with individual apartments
     */
    APARTMENT("Apartment"),
    
    /**
     * Standalone residential house
     */
    HOUSE("House"),
    
    /**
     * Single room in a shared property
     */
    ROOM("Room"),
    
    /**
     * Small, self-contained apartment typically consisting of one main room
     */
    STUDIO("Studio"),
    
    /**
     * Large, luxurious house
     */
    VILLA("Villa"),
    
    /**
     * Commercial property for business use
     */
    COMMERCIAL("Commercial"),
    
    /**
     * Land plot
     */
    LAND("Land"),
    
    /**
     * Other types not listed above
     */
    OTHER("Other");
    
    private final String displayName;
    
    PropertyType(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}
