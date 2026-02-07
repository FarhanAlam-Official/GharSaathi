package com.gharsaathi.admin.dto;

import com.gharsaathi.auth.model.Role;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for changing user role
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangeRoleRequest {
    
    @NotNull(message = "New role is required")
    private Role newRole;
    
    private String reason;
}
