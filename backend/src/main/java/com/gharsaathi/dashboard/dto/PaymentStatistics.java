package com.gharsaathi.dashboard.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for payment statistics used in dashboard
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentStatistics {
    
    // Counts
    private Integer totalPayments;
    private Integer pendingPayments;
    private Integer paidPayments;
    private Integer confirmedPayments;
    private Integer overduePayments;
    private Integer upcomingPayments; // Due in next 30 days
    
    // Amounts
    private BigDecimal totalAmount;
    private BigDecimal pendingAmount;
    private BigDecimal paidAmount;
    private BigDecimal confirmedAmount;
    private BigDecimal overdueAmount;
    private BigDecimal totalLateFees;
}
