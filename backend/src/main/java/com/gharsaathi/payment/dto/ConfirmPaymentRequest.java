package com.gharsaathi.payment.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for landlord confirming a payment
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmPaymentRequest {
    
    @NotNull(message = "Confirmation date is required")
    @PastOrPresent(message = "Confirmation date cannot be in the future")
    private LocalDate confirmationDate;
    
    @Size(max = 500, message = "Notes cannot exceed 500 characters")
    private String notes;
}
