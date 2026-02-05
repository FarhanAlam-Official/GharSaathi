package com.gharsaathi.payment.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gharsaathi.payment.model.Payment;
import com.gharsaathi.payment.model.PaymentStatus;

/**
 * Repository interface for Payment entity operations
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    
    /**
     * Find all payments for a specific lease
     */
    List<Payment> findByLeaseId(Long leaseId);
    
    /**
     * Find all payments for a lease ordered by due date
     */
    List<Payment> findByLeaseIdOrderByDueDateAsc(Long leaseId);
    
    /**
     * Find all payments for a tenant
     */
    List<Payment> findByTenantId(Long tenantId);
    
    /**
     * Find payments for a tenant with pagination
     */
    Page<Payment> findByTenantId(Long tenantId, Pageable pageable);
    
    /**
     * Find payments for a tenant with specific status
     */
    List<Payment> findByTenantIdAndStatus(Long tenantId, PaymentStatus status);
    
    /**
     * Find all payments for a landlord
     */
    List<Payment> findByLandlordId(Long landlordId);
    
    /**
     * Find payments for a landlord with pagination
     */
    Page<Payment> findByLandlordId(Long landlordId, Pageable pageable);
    
    /**
     * Find payments for a landlord with specific status
     */
    List<Payment> findByLandlordIdAndStatus(Long landlordId, PaymentStatus status);
    
    /**
     * Find all payments with specific status
     */
    List<Payment> findByStatus(PaymentStatus status);
    
    /**
     * Find payments with specific status and due date before given date
     */
    List<Payment> findByStatusAndDueDateBefore(PaymentStatus status, LocalDate date);
    
    /**
     * Find all payments for a property
     */
    List<Payment> findByPropertyId(Long propertyId);
    
    /**
     * Find overdue payments (PENDING status with due date in the past)
     */
    @Query("SELECT p FROM Payment p WHERE p.status = 'PENDING' AND p.dueDate < :currentDate")
    List<Payment> findOverduePayments(@Param("currentDate") LocalDate currentDate);
    
    /**
     * Find upcoming payments (due in next X days)
     */
    @Query("SELECT p FROM Payment p WHERE p.status = 'PENDING' " +
           "AND p.dueDate BETWEEN :startDate AND :endDate " +
           "ORDER BY p.dueDate ASC")
    List<Payment> findUpcomingPayments(@Param("startDate") LocalDate startDate, 
                                       @Param("endDate") LocalDate endDate);
    
    /**
     * Get total amount by landlord and status
     */
    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p " +
           "WHERE p.landlord.id = :landlordId AND p.status = :status")
    BigDecimal getTotalAmountByLandlordAndStatus(@Param("landlordId") Long landlordId, 
                                                  @Param("status") PaymentStatus status);
    
    /**
     * Get total amount by tenant and status
     */
    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p " +
           "WHERE p.tenant.id = :tenantId AND p.status = :status")
    BigDecimal getTotalAmountByTenantAndStatus(@Param("tenantId") Long tenantId, 
                                                @Param("status") PaymentStatus status);
    
    /**
     * Count payments by status for tenant
     */
    @Query("SELECT COUNT(p) FROM Payment p WHERE p.tenant.id = :tenantId AND p.status = :status")
    Integer countByTenantIdAndStatus(@Param("tenantId") Long tenantId, @Param("status") PaymentStatus status);
    
    /**
     * Count payments by status for landlord
     */
    @Query("SELECT COUNT(p) FROM Payment p WHERE p.landlord.id = :landlordId AND p.status = :status")
    Integer countByLandlordIdAndStatus(@Param("landlordId") Long landlordId, @Param("status") PaymentStatus status);
    
    /**
     * Find payment with full details (eager fetch relationships)
     */
    @Query("SELECT p FROM Payment p " +
           "JOIN FETCH p.lease l " +
           "JOIN FETCH p.property pr " +
           "JOIN FETCH p.tenant t " +
           "JOIN FETCH p.landlord ll " +
           "WHERE p.id = :paymentId")
    Optional<Payment> findByIdWithDetails(@Param("paymentId") Long paymentId);
    
    /**
     * Find next payment for tenant
     */
    @Query("SELECT p FROM Payment p " +
           "WHERE p.tenant.id = :tenantId " +
           "AND p.status = 'PENDING' " +
           "ORDER BY p.dueDate ASC")
    List<Payment> findNextPaymentForTenant(@Param("tenantId") Long tenantId, Pageable pageable);
    
    /**
     * Find payments for tenant with status ordered by due date
     */
    @Query("SELECT p FROM Payment p " +
           "WHERE p.tenant.id = :tenantId " +
           "AND p.status = :status " +
           "ORDER BY p.dueDate DESC")
    Page<Payment> findByTenantIdAndStatusOrderByDueDateDesc(@Param("tenantId") Long tenantId,
                                                             @Param("status") PaymentStatus status,
                                                             Pageable pageable);
    
    /**
     * Find payments for landlord with status ordered by due date
     */
    @Query("SELECT p FROM Payment p " +
           "WHERE p.landlord.id = :landlordId " +
           "AND p.status = :status " +
           "ORDER BY p.dueDate DESC")
    Page<Payment> findByLandlordIdAndStatusOrderByDueDateDesc(@Param("landlordId") Long landlordId,
                                                               @Param("status") PaymentStatus status,
                                                               Pageable pageable);
}
