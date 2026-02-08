package com.gharsaathi.dashboard.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.gharsaathi.common.dto.PropertyResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for admin dashboard response with platform-wide statistics
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminDashboardResponse {
    
    // User Statistics
    private Integer totalUsers;
    private Integer totalTenants;
    private Integer totalLandlords;
    private Integer totalAdmins;
    private Integer newUsersThisMonth;
    
    // Property Statistics
    private Integer totalProperties;
    private Integer availableProperties;
    private Integer rentedProperties;
    
    // Lease Statistics
    private Integer totalLeases;
    private Integer activeLeases;
    private Integer expiredLeases;
    private Integer terminatedLeases;
    
    // Payment Statistics
    private Integer totalPayments;
    private Integer pendingPayments;
    private Integer paidPayments;
    private Integer confirmedPayments;
    private Integer overduePayments;
    
    // Financial Statistics
    private BigDecimal platformRevenue; // Total confirmed payments
    private BigDecimal monthlyRevenue; // Current month confirmed
    private BigDecimal totalSecurityDeposits;
    
    // Application Statistics
    private Integer totalApplications;
    private Integer pendingApplications;
    private Integer approvedApplications;
    private Integer rejectedApplications;
    
    // Growth Metrics (last 12 months)
    private Map<String, Integer> userGrowth; // Month -> new users count
    private Map<String, BigDecimal> revenueGrowth; // Month -> revenue
    
    // Top Performers
    private List<PropertyResponse> topProperties; // By applications count
    private List<LandlordInfo> topLandlords; // By revenue
    
    /**
     * Inner class for landlord information
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LandlordInfo {
        private Long landlordId;
        private String landlordName;
        private String landlordEmail;
        private Integer propertiesCount;
        private BigDecimal totalRevenue;
    }
}
