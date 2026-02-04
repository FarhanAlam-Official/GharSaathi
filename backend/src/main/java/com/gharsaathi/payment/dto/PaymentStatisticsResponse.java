package com.gharsaathi.payment.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for payment statistics
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentStatisticsResponse {
    
    // Count statistics
    private Integer totalPayments;
    private Integer pendingPayments;
    private Integer paidPayments;
    private Integer confirmedPayments;
    private Integer overduePayments;
    
    // Amount statistics
    private BigDecimal totalAmount;
    private BigDecimal pendingAmount;
    private BigDecimal paidAmount;
    private BigDecimal confirmedAmount;
    private BigDecimal overdueAmount;
    private BigDecimal totalLateFees;
}
