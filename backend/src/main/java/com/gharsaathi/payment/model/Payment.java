package com.gharsaathi.payment.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.gharsaathi.auth.model.User;
import com.gharsaathi.lease.model.Lease;
import com.gharsaathi.property.model.Property;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing a payment transaction between tenant and landlord
 */
@Entity
@Table(name = "payments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lease_id", nullable = false)
    private Lease lease;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private User tenant;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "landlord_id", nullable = false)
    private User landlord;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type", nullable = false, length = 20)
    private PaymentType paymentType;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;
    
    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;
    
    @Column(name = "paid_date")
    private LocalDate paidDate;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private PaymentStatus status = PaymentStatus.PENDING;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", length = 50)
    private PaymentMethod paymentMethod;
    
    @Column(name = "transaction_reference", length = 100)
    private String transactionReference;
    
    @Column(name = "month_year", nullable = false, length = 7)
    private String monthYear; // Format: "2026-02"
    
    @Column(columnDefinition = "TEXT")
    private String notes;
    
    @Column(name = "late_fee", precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal lateFee = BigDecimal.ZERO;
    
    @Column(name = "confirmed_by_landlord")
    @Builder.Default
    private Boolean confirmedByLandlord = false;
    
    @Column(name = "confirmation_date")
    private LocalDateTime confirmationDate;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Helper Methods
    
    /**
     * Check if payment is overdue
     */
    public boolean isOverdue() {
        return this.status == PaymentStatus.OVERDUE || 
               (this.status == PaymentStatus.PENDING && LocalDate.now().isAfter(this.dueDate));
    }
    
    /**
     * Check if payment is pending
     */
    public boolean isPending() {
        return this.status == PaymentStatus.PENDING;
    }
    
    /**
     * Check if payment is confirmed by landlord
     */
    public boolean isConfirmed() {
        return this.status == PaymentStatus.CONFIRMED;
    }
    
    /**
     * Check if payment has been paid (by tenant)
     */
    public boolean isPaid() {
        return this.status == PaymentStatus.PAID || this.status == PaymentStatus.CONFIRMED;
    }
    
    /**
     * Calculate number of days overdue
     * Returns 0 if not overdue
     */
    public long getDaysOverdue() {
        if (this.dueDate == null || !LocalDate.now().isAfter(this.dueDate)) {
            return 0;
        }
        return ChronoUnit.DAYS.between(this.dueDate, LocalDate.now());
    }
    
    /**
     * Calculate late fee based on days overdue
     * Simple calculation: 2% per month of amount
     * This is a placeholder - actual calculation should be configurable
     */
    public BigDecimal calculateLateFee() {
        long daysOverdue = getDaysOverdue();
        if (daysOverdue <= 0) {
            return BigDecimal.ZERO;
        }
        
        // 2% per month, pro-rated daily
        BigDecimal monthlyRate = new BigDecimal("0.02");
        BigDecimal dailyRate = monthlyRate.divide(new BigDecimal("30"), 6, BigDecimal.ROUND_HALF_UP);
        BigDecimal lateFeeAmount = this.amount.multiply(dailyRate).multiply(new BigDecimal(daysOverdue));
        
        return lateFeeAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
    
    /**
     * Check if payment can be marked as paid by tenant
     */
    public boolean canBePaid() {
        return this.status == PaymentStatus.PENDING || this.status == PaymentStatus.OVERDUE;
    }
    
    /**
     * Check if payment can be confirmed by landlord
     */
    public boolean canBeConfirmed() {
        return this.status == PaymentStatus.PAID;
    }
    
    /**
     * Check if payment can be cancelled
     */
    public boolean canBeCancelled() {
        return this.status == PaymentStatus.PENDING && LocalDate.now().isBefore(this.dueDate);
    }
    
    /**
     * Get display text for month/year
     */
    public String getDisplayMonth() {
        if (this.paymentType == PaymentType.SECURITY_DEPOSIT) {
            return "Security Deposit";
        }
        // Format: "February 2026" from "2026-02"
        if (this.monthYear != null && this.monthYear.length() == 7) {
            String[] parts = this.monthYear.split("-");
            if (parts.length == 2) {
                int month = Integer.parseInt(parts[1]);
                String[] months = {"", "January", "February", "March", "April", "May", "June",
                                 "July", "August", "September", "October", "November", "December"};
                return months[month] + " " + parts[0];
            }
        }
        return this.monthYear;
    }
}
