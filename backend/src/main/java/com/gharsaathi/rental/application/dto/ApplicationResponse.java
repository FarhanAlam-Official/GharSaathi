package com.gharsaathi.rental.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.gharsaathi.rental.application.model.ApplicationStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for rental application response
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationResponse {

    private Long id;
    private ApplicationStatus status;
    private String message;
    private LocalDate moveInDate;
    private Integer leaseDurationMonths;
    private Integer numberOfOccupants;
    private String employmentStatus;
    private BigDecimal monthlyIncome;
    private Boolean hasPets;
    private String emergencyContactName;
    private String emergencyContactPhone;
    private String landlordResponse;
    private LocalDateTime reviewedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Nested property information
    private PropertyInfo property;

    // Nested tenant information
    private TenantInfo tenant;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PropertyInfo {
        private Long id;
        private String title;
        private String address;
        private String city;
        private BigDecimal price;
        private Integer bedrooms;
        private Integer bathrooms;
        private String propertyType;
        private String primaryImageUrl;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TenantInfo {
        private Long id;
        private String fullName;
        private String email;
        private String phoneNumber;
    }
}
