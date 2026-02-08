package com.gharsaathi.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for lease statistics used in dashboard
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeaseStatistics {
    
    private Integer totalLeases;
    private Integer activeLeases;
    private Integer expiredLeases;
    private Integer terminatedLeases;
    private Integer expiringLeases; // Expiring in next 30 days
}
