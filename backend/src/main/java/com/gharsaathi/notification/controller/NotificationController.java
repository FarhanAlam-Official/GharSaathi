package com.gharsaathi.notification.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gharsaathi.notification.dto.CreateNotificationRequest;
import com.gharsaathi.notification.dto.NotificationDTO;
import com.gharsaathi.notification.model.NotificationType;
import com.gharsaathi.notification.service.NotificationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Controller for managing user notifications
 */
@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    /**
     * Get all notifications for current user
     */
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<NotificationDTO>> getMyNotifications(Authentication authentication) {
        Long userId = getUserIdFromAuth(authentication);
        List<NotificationDTO> notifications = notificationService.getUserNotifications(userId);
        return ResponseEntity.ok(notifications);
    }

    /**
     * Get unread notifications for current user
     */
    @GetMapping("/unread")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<NotificationDTO>> getUnreadNotifications(Authentication authentication) {
        Long userId = getUserIdFromAuth(authentication);
        List<NotificationDTO> notifications = notificationService.getUnreadNotifications(userId);
        return ResponseEntity.ok(notifications);
    }

    /**
     * Get read notifications for current user
     */
    @GetMapping("/read")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<NotificationDTO>> getReadNotifications(Authentication authentication) {
        Long userId = getUserIdFromAuth(authentication);
        List<NotificationDTO> notifications = notificationService.getReadNotifications(userId);
        return ResponseEntity.ok(notifications);
    }

    /**
     * Get notifications by type
     */
    @GetMapping("/type/{type}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<NotificationDTO>> getNotificationsByType(
            @PathVariable NotificationType type,
            Authentication authentication) {
        Long userId = getUserIdFromAuth(authentication);
        List<NotificationDTO> notifications = notificationService.getNotificationsByType(userId, type);
        return ResponseEntity.ok(notifications);
    }

    /**
     * Get unread notification count
     */
    @GetMapping("/unread/count")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Map<String, Long>> getUnreadCount(Authentication authentication) {
        Long userId = getUserIdFromAuth(authentication);
        Long count = notificationService.getUnreadCount(userId);
        return ResponseEntity.ok(Map.of("unreadCount", count));
    }

    /**
     * Mark notification as read
     */
    @PatchMapping("/{notificationId}/read")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<NotificationDTO> markAsRead(
            @PathVariable Long notificationId,
            Authentication authentication) {
        Long userId = getUserIdFromAuth(authentication);
        NotificationDTO notification = notificationService.markAsRead(notificationId, userId);
        return ResponseEntity.ok(notification);
    }

    /**
     * Mark all notifications as read
     */
    @PatchMapping("/read-all")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> markAllAsRead(Authentication authentication) {
        Long userId = getUserIdFromAuth(authentication);
        String message = notificationService.markAllAsRead(userId);
        return ResponseEntity.ok(message);
    }

    /**
     * Delete a notification
     */
    @DeleteMapping("/{notificationId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> deleteNotification(
            @PathVariable Long notificationId,
            Authentication authentication) {
        Long userId = getUserIdFromAuth(authentication);
        String message = notificationService.deleteNotification(notificationId, userId);
        return ResponseEntity.ok(message);
    }

    /**
     * Delete all read notifications
     */
    @DeleteMapping("/read-all")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> deleteAllRead(Authentication authentication) {
        Long userId = getUserIdFromAuth(authentication);
        String message = notificationService.deleteAllRead(userId);
        return ResponseEntity.ok(message);
    }

    /**
     * Create a notification (admin only)
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<NotificationDTO> createNotification(@Valid @RequestBody CreateNotificationRequest request) {
        NotificationDTO notification = notificationService.createNotification(request);
        return ResponseEntity.ok(notification);
    }

    /**
     * Helper method to extract user ID from authentication
     */
    private Long getUserIdFromAuth(Authentication authentication) {
        return Long.parseLong(authentication.getName());
    }
}
