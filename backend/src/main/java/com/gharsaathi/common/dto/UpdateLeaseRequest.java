package com.gharsaathi.common.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for updating lease terms (optional fields)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateLeaseRequest {
    
    @Size(max = 5000, message = "Special terms cannot exceed 5000 characters")
    private String specialTerms;
    
    private Boolean autoRenew;
    
    @Positive(message = "Early termination notice days must be positive")
    private Integer earlyTerminationNoticeDays;
}
