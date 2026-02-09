package com.gharsaathi.notification.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gharsaathi.notification.model.Notification;
import com.gharsaathi.notification.model.NotificationType;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    
    // Find all notifications for a user
    List<Notification> findByUserIdOrderByCreatedAtDesc(Long userId);
    
    // Find unread notifications for a user
    List<Notification> findByUserIdAndIsReadFalseOrderByCreatedAtDesc(Long userId);
    
    // Find read notifications for a user
    List<Notification> findByUserIdAndIsReadTrueOrderByCreatedAtDesc(Long userId);
    
    // Find notifications by type for a user
    List<Notification> findByUserIdAndTypeOrderByCreatedAtDesc(Long userId, NotificationType type);
    
    // Count unread notifications for a user
    Long countByUserIdAndIsReadFalse(Long userId);
    
    // Find notifications created after a certain date
    List<Notification> findByUserIdAndCreatedAtAfterOrderByCreatedAtDesc(Long userId, LocalDateTime date);
    
    // Delete old read notifications (for cleanup)
    void deleteByUserIdAndIsReadTrueAndCreatedAtBefore(Long userId, LocalDateTime date);
}
