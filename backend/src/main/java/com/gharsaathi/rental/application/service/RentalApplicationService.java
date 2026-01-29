package com.gharsaathi.rental.application.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gharsaathi.rental.application.dto.ApplicationListResponse;
import com.gharsaathi.rental.application.dto.ApplicationResponse;
import com.gharsaathi.rental.application.dto.CreateApplicationRequest;
import com.gharsaathi.rental.application.exception.ApplicationAccessDeniedException;
import com.gharsaathi.rental.application.exception.ApplicationNotFoundException;
import com.gharsaathi.rental.application.exception.DuplicateApplicationException;
import com.gharsaathi.rental.application.exception.InvalidApplicationStateException;
import com.gharsaathi.rental.application.model.ApplicationStatus;
import com.gharsaathi.rental.application.model.RentalApplication;
import com.gharsaathi.rental.application.repository.RentalApplicationRepository;
import com.gharsaathi.auth.model.User;
import com.gharsaathi.auth.repository.UserRepository;
import com.gharsaathi.common.exception.PropertyNotFoundException;
import com.gharsaathi.property.model.Property;
import com.gharsaathi.property.model.PropertyStatus;
import com.gharsaathi.property.repository.PropertyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service class for rental application operations
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class RentalApplicationService {

    private final RentalApplicationRepository applicationRepository;
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;

    /**
     * Submit a new rental application
     */
    public ApplicationResponse submitApplication(CreateApplicationRequest request, Long tenantId) {
        log.info("Submitting application for property {} by tenant {}", request.getPropertyId(), tenantId);

        // Validate property exists and is available
        Property property = propertyRepository.findById(request.getPropertyId())
            .orElseThrow(() -> new PropertyNotFoundException(request.getPropertyId()));

        if (property.getStatus() != PropertyStatus.AVAILABLE) {
            throw new InvalidApplicationStateException(
                "Cannot apply for property with status: " + property.getStatus()
            );
        }

        // Get tenant
        User tenant = userRepository.findById(tenantId)
            .orElseThrow(() -> new RuntimeException("Tenant not found"));

        // Check for duplicate application
        if (applicationRepository.existsActiveApplicationByTenantAndProperty(tenantId, property.getId())) {
            throw new DuplicateApplicationException(
                "You already have an active application for this property"
            );
        }

        // Prevent landlord from applying to their own property
        if (property.getLandlord().getId().equals(tenantId)) {
            throw new InvalidApplicationStateException(
                "Cannot apply to your own property"
            );
        }

        // Create application
        RentalApplication application = RentalApplication.builder()
            .property(property)
            .tenant(tenant)
            .status(ApplicationStatus.PENDING)
            .message(request.getMessage())
            .moveInDate(request.getMoveInDate())
            .leaseDurationMonths(request.getLeaseDurationMonths())
            .numberOfOccupants(request.getNumberOfOccupants())
            .employmentStatus(request.getEmploymentStatus())
            .monthlyIncome(request.getMonthlyIncome())
            .hasPets(request.getHasPets())
            .emergencyContactName(request.getEmergencyContactName())
            .emergencyContactPhone(request.getEmergencyContactPhone())
            .build();

        RentalApplication savedApplication = applicationRepository.save(application);
        log.info("Application {} submitted successfully", savedApplication.getId());

        return mapToResponse(savedApplication);
    }

    /**
     * Get all applications submitted by a tenant
     */
    @Transactional(readOnly = true)
    public ApplicationListResponse getMyApplications(Long tenantId, int page, int size, String sortBy, String sortDirection) {
        log.info("Getting applications for tenant {}", tenantId);

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<RentalApplication> applicationsPage = applicationRepository.findByTenantId(tenantId, pageable);

        return mapToListResponse(applicationsPage);
    }

    /**
     * Get specific application by ID for tenant
     */
    @Transactional(readOnly = true)
    public ApplicationResponse getApplicationById(Long applicationId, Long tenantId) {
        log.info("Getting application {} for tenant {}", applicationId, tenantId);

        RentalApplication application = applicationRepository.findByIdAndTenantId(applicationId, tenantId)
            .orElseThrow(() -> new ApplicationNotFoundException(applicationId));

        return mapToResponse(application);
    }

    /**
     * Withdraw an application by tenant
     */
    public ApplicationResponse withdrawApplication(Long applicationId, Long tenantId) {
        log.info("Withdrawing application {} by tenant {}", applicationId, tenantId);

        RentalApplication application = applicationRepository.findByIdAndTenantId(applicationId, tenantId)
            .orElseThrow(() -> new ApplicationNotFoundException(applicationId));

        if (!application.isPending()) {
            throw new InvalidApplicationStateException(
                "Can only withdraw applications in PENDING status. Current status: " + application.getStatus()
            );
        }

        application.setStatus(ApplicationStatus.WITHDRAWN);
        RentalApplication updatedApplication = applicationRepository.save(application);

        log.info("Application {} withdrawn successfully", applicationId);
        return mapToResponse(updatedApplication);
    }

    /**
     * Get all applications for properties owned by landlord
     */
    @Transactional(readOnly = true)
    public ApplicationListResponse getApplicationsForMyProperties(Long landlordId, int page, int size, String sortBy, String sortDirection) {
        log.info("Getting applications for landlord {}", landlordId);

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<RentalApplication> applicationsPage = applicationRepository.findByLandlordId(landlordId, pageable);

        return mapToListResponse(applicationsPage);
    }

    /**
     * Get applications for a specific property
     */
    @Transactional(readOnly = true)
    public ApplicationListResponse getApplicationsForProperty(Long propertyId, Long landlordId, int page, int size) {
        log.info("Getting applications for property {} by landlord {}", propertyId, landlordId);

        // Verify property ownership
        Property property = propertyRepository.findById(propertyId)
            .orElseThrow(() -> new PropertyNotFoundException(propertyId));

        if (!property.getLandlord().getId().equals(landlordId)) {
            throw new ApplicationAccessDeniedException(
                "You do not have access to applications for this property"
            );
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<RentalApplication> applicationsPage = applicationRepository.findByPropertyId(propertyId, pageable);

        return mapToListResponse(applicationsPage);
    }

    /**
     * Approve an application
     */
    public ApplicationResponse approveApplication(Long applicationId, Long landlordId, String landlordResponse) {
        log.info("Approving application {} by landlord {}", applicationId, landlordId);

        RentalApplication application = applicationRepository.findByIdAndLandlordId(applicationId, landlordId)
            .orElseThrow(() -> new ApplicationNotFoundException(applicationId));

        if (!application.isPending()) {
            throw new InvalidApplicationStateException(
                "Can only approve applications in PENDING status. Current status: " + application.getStatus()
            );
        }

        // Update application status
        application.setStatus(ApplicationStatus.APPROVED);
        application.setLandlordResponse(landlordResponse);
        application.setReviewedAt(LocalDateTime.now());

        // Update property status to RENTED
        Property property = application.getProperty();
        property.setStatus(PropertyStatus.RENTED);
        propertyRepository.save(property);

        // Auto-reject all other pending applications for the same property
        List<RentalApplication> otherApplications = applicationRepository.findByPropertyIdAndStatus(
            property.getId(), ApplicationStatus.PENDING
        );

        for (RentalApplication otherApp : otherApplications) {
            if (!otherApp.getId().equals(applicationId)) {
                otherApp.setStatus(ApplicationStatus.REJECTED);
                otherApp.setLandlordResponse("Property has been rented to another tenant");
                otherApp.setReviewedAt(LocalDateTime.now());
                applicationRepository.save(otherApp);
            }
        }

        RentalApplication updatedApplication = applicationRepository.save(application);
        log.info("Application {} approved successfully", applicationId);

        return mapToResponse(updatedApplication);
    }

    /**
     * Reject an application
     */
    public ApplicationResponse rejectApplication(Long applicationId, Long landlordId, String landlordResponse) {
        log.info("Rejecting application {} by landlord {}", applicationId, landlordId);

        RentalApplication application = applicationRepository.findByIdAndLandlordId(applicationId, landlordId)
            .orElseThrow(() -> new ApplicationNotFoundException(applicationId));

        if (!application.isPending()) {
            throw new InvalidApplicationStateException(
                "Can only reject applications in PENDING status. Current status: " + application.getStatus()
            );
        }

        application.setStatus(ApplicationStatus.REJECTED);
        application.setLandlordResponse(landlordResponse);
        application.setReviewedAt(LocalDateTime.now());

        RentalApplication updatedApplication = applicationRepository.save(application);
        log.info("Application {} rejected successfully", applicationId);

        return mapToResponse(updatedApplication);
    }

    /**
     * Get application statistics for tenant
     */
    @Transactional(readOnly = true)
    public Map<String, Long> getTenantStatistics(Long tenantId) {
        log.info("Getting application statistics for tenant {}", tenantId);

        Map<String, Long> stats = new HashMap<>();
        stats.put("pending", applicationRepository.countByTenantIdAndStatus(tenantId, ApplicationStatus.PENDING));
        stats.put("approved", applicationRepository.countByTenantIdAndStatus(tenantId, ApplicationStatus.APPROVED));
        stats.put("rejected", applicationRepository.countByTenantIdAndStatus(tenantId, ApplicationStatus.REJECTED));
        stats.put("withdrawn", applicationRepository.countByTenantIdAndStatus(tenantId, ApplicationStatus.WITHDRAWN));

        return stats;
    }

    /**
     * Get application statistics for landlord
     */
    @Transactional(readOnly = true)
    public Map<String, Long> getLandlordStatistics(Long landlordId) {
        log.info("Getting application statistics for landlord {}", landlordId);

        Map<String, Long> stats = new HashMap<>();
        stats.put("pending", applicationRepository.countByLandlordIdAndStatus(landlordId, ApplicationStatus.PENDING));
        stats.put("approved", applicationRepository.countByLandlordIdAndStatus(landlordId, ApplicationStatus.APPROVED));
        stats.put("rejected", applicationRepository.countByLandlordIdAndStatus(landlordId, ApplicationStatus.REJECTED));

        return stats;
    }

    /**
     * Map RentalApplication entity to ApplicationResponse DTO
     */
    private ApplicationResponse mapToResponse(RentalApplication application) {
        Property property = application.getProperty();
        User tenant = application.getTenant();

        return ApplicationResponse.builder()
            .id(application.getId())
            .status(application.getStatus())
            .message(application.getMessage())
            .moveInDate(application.getMoveInDate())
            .leaseDurationMonths(application.getLeaseDurationMonths())
            .numberOfOccupants(application.getNumberOfOccupants())
            .employmentStatus(application.getEmploymentStatus())
            .monthlyIncome(application.getMonthlyIncome())
            .hasPets(application.getHasPets())
            .emergencyContactName(application.getEmergencyContactName())
            .emergencyContactPhone(application.getEmergencyContactPhone())
            .landlordResponse(application.getLandlordResponse())
            .reviewedAt(application.getReviewedAt())
            .createdAt(application.getCreatedAt())
            .updatedAt(application.getUpdatedAt())
            .property(ApplicationResponse.PropertyInfo.builder()
                .id(property.getId())
                .title(property.getTitle())
                .address(property.getAddress())
                .city(property.getCity())
                .price(property.getPrice())
                .bedrooms(property.getBedrooms())
                .bathrooms(property.getBathrooms())
                .propertyType(property.getPropertyType().name())
                .primaryImageUrl(property.getImages().isEmpty() ? null : property.getImages().get(0).getImageUrl())
                .build())
            .tenant(ApplicationResponse.TenantInfo.builder()
                .id(tenant.getId())
                .fullName(tenant.getFullName())
                .email(tenant.getEmail())
                .phoneNumber(tenant.getPhoneNumber())
                .build())
            .build();
    }

    /**
     * Map Page of RentalApplication to ApplicationListResponse
     */
    private ApplicationListResponse mapToListResponse(Page<RentalApplication> page) {
        List<ApplicationResponse> applications = page.getContent().stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());

        return ApplicationListResponse.builder()
            .applications(applications)
            .totalElements(page.getTotalElements())
            .totalPages(page.getTotalPages())
            .currentPage(page.getNumber())
            .pageSize(page.getSize())
            .hasNext(page.hasNext())
            .hasPrevious(page.hasPrevious())
            .build();
    }
}
