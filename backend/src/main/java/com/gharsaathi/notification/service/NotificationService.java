package com.gharsaathi.notification.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gharsaathi.auth.model.User;
import com.gharsaathi.auth.repository.UserRepository;
import com.gharsaathi.notification.dto.CreateNotificationRequest;
import com.gharsaathi.notification.dto.NotificationDTO;
import com.gharsaathi.notification.model.Notification;
import com.gharsaathi.notification.model.NotificationType;
import com.gharsaathi.notification.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service for managing notifications
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    /**
     * Get all notifications for a user
     */
    public List<NotificationDTO> getUserNotifications(Long userId) {
        log.info("Fetching all notifications for user: {}", userId);
        List<Notification> notifications = notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
        return notifications.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get unread notifications for a user
     */
    public List<NotificationDTO> getUnreadNotifications(Long userId) {
        log.info("Fetching unread notifications for user: {}", userId);
        List<Notification> notifications = notificationRepository.findByUserIdAndIsReadFalseOrderByCreatedAtDesc(userId);
        return notifications.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get read notifications for a user
     */
    public List<NotificationDTO> getReadNotifications(Long userId) {
        log.info("Fetching read notifications for user: {}", userId);
        List<Notification> notifications = notificationRepository.findByUserIdAndIsReadTrueOrderByCreatedAtDesc(userId);
        return notifications.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get notifications by type for a user
     */
    public List<NotificationDTO> getNotificationsByType(Long userId, NotificationType type) {
        log.info("Fetching notifications of type {} for user: {}", type, userId);
        List<Notification> notifications = notificationRepository.findByUserIdAndTypeOrderByCreatedAtDesc(userId, type);
        return notifications.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get unread notification count for a user
     */
    public Long getUnreadCount(Long userId) {
        log.info("Counting unread notifications for user: {}", userId);
        return notificationRepository.countByUserIdAndIsReadFalse(userId);
    }

    /**
     * Mark notification as read
     */
    public NotificationDTO markAsRead(Long notificationId, Long userId) {
        log.info("Marking notification {} as read for user: {}", notificationId, userId);
        
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found with id: " + notificationId));
        
        // Verify ownership
        if (!notification.getUser().getId().equals(userId)) {
            throw new RuntimeException("You don't have permission to update this notification");
        }
        
        if (!notification.getIsRead()) {
            notification.setIsRead(true);
            notification.setReadAt(LocalDateTime.now());
            notification = notificationRepository.save(notification);
            log.info("Notification {} marked as read", notificationId);
        } else {
            log.info("Notification {} was already read", notificationId);
        }
        
        return mapToDTO(notification);
    }

    /**
     * Mark all notifications as read for a user
     */
    public String markAllAsRead(Long userId) {
        log.info("Marking all notifications as read for user: {}", userId);
        
        List<Notification> unreadNotifications = notificationRepository.findByUserIdAndIsReadFalseOrderByCreatedAtDesc(userId);
        
        if (unreadNotifications.isEmpty()) {
            return "No unread notifications";
        }
        
        LocalDateTime now = LocalDateTime.now();
        unreadNotifications.forEach(notification -> {
            notification.setIsRead(true);
            notification.setReadAt(now);
        });
        
        notificationRepository.saveAll(unreadNotifications);
        log.info("Marked {} notifications as read for user: {}", unreadNotifications.size(), userId);
        
        return "All notifications marked as read";
    }

    /**
     * Delete a notification
     */
    public String deleteNotification(Long notificationId, Long userId) {
        log.info("Deleting notification {} for user: {}", notificationId, userId);
        
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found with id: " + notificationId));
        
        // Verify ownership
        if (!notification.getUser().getId().equals(userId)) {
            throw new RuntimeException("You don't have permission to delete this notification");
        }
        
        notificationRepository.delete(notification);
        log.info("Notification {} deleted successfully", notificationId);
        
        return "Notification deleted successfully";
    }

    /**
     * Delete all read notifications for a user
     */
    public String deleteAllRead(Long userId) {
        log.info("Deleting all read notifications for user: {}", userId);
        
        List<Notification> readNotifications = notificationRepository.findByUserIdAndIsReadTrueOrderByCreatedAtDesc(userId);
        
        if (readNotifications.isEmpty()) {
            return "No read notifications to delete";
        }
        
        notificationRepository.deleteAll(readNotifications);
        log.info("Deleted {} read notifications for user: {}", readNotifications.size(), userId);
        
        return "All read notifications deleted";
    }

    /**
     * Create a notification (typically called by system or admin)
     */
    public NotificationDTO createNotification(CreateNotificationRequest request) {
        log.info("Creating notification for user: {}", request.getUserId());
        
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + request.getUserId()));
        
        Notification notification = Notification.builder()
                .user(user)
                .title(request.getTitle())
                .message(request.getMessage())
                .type(request.getType())
                .referenceUrl(request.getReferenceUrl())
                .build();
        
        notification = notificationRepository.save(notification);
        log.info("Notification created with id: {}", notification.getId());
        
        return mapToDTO(notification);
    }

    /**
     * Create notification for application status change
     */
    public void notifyApplicationStatus(Long tenantId, Long applicationId, String propertyTitle, boolean approved) {
        User tenant = userRepository.findById(tenantId)
                .orElseThrow(() -> new RuntimeException("Tenant not found"));
        
        NotificationType type = approved ? NotificationType.APPLICATION_APPROVED : NotificationType.APPLICATION_REJECTED;
        String status = approved ? "approved" : "rejected";
        
        Notification notification = Notification.builder()
                .user(tenant)
                .title("Application " + (approved ? "Approved" : "Rejected"))
                .message("Your application for " + propertyTitle + " has been " + status)
                .type(type)
                .referenceUrl("/tenant/applications/" + applicationId)
                .build();
        
        notificationRepository.save(notification);
        log.info("Application status notification sent to tenant: {}", tenantId);
    }

    /**
     * Create notification for new application received
     */
    public void notifyNewApplication(Long landlordId, Long applicationId, String propertyTitle, String tenantName) {
        User landlord = userRepository.findById(landlordId)
                .orElseThrow(() -> new RuntimeException("Landlord not found"));
        
        Notification notification = Notification.builder()
                .user(landlord)
                .title("New Application Received")
                .message(tenantName + " has applied for your property: " + propertyTitle)
                .type(NotificationType.APPLICATION_SUBMITTED)
                .referenceUrl("/landlord/requests/" + applicationId)
                .build();
        
        notificationRepository.save(notification);
        log.info("New application notification sent to landlord: {}", landlordId);
    }

    /**
     * Create notification for payment received
     */
    public void notifyPaymentReceived(Long landlordId, Long paymentId, Double amount, String tenantName) {
        User landlord = userRepository.findById(landlordId)
                .orElseThrow(() -> new RuntimeException("Landlord not found"));
        
        Notification notification = Notification.builder()
                .user(landlord)
                .title("Payment Received")
                .message("Payment of NPR " + amount + " received from " + tenantName)
                .type(NotificationType.PAYMENT_RECEIVED)
                .referenceUrl("/landlord/payments/" + paymentId)
                .build();
        
        notificationRepository.save(notification);
        log.info("Payment received notification sent to landlord: {}", landlordId);
    }

    /**
     * Create notification for payment confirmed
     */
    public void notifyPaymentConfirmed(Long tenantId, Long paymentId, Double amount) {
        User tenant = userRepository.findById(tenantId)
                .orElseThrow(() -> new RuntimeException("Tenant not found"));
        
        Notification notification = Notification.builder()
                .user(tenant)
                .title("Payment Confirmed")
                .message("Your payment of NPR " + amount + " has been confirmed")
                .type(NotificationType.PAYMENT_CONFIRMED)
                .referenceUrl("/tenant/payments/" + paymentId)
                .build();
        
        notificationRepository.save(notification);
        log.info("Payment confirmed notification sent to tenant: {}", tenantId);
    }

    /**
     * Map Notification entity to DTO
     */
    private NotificationDTO mapToDTO(Notification notification) {
        return NotificationDTO.builder()
                .id(notification.getId())
                .title(notification.getTitle())
                .message(notification.getMessage())
                .type(notification.getType())
                .referenceUrl(notification.getReferenceUrl())
                .isRead(notification.getIsRead())
                .createdAt(notification.getCreatedAt())
                .readAt(notification.getReadAt())
                .build();
    }
}
