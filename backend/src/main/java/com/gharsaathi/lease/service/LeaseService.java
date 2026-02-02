package com.gharsaathi.lease.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gharsaathi.auth.model.User;
import com.gharsaathi.auth.repository.UserRepository;
import com.gharsaathi.common.dto.CreateLeaseRequest;
import com.gharsaathi.common.dto.LeaseListResponse;
import com.gharsaathi.common.dto.LeaseResponse;
import com.gharsaathi.common.dto.TerminateLeaseRequest;
import com.gharsaathi.common.dto.UpdateLeaseRequest;
import com.gharsaathi.common.exception.ResourceNotFoundException;
import com.gharsaathi.lease.exception.InvalidLeaseDateException;
import com.gharsaathi.lease.exception.InvalidLeaseStateException;
import com.gharsaathi.lease.exception.LeaseAccessDeniedException;
import com.gharsaathi.lease.exception.LeaseAlreadyExistsException;
import com.gharsaathi.lease.exception.LeaseNotFoundException;
import com.gharsaathi.lease.model.Lease;
import com.gharsaathi.lease.model.LeaseStatus;
import com.gharsaathi.lease.repository.LeaseRepository;
import com.gharsaathi.payment.service.PaymentService;
import com.gharsaathi.property.model.Property;
import com.gharsaathi.property.model.PropertyImage;
import com.gharsaathi.property.model.PropertyStatus;
import com.gharsaathi.property.repository.PropertyRepository;
import com.gharsaathi.rental.application.model.RentalApplication;
import com.gharsaathi.rental.application.repository.RentalApplicationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service class for managing lease operations
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class LeaseService {
    
    private final LeaseRepository leaseRepository;
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final RentalApplicationRepository rentalApplicationRepository;
    private final PaymentService paymentService;
    
    /**
     * Auto-create lease from approved rental application
     * This is called when an application is approved
     */
    @Transactional
    public LeaseResponse createLeaseFromApplication(RentalApplication application) {
        log.info("Creating lease from approved application: {}", application.getId());
        
        // Check if lease already exists for this application
        if (leaseRepository.findByApplicationId(application.getId()).isPresent()) {
            log.warn("Lease already exists for application: {}", application.getId());
            throw new LeaseAlreadyExistsException("A lease already exists for this application");
        }
        
        // Check if property already has an active lease
        if (leaseRepository.existsByPropertyIdAndStatus(application.getProperty().getId(), LeaseStatus.ACTIVE)) {
            log.error("Property {} already has an active lease", application.getProperty().getId());
            throw new LeaseAlreadyExistsException(application.getProperty().getId());
        }
        
        Property property = application.getProperty();
        User tenant = application.getTenant();
        User landlord = property.getLandlord();
        
        // Calculate lease dates
        LocalDate leaseStartDate = application.getMoveInDate();
        LocalDate leaseEndDate = leaseStartDate.plusMonths(application.getLeaseDurationMonths());
        
        // Validate dates
        validateLeaseDates(leaseStartDate, leaseEndDate);
        
        // Build lease entity
        Lease lease = Lease.builder()
            .property(property)
            .tenant(tenant)
            .landlord(landlord)
            .application(application)
            .leaseStartDate(leaseStartDate)
            .leaseEndDate(leaseEndDate)
            .monthlyRent(property.getPrice())
            .securityDeposit(property.getSecurityDeposit())
            .status(LeaseStatus.ACTIVE)
            .numberOfOccupants(application.getNumberOfOccupants())
            .specialTerms(null)
            .autoRenew(false)
            .earlyTerminationNoticeDays(30)
            .build();
        
        Lease savedLease = leaseRepository.save(lease);
        log.info("Lease created successfully with id: {} for property: {}", savedLease.getId(), property.getId());
        
        // Generate payments for the lease
        try {
            paymentService.generatePaymentsForLease(savedLease);
            log.info("Payments generated successfully for lease ID: {}", savedLease.getId());
        } catch (Exception e) {
            log.error("Failed to generate payments for lease ID: {}, but lease was created", savedLease.getId(), e);
            // Don't fail the lease creation if payment generation fails
        }
        
        return mapToResponse(savedLease);
    }
    
    /**
     * Create manual lease (by landlord or admin)
     */
    @Transactional
    public LeaseResponse createManualLease(CreateLeaseRequest request, Long landlordId) {
        log.info("Creating manual lease for property: {} by landlord: {}", request.getPropertyId(), landlordId);
        
        // Verify property exists and landlord owns it
        Property property = propertyRepository.findById(request.getPropertyId())
            .orElseThrow(() -> new ResourceNotFoundException("Property not found with id: " + request.getPropertyId()));
        
        if (!property.getLandlord().getId().equals(landlordId)) {
            throw new LeaseAccessDeniedException("You do not have permission to create a lease for this property");
        }
        
        // Check if property already has an active lease
        if (leaseRepository.existsByPropertyIdAndStatus(property.getId(), LeaseStatus.ACTIVE)) {
            throw new LeaseAlreadyExistsException(property.getId());
        }
        
        // Verify tenant exists
        User tenant = userRepository.findById(request.getTenantId())
            .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + request.getTenantId()));
        
        // Validate dates
        validateLeaseDates(request.getLeaseStartDate(), request.getLeaseEndDate());
        
        // Build lease entity
        Lease lease = Lease.builder()
            .property(property)
            .tenant(tenant)
            .landlord(property.getLandlord())
            .application(null) // Manual lease has no associated application
            .leaseStartDate(request.getLeaseStartDate())
            .leaseEndDate(request.getLeaseEndDate())
            .monthlyRent(request.getMonthlyRent())
            .securityDeposit(request.getSecurityDeposit())
            .status(LeaseStatus.ACTIVE)
            .numberOfOccupants(request.getNumberOfOccupants())
            .specialTerms(request.getSpecialTerms())
            .autoRenew(request.getAutoRenew() != null ? request.getAutoRenew() : false)
            .earlyTerminationNoticeDays(request.getEarlyTerminationNoticeDays() != null ? 
                request.getEarlyTerminationNoticeDays() : 30)
            .build();
        
        Lease savedLease = leaseRepository.save(lease);
        
        // Update property status to RENTED
        property.setStatus(PropertyStatus.RENTED);
        propertyRepository.save(property);
        
        log.info("Manual lease created successfully with id: {}", savedLease.getId());
        
        // Generate payments for the lease
        try {
            paymentService.generatePaymentsForLease(savedLease);
            log.info("Payments generated successfully for manual lease ID: {}", savedLease.getId());
        } catch (Exception e) {
            log.error("Failed to generate payments for manual lease ID: {}, but lease was created", savedLease.getId(), e);
            // Don't fail the lease creation if payment generation fails
        }
        
        return mapToResponse(savedLease);
    }
    
    /**
     * Get lease by ID
     */
    @Transactional(readOnly = true)
    public LeaseResponse getLeaseById(Long leaseId, Long userId) {
        log.info("Fetching lease: {} for user: {}", leaseId, userId);
        
        Lease lease = getLeaseAndVerifyAccess(leaseId, userId);
        return mapToResponse(lease);
    }
    
    /**
     * Get all leases for a tenant
     */
    @Transactional(readOnly = true)
    public LeaseListResponse getTenantLeases(Long tenantId, LeaseStatus status, int page, int size) {
        log.info("Fetching leases for tenant: {} with status: {}", tenantId, status);
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        
        Page<Lease> leasePage;
        if (status != null) {
            leasePage = leaseRepository.findByTenantId(tenantId, pageable)
                .map(lease -> {
                    if (lease.getStatus() == status) {
                        return lease;
                    }
                    return null;
                });
            // Filter out nulls
            List<Lease> filtered = leasePage.getContent().stream()
                .filter(lease -> lease != null && lease.getStatus() == status)
                .collect(Collectors.toList());
            
            // For simplicity, fetch all and filter
            List<Lease> allLeases = leaseRepository.findByTenantIdAndStatus(tenantId, status);
            return mapToListResponse(allLeases, page, size);
        } else {
            leasePage = leaseRepository.findByTenantId(tenantId, pageable);
            return mapToListResponse(leasePage);
        }
    }
    
    /**
     * Get all leases for a landlord
     */
    @Transactional(readOnly = true)
    public LeaseListResponse getLandlordLeases(Long landlordId, LeaseStatus status, int page, int size) {
        log.info("Fetching leases for landlord: {} with status: {}", landlordId, status);
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        
        Page<Lease> leasePage;
        if (status != null) {
            List<Lease> allLeases = leaseRepository.findByLandlordIdAndStatus(landlordId, status);
            return mapToListResponse(allLeases, page, size);
        } else {
            leasePage = leaseRepository.findByLandlordId(landlordId, pageable);
            return mapToListResponse(leasePage);
        }
    }
    
    /**
     * Get active lease for a property
     */
    @Transactional(readOnly = true)
    public LeaseResponse getActiveLeaseForProperty(Long propertyId) {
        log.info("Fetching active lease for property: {}", propertyId);
        
        List<Lease> activeLeases = leaseRepository.findByPropertyIdAndStatus(propertyId, LeaseStatus.ACTIVE);
        
        if (activeLeases.isEmpty()) {
            throw new LeaseNotFoundException("No active lease found for property: " + propertyId);
        }
        
        return mapToResponse(activeLeases.get(0));
    }
    
    /**
     * Update lease terms
     */
    @Transactional
    public LeaseResponse updateLease(Long leaseId, UpdateLeaseRequest request, Long userId) {
        log.info("Updating lease: {} by user: {}", leaseId, userId);
        
        Lease lease = getLeaseAndVerifyAccess(leaseId, userId);
        
        // Only landlord can update lease
        if (!lease.getLandlord().getId().equals(userId)) {
            throw new LeaseAccessDeniedException(leaseId, userId);
        }
        
        // Can only update active leases
        if (lease.getStatus() != LeaseStatus.ACTIVE) {
            throw new InvalidLeaseStateException(leaseId, lease.getStatus(), "update");
        }
        
        // Update fields if provided
        if (request.getSpecialTerms() != null) {
            lease.setSpecialTerms(request.getSpecialTerms());
        }
        if (request.getAutoRenew() != null) {
            lease.setAutoRenew(request.getAutoRenew());
        }
        if (request.getEarlyTerminationNoticeDays() != null) {
            lease.setEarlyTerminationNoticeDays(request.getEarlyTerminationNoticeDays());
        }
        
        Lease updatedLease = leaseRepository.save(lease);
        log.info("Lease updated successfully: {}", leaseId);
        
        return mapToResponse(updatedLease);
    }
    
    /**
     * Terminate lease early
     */
    @Transactional
    public LeaseResponse terminateLease(Long leaseId, TerminateLeaseRequest request, Long userId) {
        log.info("Terminating lease: {} by user: {}", leaseId, userId);
        
        Lease lease = getLeaseAndVerifyAccess(leaseId, userId);
        
        // Can only terminate active leases
        if (lease.getStatus() != LeaseStatus.ACTIVE) {
            throw new InvalidLeaseStateException(leaseId, lease.getStatus(), "terminate");
        }
        
        // Validate termination date
        LocalDate terminationDate = request.getTerminationDate();
        LocalDate today = LocalDate.now();
        
        if (terminationDate.isBefore(today)) {
            throw new InvalidLeaseDateException("Termination date cannot be in the past");
        }
        
        // Check notice period
        LocalDate earliestTerminationDate = today.plusDays(lease.getEarlyTerminationNoticeDays());
        if (terminationDate.isBefore(earliestTerminationDate)) {
            throw new InvalidLeaseDateException(
                "Termination date must be at least " + lease.getEarlyTerminationNoticeDays() + 
                " days from today. Earliest date: " + earliestTerminationDate
            );
        }
        
        // Update lease
        lease.setStatus(LeaseStatus.TERMINATED);
        lease.setTerminationDate(terminationDate);
        lease.setTerminationReason(request.getTerminationReason());
        
        Lease terminatedLease = leaseRepository.save(lease);
        
        // Update property status to AVAILABLE
        Property property = lease.getProperty();
        property.setStatus(PropertyStatus.AVAILABLE);
        propertyRepository.save(property);
        
        // Cancel future payments
        try {
            paymentService.cancelFuturePayments(leaseId, terminationDate);
            log.info("Future payments cancelled for terminated lease ID: {}", leaseId);
        } catch (Exception e) {
            log.error("Failed to cancel future payments for lease ID: {}, but lease was terminated", leaseId, e);
            // Don't fail the termination if payment cancellation fails
        }
        
        log.info("Lease terminated successfully: {}", leaseId);
        
        return mapToResponse(terminatedLease);
    }
    
    /**
     * Renew lease
     */
    @Transactional
    public LeaseResponse renewLease(Long leaseId, LocalDate newEndDate, Long userId) {
        log.info("Renewing lease: {} with new end date: {}", leaseId, newEndDate);
        
        Lease lease = getLeaseAndVerifyAccess(leaseId, userId);
        
        // Only landlord can renew lease
        if (!lease.getLandlord().getId().equals(userId)) {
            throw new LeaseAccessDeniedException(leaseId, userId);
        }
        
        // Can renew active or expired leases
        if (!lease.canBeRenewed()) {
            throw new InvalidLeaseStateException(leaseId, lease.getStatus(), "renew");
        }
        
        // Validate new end date
        if (newEndDate.isBefore(lease.getLeaseEndDate())) {
            throw new InvalidLeaseDateException("New end date must be after current end date");
        }
        
        // Store old end date for payment generation
        LocalDate oldEndDate = lease.getLeaseEndDate();
        
        // Update lease
        lease.setLeaseEndDate(newEndDate);
        lease.setStatus(LeaseStatus.ACTIVE);
        
        Lease renewedLease = leaseRepository.save(lease);
        
        // Ensure property is marked as RENTED
        Property property = lease.getProperty();
        if (property.getStatus() != PropertyStatus.RENTED) {
            property.setStatus(PropertyStatus.RENTED);
            propertyRepository.save(property);
        }
        
        // Generate payments for the renewal period
        try {
            paymentService.generateRenewalPayments(renewedLease, oldEndDate);
            log.info("Renewal payments generated successfully for lease ID: {}", leaseId);
        } catch (Exception e) {
            log.error("Failed to generate renewal payments for lease ID: {}, but lease was renewed", leaseId, e);
            // Don't fail the renewal if payment generation fails
        }
        
        log.info("Lease renewed successfully: {}", leaseId);
        
        return mapToResponse(renewedLease);
    }
    
    /**
     * System task: Process expired leases
     * This is called by the scheduler daily
     */
    @Transactional
    public void processExpiredLeases() {
        log.info("Processing expired leases");
        
        LocalDate today = LocalDate.now();
        List<Lease> expiredLeases = leaseRepository.findByStatusAndLeaseEndDateBefore(LeaseStatus.ACTIVE, today);
        
        log.info("Found {} expired leases", expiredLeases.size());
        
        for (Lease lease : expiredLeases) {
            // Mark lease as expired
            lease.setStatus(LeaseStatus.EXPIRED);
            leaseRepository.save(lease);
            
            // Update property status to AVAILABLE
            Property property = lease.getProperty();
            property.setStatus(PropertyStatus.AVAILABLE);
            propertyRepository.save(property);
            
            log.info("Marked lease {} as expired and property {} as available", 
                lease.getId(), property.getId());
        }
    }
    
    /**
     * Get leases expiring soon (for notifications)
     */
    @Transactional(readOnly = true)
    public List<LeaseResponse> getLeasesExpiringSoon(int daysAhead) {
        log.info("Fetching leases expiring within {} days", daysAhead);
        
        LocalDate today = LocalDate.now();
        LocalDate futureDate = today.plusDays(daysAhead);
        
        List<Lease> expiringLeases = leaseRepository.findByStatusAndLeaseEndDateBetween(
            LeaseStatus.ACTIVE, today, futureDate);
        
        return expiringLeases.stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }
    
    // Helper Methods
    
    /**
     * Get lease and verify user has access to it
     */
    private Lease getLeaseAndVerifyAccess(Long leaseId, Long userId) {
        Lease lease = leaseRepository.findByIdWithDetails(leaseId)
            .orElseThrow(() -> new LeaseNotFoundException(leaseId));
        
        // User must be either tenant or landlord
        if (!lease.getTenant().getId().equals(userId) && !lease.getLandlord().getId().equals(userId)) {
            throw new LeaseAccessDeniedException(leaseId, userId);
        }
        
        return lease;
    }
    
    /**
     * Validate lease dates
     */
    private void validateLeaseDates(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate) || startDate.isEqual(endDate)) {
            throw new InvalidLeaseDateException(startDate, endDate);
        }
    }
    
    /**
     * Map Lease entity to LeaseResponse DTO
     */
    private LeaseResponse mapToResponse(Lease lease) {
        Property property = lease.getProperty();
        User tenant = lease.getTenant();
        User landlord = lease.getLandlord();
        
        // Get primary image URL
        String primaryImageUrl = property.getImages().stream()
            .filter(PropertyImage::getIsPrimary)
            .findFirst()
            .map(PropertyImage::getImageUrl)
            .orElse(property.getImages().isEmpty() ? null : property.getImages().get(0).getImageUrl());
        
        LeaseResponse.PropertyInfo propertyInfo = LeaseResponse.PropertyInfo.builder()
            .id(property.getId())
            .title(property.getTitle())
            .propertyType(property.getPropertyType())
            .address(property.getAddress())
            .city(property.getCity())
            .area(property.getArea())
            .bedrooms(property.getBedrooms())
            .bathrooms(property.getBathrooms())
            .propertyArea(property.getPropertyArea())
            .furnished(property.getFurnished())
            .amenities(property.getAmenities())
            .primaryImageUrl(primaryImageUrl)
            .build();
        
        LeaseResponse.TenantInfo tenantInfo = LeaseResponse.TenantInfo.builder()
            .id(tenant.getId())
            .fullName(tenant.getFullName())
            .email(tenant.getEmail())
            .phoneNumber(tenant.getPhoneNumber())
            .build();
        
        LeaseResponse.LandlordInfo landlordInfo = LeaseResponse.LandlordInfo.builder()
            .id(landlord.getId())
            .fullName(landlord.getFullName())
            .email(landlord.getEmail())
            .phoneNumber(landlord.getPhoneNumber())
            .build();
        
        return LeaseResponse.builder()
            .id(lease.getId())
            .property(propertyInfo)
            .tenant(tenantInfo)
            .landlord(landlordInfo)
            .applicationId(lease.getApplication() != null ? lease.getApplication().getId() : null)
            .leaseStartDate(lease.getLeaseStartDate())
            .leaseEndDate(lease.getLeaseEndDate())
            .monthlyRent(lease.getMonthlyRent())
            .securityDeposit(lease.getSecurityDeposit())
            .status(lease.getStatus())
            .numberOfOccupants(lease.getNumberOfOccupants())
            .specialTerms(lease.getSpecialTerms())
            .autoRenew(lease.getAutoRenew())
            .earlyTerminationNoticeDays(lease.getEarlyTerminationNoticeDays())
            .terminationDate(lease.getTerminationDate())
            .terminationReason(lease.getTerminationReason())
            .createdAt(lease.getCreatedAt())
            .updatedAt(lease.getUpdatedAt())
            .signedAt(lease.getSignedAt())
            .daysRemaining(lease.getDaysRemaining())
            .durationInMonths(lease.getDurationInMonths())
            .isExpiringSoon(lease.isExpiringSoon(30))
            .build();
    }
    
    /**
     * Map Page<Lease> to LeaseListResponse
     */
    private LeaseListResponse mapToListResponse(Page<Lease> leasePage) {
        List<LeaseResponse> leases = leasePage.getContent().stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
        
        return LeaseListResponse.builder()
            .leases(leases)
            .totalElements(leasePage.getTotalElements())
            .totalPages(leasePage.getTotalPages())
            .currentPage(leasePage.getNumber())
            .pageSize(leasePage.getSize())
            .hasNext(leasePage.hasNext())
            .hasPrevious(leasePage.hasPrevious())
            .build();
    }
    
    /**
     * Map List<Lease> to LeaseListResponse (for filtered results)
     */
    private LeaseListResponse mapToListResponse(List<Lease> leases, int page, int size) {
        List<LeaseResponse> leaseResponses = leases.stream()
            .skip((long) page * size)
            .limit(size)
            .map(this::mapToResponse)
            .collect(Collectors.toList());
        
        int totalElements = leases.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        
        return LeaseListResponse.builder()
            .leases(leaseResponses)
            .totalElements((long) totalElements)
            .totalPages(totalPages)
            .currentPage(page)
            .pageSize(size)
            .hasNext(page < totalPages - 1)
            .hasPrevious(page > 0)
            .build();
    }
}
