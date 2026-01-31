package com.gharsaathi.common.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for terminating a lease early
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TerminateLeaseRequest {
    
    @NotNull(message = "Termination date is required")
    private LocalDate terminationDate;
    
    @NotBlank(message = "Termination reason is required")
    @Size(min = 10, max = 1000, message = "Termination reason must be between 10 and 1000 characters")
    private String terminationReason;
}
