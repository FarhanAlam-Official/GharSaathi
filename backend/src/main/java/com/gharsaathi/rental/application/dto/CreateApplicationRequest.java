package com.gharsaathi.rental.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for creating a new rental application
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateApplicationRequest {

    @NotNull(message = "Property ID is required")
    @Positive(message = "Property ID must be positive")
    private Long propertyId;

    @Size(max = 2000, message = "Message must not exceed 2000 characters")
    private String message;

    @NotNull(message = "Move-in date is required")
    @FutureOrPresent(message = "Move-in date must be today or in the future")
    private LocalDate moveInDate;

    @NotNull(message = "Lease duration is required")
    @Min(value = 1, message = "Lease duration must be at least 1 month")
    private Integer leaseDurationMonths;

    @NotNull(message = "Number of occupants is required")
    @Min(value = 1, message = "Number of occupants must be at least 1")
    private Integer numberOfOccupants;

    @NotBlank(message = "Employment status is required")
    @Size(max = 100, message = "Employment status must not exceed 100 characters")
    private String employmentStatus;

    @NotNull(message = "Monthly income is required")
    @Positive(message = "Monthly income must be positive")
    private BigDecimal monthlyIncome;

    @NotNull(message = "Pet information is required")
    private Boolean hasPets;

    @NotBlank(message = "Emergency contact name is required")
    @Size(max = 100, message = "Emergency contact name must not exceed 100 characters")
    private String emergencyContactName;

    @NotBlank(message = "Emergency contact phone is required")
    @Size(max = 20, message = "Emergency contact phone must not exceed 20 characters")
    private String emergencyContactPhone;
}
