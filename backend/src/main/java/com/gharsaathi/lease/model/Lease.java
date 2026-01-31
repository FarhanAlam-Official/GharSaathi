package com.gharsaathi.lease.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.gharsaathi.auth.model.User;
import com.gharsaathi.property.model.Property;
import com.gharsaathi.rental.application.model.RentalApplication;

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
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing a lease agreement between a landlord and tenant
 */
@Entity
@Table(name = "leases")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lease {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private User tenant;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "landlord_id", nullable = false)
    private User landlord;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", unique = true)
    private RentalApplication application;
    
    @Column(name = "lease_start_date", nullable = false)
    private LocalDate leaseStartDate;
    
    @Column(name = "lease_end_date", nullable = false)
    private LocalDate leaseEndDate;
    
    @Column(name = "monthly_rent", nullable = false, precision = 10, scale = 2)
    private BigDecimal monthlyRent;
    
    @Column(name = "security_deposit", nullable = false, precision = 10, scale = 2)
    private BigDecimal securityDeposit;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private LeaseStatus status = LeaseStatus.ACTIVE;
    
    @Column(name = "number_of_occupants")
    private Integer numberOfOccupants;
    
    @Column(name = "special_terms", columnDefinition = "TEXT")
    private String specialTerms;
    
    @Column(name = "auto_renew")
    @Builder.Default
    private Boolean autoRenew = false;
    
    @Column(name = "early_termination_notice_days")
    @Builder.Default
    private Integer earlyTerminationNoticeDays = 30;
    
    @Column(name = "termination_date")
    private LocalDate terminationDate;
    
    @Column(name = "termination_reason", columnDefinition = "TEXT")
    private String terminationReason;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    @Column(name = "signed_at")
    private LocalDateTime signedAt;
    
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
     * Check if the lease is currently active
     */
    public boolean isActive() {
        return this.status == LeaseStatus.ACTIVE;
    }
    
    /**
     * Check if the lease has expired
     */
    public boolean isExpired() {
        return this.status == LeaseStatus.EXPIRED || 
               (this.status == LeaseStatus.ACTIVE && LocalDate.now().isAfter(this.leaseEndDate));
    }
    
    /**
     * Get the number of days remaining until lease expiration
     * Returns negative if already expired
     */
    public long getDaysRemaining() {
        return ChronoUnit.DAYS.between(LocalDate.now(), this.leaseEndDate);
    }
    
    /**
     * Calculate the total duration of the lease in months
     */
    public long getDurationInMonths() {
        return ChronoUnit.MONTHS.between(this.leaseStartDate, this.leaseEndDate);
    }
    
    /**
     * Check if the lease is about to expire within specified days
     */
    public boolean isExpiringSoon(int daysAhead) {
        long daysRemaining = getDaysRemaining();
        return daysRemaining > 0 && daysRemaining <= daysAhead;
    }
    
    /**
     * Check if the lease can be terminated
     */
    public boolean canBeTerminated() {
        return this.status == LeaseStatus.ACTIVE;
    }
    
    /**
     * Check if the lease can be renewed
     */
    public boolean canBeRenewed() {
        return this.status == LeaseStatus.ACTIVE || this.status == LeaseStatus.EXPIRED;
    }
}
