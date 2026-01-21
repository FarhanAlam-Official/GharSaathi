package com.gharsaathi.common.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import com.gharsaathi.property.model.PropertyStatus;
import com.gharsaathi.property.model.PropertyType;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for updating an existing property listing
 * All fields are optional - only provided fields will be updated
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdatePropertyRequest {
    
    @Size(min = 10, max = 255, message = "Title must be between 10 and 255 characters")
    private String title;
    
    @Size(min = 50, max = 5000, message = "Description must be between 50 and 5000 characters")
    private String description;
    
    private PropertyType propertyType;
    
    private PropertyStatus status;
    
    @Size(max = 500, message = "Address must not exceed 500 characters")
    private String address;
    
    @Size(max = 100, message = "City name must not exceed 100 characters")
    private String city;
    
    @Size(max = 100, message = "Area name must not exceed 100 characters")
    private String area;
    
    @DecimalMin(value = "-90.0", message = "Latitude must be between -90 and 90")
    @DecimalMax(value = "90.0", message = "Latitude must be between -90 and 90")
    private Double latitude;
    
    @DecimalMin(value = "-180.0", message = "Longitude must be between -180 and 180")
    @DecimalMax(value = "180.0", message = "Longitude must be between -180 and 180")
    private Double longitude;
    
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @Digits(integer = 8, fraction = 2, message = "Price must be a valid amount")
    private BigDecimal price;
    
    @DecimalMin(value = "0.0", message = "Security deposit must be 0 or greater")
    @Digits(integer = 8, fraction = 2, message = "Security deposit must be a valid amount")
    private BigDecimal securityDeposit;
    
    @Min(value = 0, message = "Bedrooms must be 0 or greater")
    @Max(value = 50, message = "Bedrooms must not exceed 50")
    private Integer bedrooms;
    
    @Min(value = 0, message = "Bathrooms must be 0 or greater")
    @Max(value = 50, message = "Bathrooms must not exceed 50")
    private Integer bathrooms;
    
    @DecimalMin(value = "0.0", inclusive = false, message = "Property area must be greater than 0")
    private Double propertyArea;
    
    private Boolean furnished;
    
    private Boolean parkingAvailable;
    
    private Boolean petsAllowed;
    
    private Set<String> amenities;
    
    private LocalDate availableFrom;
}
