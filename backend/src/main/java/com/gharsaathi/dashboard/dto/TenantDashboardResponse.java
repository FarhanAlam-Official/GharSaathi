package com.gharsaathi.dashboard.dto;

import java.math.BigDecimal;
import java.util.List;

import com.gharsaathi.common.dto.LeaseResponse;
import com.gharsaathi.payment.dto.PaymentResponse;
import com.gharsaathi.rental.application.dto.ApplicationResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for tenant dashboard response
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TenantDashboardResponse {
    
    // Overview Statistics
    private Integer activeLeases;
    private Integer totalApplications;
    private Integer pendingApplications;
    private Integer approvedApplications;
    private Integer rejectedApplications;
    private Integer upcomingPayments;
    private Integer overduePayments;
    private Integer savedProperties; // For future favorites feature
    
    // Financial Summary
    private BigDecimal totalAmountPaid;
    private BigDecimal totalAmountPending;
    private BigDecimal totalAmountOverdue;
    
    // Recent Activities (Last 5 items)
    private List<ApplicationResponse> recentApplications;
    private List<PaymentResponse> upcomingPaymentsList;
    private List<LeaseResponse> activeLeasesList;
}
