package com.gharsaathi.rental.application.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gharsaathi.rental.application.model.ApplicationStatus;
import com.gharsaathi.rental.application.model.RentalApplication;

/**
 * Repository for RentalApplication entity
 */
@Repository
public interface RentalApplicationRepository extends JpaRepository<RentalApplication, Long> {

    /**
     * Find all applications by tenant ID with pagination
     */
    Page<RentalApplication> findByTenantId(Long tenantId, Pageable pageable);

    /**
     * Find all applications for properties owned by a specific landlord
     */
    @Query("SELECT ra FROM RentalApplication ra WHERE ra.property.landlord.id = :landlordId")
    Page<RentalApplication> findByLandlordId(@Param("landlordId") Long landlordId, Pageable pageable);

    /**
     * Find all applications for a specific property with pagination
     */
    Page<RentalApplication> findByPropertyId(Long propertyId, Pageable pageable);

    /**
     * Find all applications by property ID and status
     */
    List<RentalApplication> findByPropertyIdAndStatus(Long propertyId, ApplicationStatus status);

    /**
     * Find applications by tenant ID and status
     */
    List<RentalApplication> findByTenantIdAndStatus(Long tenantId, ApplicationStatus status);

    /**
     * Check if an active application exists for a tenant and property
     * Active means PENDING or APPROVED status
     */
    @Query("SELECT COUNT(ra) > 0 FROM RentalApplication ra " +
           "WHERE ra.tenant.id = :tenantId " +
           "AND ra.property.id = :propertyId " +
           "AND ra.status IN ('PENDING', 'APPROVED')")
    boolean existsActiveApplicationByTenantAndProperty(
        @Param("tenantId") Long tenantId, 
        @Param("propertyId") Long propertyId
    );

    /**
     * Find an application by ID and tenant ID (for access control)
     */
    Optional<RentalApplication> findByIdAndTenantId(Long id, Long tenantId);

    /**
     * Find an application by ID and landlord ID (for access control)
     */
    @Query("SELECT ra FROM RentalApplication ra " +
           "WHERE ra.id = :applicationId " +
           "AND ra.property.landlord.id = :landlordId")
    Optional<RentalApplication> findByIdAndLandlordId(
        @Param("applicationId") Long applicationId, 
        @Param("landlordId") Long landlordId
    );

    /**
     * Count applications by status for a specific tenant
     */
    long countByTenantIdAndStatus(Long tenantId, ApplicationStatus status);

    /**
     * Count applications by status for a specific landlord
     */
    @Query("SELECT COUNT(ra) FROM RentalApplication ra " +
           "WHERE ra.property.landlord.id = :landlordId " +
           "AND ra.status = :status")
    long countByLandlordIdAndStatus(
        @Param("landlordId") Long landlordId, 
        @Param("status") ApplicationStatus status
    );
    
    /**
     * Count total applications by tenant ID
     */
    long countByTenantId(Long tenantId);
    
    /**
     * Count total applications by landlord ID
     */
    @Query("SELECT COUNT(ra) FROM RentalApplication ra WHERE ra.property.landlord.id = :landlordId")
    long countByLandlordId(@Param("landlordId") Long landlordId);
    
    /**
     * Count applications by status
     */
    long countByStatus(ApplicationStatus status);
    
    /**
     * Count total applications by property ID
     */
    long countByPropertyId(Long propertyId);
}
