package com.gharsaathi.common.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import com.gharsaathi.lease.model.LeaseStatus;
import com.gharsaathi.property.model.PropertyType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for lease response with full details
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeaseResponse {
    
    private Long id;
    private PropertyInfo property;
    private TenantInfo tenant;
    private LandlordInfo landlord;
    private Long applicationId;
    private LocalDate leaseStartDate;
    private LocalDate leaseEndDate;
    private BigDecimal monthlyRent;
    private BigDecimal securityDeposit;
    private LeaseStatus status;
    private Integer numberOfOccupants;
    private String specialTerms;
    private Boolean autoRenew;
    private Integer earlyTerminationNoticeDays;
    private LocalDate terminationDate;
    private String terminationReason;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime signedAt;
    
    // Helper fields
    private Long daysRemaining;
    private Long durationInMonths;
    private Boolean isExpiringSoon;
    
    /**
     * Nested DTO for property information
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PropertyInfo {
        private Long id;
        private String title;
        private PropertyType propertyType;
        private String address;
        private String city;
        private String area;
        private Integer bedrooms;
        private Integer bathrooms;
        private Double propertyArea;
        private Boolean furnished;
        private Set<String> amenities;
        private String primaryImageUrl;
    }
    
    /**
     * Nested DTO for tenant information
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TenantInfo {
        private Long id;
        private String fullName;
        private String email;
        private String phoneNumber;
    }
    
    /**
     * Nested DTO for landlord information
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LandlordInfo {
        private Long id;
        private String fullName;
        private String email;
        private String phoneNumber;
    }
}
