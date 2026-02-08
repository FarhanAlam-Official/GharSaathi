package com.gharsaathi.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for application statistics used in dashboard
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationStatistics {
    
    private Integer totalApplications;
    private Integer pendingApplications;
    private Integer approvedApplications;
    private Integer rejectedApplications;
    private Integer withdrawnApplications;
}
