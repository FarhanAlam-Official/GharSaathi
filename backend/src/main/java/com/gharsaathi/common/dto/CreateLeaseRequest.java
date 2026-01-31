package com.gharsaathi.common.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for creating a manual lease (by landlord or admin)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateLeaseRequest {
    
    @NotNull(message = "Property ID is required")
    private Long propertyId;
    
    @NotNull(message = "Tenant ID is required")
    private Long tenantId;
    
    @NotNull(message = "Lease start date is required")
    private LocalDate leaseStartDate;
    
    @NotNull(message = "Lease end date is required")
    @Future(message = "Lease end date must be in the future")
    private LocalDate leaseEndDate;
    
    @NotNull(message = "Monthly rent is required")
    @Positive(message = "Monthly rent must be positive")
    private BigDecimal monthlyRent;
    
    @NotNull(message = "Security deposit is required")
    @PositiveOrZero(message = "Security deposit must be zero or positive")
    private BigDecimal securityDeposit;
    
    @Positive(message = "Number of occupants must be positive")
    private Integer numberOfOccupants;
    
    @Size(max = 5000, message = "Special terms cannot exceed 5000 characters")
    private String specialTerms;
    
    private Boolean autoRenew;
    
    @Positive(message = "Early termination notice days must be positive")
    private Integer earlyTerminationNoticeDays;
}
