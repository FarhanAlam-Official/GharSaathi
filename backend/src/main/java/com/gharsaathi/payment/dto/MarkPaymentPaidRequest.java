package com.gharsaathi.payment.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.gharsaathi.payment.model.PaymentMethod;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for marking a payment as paid by tenant
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarkPaymentPaidRequest {
    
    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;
    
    @NotNull(message = "Payment date is required")
    @PastOrPresent(message = "Payment date cannot be in the future")
    private LocalDate paidDate;
    
    @Size(max = 100, message = "Transaction reference cannot exceed 100 characters")
    private String transactionReference;
    
    @PositiveOrZero(message = "Late fee cannot be negative")
    private BigDecimal lateFee;
    
    @Size(max = 500, message = "Notes cannot exceed 500 characters")
    private String notes;
}
