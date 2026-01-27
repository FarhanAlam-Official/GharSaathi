package com.gharsaathi.rental.application.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.gharsaathi.auth.model.User;
import com.gharsaathi.property.model.Property;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
 * Entity representing a rental application submitted by a tenant for a property
 */
@Entity
@Table(name = "rental_applications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RentalApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @ManyToOne
    @JoinColumn(name = "tenant_id", nullable = false)
    private User tenant;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private ApplicationStatus status = ApplicationStatus.PENDING;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Column(name = "move_in_date")
    private LocalDate moveInDate;

    @Column(name = "lease_duration_months")
    private Integer leaseDurationMonths;

    @Column(name = "number_of_occupants")
    private Integer numberOfOccupants;

    @Column(name = "employment_status", length = 100)
    private String employmentStatus;

    @Column(name = "monthly_income", precision = 10, scale = 2)
    private BigDecimal monthlyIncome;

    @Column(name = "has_pets")
    @Builder.Default
    private Boolean hasPets = false;

    @Column(name = "emergency_contact_name", length = 100)
    private String emergencyContactName;

    @Column(name = "emergency_contact_phone", length = 20)
    private String emergencyContactPhone;

    @Column(name = "landlord_response", columnDefinition = "TEXT")
    private String landlordResponse;

    @Column(name = "reviewed_at")
    private LocalDateTime reviewedAt;

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

    /**
     * Check if application is in a state that can be modified
     */
    public boolean isPending() {
        return status == ApplicationStatus.PENDING;
    }

    /**
     * Check if application is in a final state (approved, rejected, withdrawn, cancelled)
     */
    public boolean isFinal() {
        return status == ApplicationStatus.APPROVED || 
               status == ApplicationStatus.REJECTED || 
               status == ApplicationStatus.WITHDRAWN || 
               status == ApplicationStatus.CANCELLED;
    }

    /**
     * Check if application is active (pending or approved)
     */
    public boolean isActive() {
        return status == ApplicationStatus.PENDING || status == ApplicationStatus.APPROVED;
    }
}
