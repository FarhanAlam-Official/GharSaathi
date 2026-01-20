package com.gharsaathi.property.model;

/**
 * Enum representing various amenities that a property may offer
 */
public enum AmenityType {
    // Internet & Entertainment
    WIFI("WiFi"),
    CABLE_TV("Cable TV"),
    
    // Climate Control
    AIR_CONDITIONING("Air Conditioning"),
    HEATING("Heating"),
    CEILING_FAN("Ceiling Fan"),
    
    // Appliances
    WASHING_MACHINE("Washing Machine"),
    DRYER("Dryer"),
    DISHWASHER("Dishwasher"),
    REFRIGERATOR("Refrigerator"),
    MICROWAVE("Microwave"),
    OVEN("Oven"),
    
    // Facilities
    GYM("Gym"),
    SWIMMING_POOL("Swimming Pool"),
    GARDEN("Garden"),
    BALCONY("Balcony"),
    TERRACE("Terrace"),
    ELEVATOR("Elevator"),
    
    // Security & Parking
    SECURITY_GUARD("24/7 Security"),
    CCTV("CCTV Surveillance"),
    PARKING("Parking Space"),
    COVERED_PARKING("Covered Parking"),
    
    // Utilities
    WATER_SUPPLY("24/7 Water Supply"),
    BACKUP_POWER("Power Backup"),
    GAS_CONNECTION("Gas Connection"),
    
    // Other
    FURNISHED("Fully Furnished"),
    SEMI_FURNISHED("Semi Furnished"),
    PET_FRIENDLY("Pet Friendly"),
    WHEELCHAIR_ACCESSIBLE("Wheelchair Accessible");
    
    private final String displayName;
    
    AmenityType(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}
