package com.gharsaathi.common.dto;

import java.math.BigDecimal;

import com.gharsaathi.property.model.PropertyStatus;
import com.gharsaathi.property.model.PropertyType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for property listing in search results and property lists
 * Contains essential information for displaying properties in a list view
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertyResponse {
    
    private Long id;
    private String title;
    private PropertyType propertyType;
    private PropertyStatus status;
    private String city;
    private String area;
    private BigDecimal price;
    private Integer bedrooms;
    private Integer bathrooms;
    private Double propertyArea;
    private Boolean furnished;
    private String primaryImageUrl;
    
    // Landlord basic info
    private Long landlordId;
    private String landlordName;
    private String landlordEmail;
}
