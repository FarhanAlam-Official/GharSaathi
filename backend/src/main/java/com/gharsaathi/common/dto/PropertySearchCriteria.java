package com.gharsaathi.common.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import com.gharsaathi.property.model.PropertyStatus;
import com.gharsaathi.property.model.PropertyType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for property search and filtering criteria
 * Used to build dynamic queries for property search
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertySearchCriteria {
    
    // Location filters
    private String city;
    private String area;
    
    // Property type filter
    private PropertyType propertyType;
    
    // Status filter (default: AVAILABLE)
    private PropertyStatus status;
    
    // Price range
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    
    // Bedroom range
    private Integer minBedrooms;
    private Integer maxBedrooms;
    
    // Bathroom range
    private Integer minBathrooms;
    private Integer maxBathrooms;
    
    // Area range (square feet)
    private Double minPropertyArea;
    private Double maxPropertyArea;
    
    // Boolean filters
    private Boolean furnished;
    private Boolean parkingAvailable;
    private Boolean petsAllowed;
    
    // Amenities filter (property must have ALL specified amenities)
    private Set<String> amenities;
    
    // Availability filter
    private LocalDate availableFrom;
    
    // Search keyword (searches in title and description)
    private String keyword;
    
    // Landlord filter (for landlord-specific queries)
    private Long landlordId;
    
    // Pagination and sorting
    @Builder.Default
    private Integer page = 0;
    
    @Builder.Default
    private Integer size = 10;
    
    @Builder.Default
    private String sortBy = "createdAt"; // createdAt, price, bedrooms, propertyArea
    
    @Builder.Default
    private String sortDirection = "DESC"; // ASC or DESC
}
