package com.gharsaathi.dashboard.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.gharsaathi.common.dto.LeaseResponse;
import com.gharsaathi.common.dto.PropertyResponse;
import com.gharsaathi.rental.application.dto.ApplicationResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for landlord dashboard response
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LandlordDashboardResponse {
    
    // Property Overview
    private Integer totalProperties;
    private Integer availableProperties;
    private Integer rentedProperties;
    private Integer underMaintenanceProperties;
    
    // Lease Overview
    private Integer activeLeases;
    private Integer expiringLeases; // Next 30 days
    
    // Application Overview
    private Integer pendingApplications;
    private Integer totalApplicationsThisMonth;
    
    // Financial Overview
    private BigDecimal monthlyRentalIncome; // From active leases
    private BigDecimal expectedMonthlyIncome; // Same as above
    private BigDecimal totalRevenue; // All confirmed payments
    private Double paymentCollectionRate; // Percentage of confirmed payments
    
    // Recent Activities
    private List<PropertyResponse> topProperties; // By applications count
    private List<ApplicationResponse> recentApplications; // Last 5
    private List<LeaseResponse> expiringLeasesList; // Next 30 days
    
    // Revenue Breakdown
    private Map<String, BigDecimal> revenueByProperty; // Property title -> revenue
    private Map<String, BigDecimal> revenueByMonth; // Month -> revenue (last 6 months)
}
