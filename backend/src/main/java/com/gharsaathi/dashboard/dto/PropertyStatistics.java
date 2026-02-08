package com.gharsaathi.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for property statistics used in dashboard
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyStatistics {
    
    private Integer totalProperties;
    private Integer availableProperties;
    private Integer rentedProperties;
    private Integer underMaintenanceProperties;
    private Integer unavailableProperties;
}
