package com.gharsaathi.admin.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for suspending/unsuspending user accounts
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SuspendUserRequest {
    
    @NotBlank(message = "Reason is required")
    private String reason;
}
