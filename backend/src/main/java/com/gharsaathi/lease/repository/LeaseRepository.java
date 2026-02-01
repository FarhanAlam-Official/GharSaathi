package com.gharsaathi.lease.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gharsaathi.lease.model.Lease;
import com.gharsaathi.lease.model.LeaseStatus;

/**
 * Repository interface for Lease entity operations
 */
@Repository
public interface LeaseRepository extends JpaRepository<Lease, Long> {
    
    /**
     * Find lease by the original rental application ID
     */
    Optional<Lease> findByApplicationId(Long applicationId);
    
    /**
     * Find all leases for a specific property
     */
    List<Lease> findByPropertyId(Long propertyId);
    
    /**
     * Find leases for a property with specific status
     */
    List<Lease> findByPropertyIdAndStatus(Long propertyId, LeaseStatus status);
    
    /**
     * Find all leases for a tenant
     */
    List<Lease> findByTenantId(Long tenantId);
    
    /**
     * Find leases for a tenant with specific status
     */
    List<Lease> findByTenantIdAndStatus(Long tenantId, LeaseStatus status);
    
    /**
     * Find all leases managed by a landlord
     */
    List<Lease> findByLandlordId(Long landlordId);
    
    /**
     * Find leases for a landlord with specific status
     */
    List<Lease> findByLandlordIdAndStatus(Long landlordId, LeaseStatus status);
    
    /**
     * Find leases for a tenant with pagination
     */
    Page<Lease> findByTenantId(Long tenantId, Pageable pageable);
    
    /**
     * Find leases for a landlord with pagination
     */
    Page<Lease> findByLandlordId(Long landlordId, Pageable pageable);
    
    /**
     * Find leases with specific status that have end date before given date
     * Useful for finding expired leases
     */
    List<Lease> findByStatusAndLeaseEndDateBefore(LeaseStatus status, LocalDate date);
    
    /**
     * Find leases with specific status expiring between two dates
     * Useful for finding leases expiring soon
     */
    List<Lease> findByStatusAndLeaseEndDateBetween(LeaseStatus status, LocalDate startDate, LocalDate endDate);
    
    /**
     * Check if an active lease exists for a property
     */
    boolean existsByPropertyIdAndStatus(Long propertyId, LeaseStatus status);
    
    /**
     * Find lease with full details (eager fetch relationships)
     */
    @Query("SELECT l FROM Lease l " +
           "JOIN FETCH l.property p " +
           "JOIN FETCH l.tenant t " +
           "JOIN FETCH l.landlord ll " +
           "WHERE l.id = :leaseId")
    Optional<Lease> findByIdWithDetails(@Param("leaseId") Long leaseId);
    
    /**
     * Find all leases for landlord with eager fetch
     */
    @Query("SELECT l FROM Lease l " +
           "JOIN FETCH l.property p " +
           "JOIN FETCH l.tenant t " +
           "WHERE l.landlord.id = :landlordId " +
           "ORDER BY l.createdAt DESC")
    List<Lease> findByLandlordIdWithDetails(@Param("landlordId") Long landlordId);
    
    /**
     * Find all leases for tenant with eager fetch
     */
    @Query("SELECT l FROM Lease l " +
           "JOIN FETCH l.property p " +
           "JOIN FETCH l.landlord ll " +
           "WHERE l.tenant.id = :tenantId " +
           "ORDER BY l.createdAt DESC")
    List<Lease> findByTenantIdWithDetails(@Param("tenantId") Long tenantId);
}
