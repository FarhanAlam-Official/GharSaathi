package com.gharsaathi.common.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.gharsaathi.property.model.PropertyStatus;
import com.gharsaathi.property.model.PropertyType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for detailed property information
 * Contains complete property details for individual property view
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertyDetailResponse {
    
    // Basic Info
    private Long id;
    private String title;
    private String description;
    private PropertyType propertyType;
    private PropertyStatus status;
    
    // Location
    private String address;
    private String city;
    private String area;
    private Double latitude;
    private Double longitude;
    private String googleMapsUrl;
    
    // Pricing
    private BigDecimal price;
    private BigDecimal securityDeposit;
    
    // Specifications
    private Integer bedrooms;
    private Integer bathrooms;
    private Double propertyArea;
    private Boolean furnished;
    private Boolean parkingAvailable;
    private Boolean petsAllowed;
    
    // Amenities
    private Set<String> amenities;
    
    // Images
    private List<PropertyImageResponse> images;
    
    // Landlord Info
    private LandlordInfo landlord;
    
    // Availability
    private LocalDate availableFrom;
    
    // Metadata
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    /**
     * Nested DTO for property image information
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PropertyImageResponse {
        private Long id;
        private String imageUrl;
        private String mediaType;
        private Boolean isPrimary;
        private Integer displayOrder;
    }
    
    /**
     * Nested DTO for landlord information
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class LandlordInfo {
        private Long id;
        private String fullName;
        private String email;
        private String phoneNumber;
    }
}
