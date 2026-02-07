package com.gharsaathi.admin.dto;

import java.time.LocalDateTime;

import com.gharsaathi.auth.model.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for admin view of user details
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserDTO {
    
    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private Role role;
    private Boolean enabled;
    private String profilePicture;
    private Boolean emailVerified;
    private Boolean phoneVerified;
    private LocalDateTime lastLogin;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Admin-specific aggregations
    private Long propertyCount;      // For landlords
    private Long applicationCount;   // For tenants
    private Long activeLeaseCount;   // For both
}
