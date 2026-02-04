package com.gharsaathi.payment.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.gharsaathi.payment.model.PaymentMethod;
import com.gharsaathi.payment.model.PaymentStatus;
import com.gharsaathi.payment.model.PaymentType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for payment details
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    
    private Long id;
    private LeaseInfo lease;
    private TenantInfo tenant;
    private LandlordInfo landlord;
    private PropertyInfo property;
    private PaymentType paymentType;
    private BigDecimal amount;
    private LocalDate dueDate;
    private LocalDate paidDate;
    private PaymentStatus status;
    private PaymentMethod paymentMethod;
    private String transactionReference;
    private String monthYear;
    private String displayMonth;
    private String notes;
    private BigDecimal lateFee;
    private Boolean confirmedByLandlord;
    private LocalDate confirmationDate;
    private Boolean overdue;
    private Integer daysOverdue;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Nested DTO for lease information
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LeaseInfo {
        private Long id;
        private LocalDate startDate;
        private LocalDate endDate;
        private String leaseStatus;
    }

    /**
     * Nested DTO for tenant information
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TenantInfo {
        private Long id;
        private String fullName;
        private String email;
        private String phone;
    }

    /**
     * Nested DTO for landlord information
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LandlordInfo {
        private Long id;
        private String fullName;
        private String email;
        private String phone;
    }

    /**
     * Nested DTO for property information
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PropertyInfo {
        private Long id;
        private String title;
        private String address;
        private String city;
    }
}
