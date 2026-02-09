package com.gharsaathi.notification.model;

/**
 * Enum representing different types of notifications in the system
 */
public enum NotificationType {
    // Application-related notifications
    APPLICATION_SUBMITTED,      // Landlord: New application received
    APPLICATION_APPROVED,       // Tenant: Application approved
    APPLICATION_REJECTED,       // Tenant: Application rejected
    
    // Lease-related notifications
    LEASE_CREATED,             // Tenant: New lease created
    LEASE_SIGNED,              // Landlord: Tenant signed lease
    LEASE_EXPIRING,            // Both: Lease expiring soon
    LEASE_EXPIRED,             // Both: Lease has expired
    LEASE_TERMINATED,          // Both: Lease terminated
    
    // Payment-related notifications
    PAYMENT_RECEIVED,          // Landlord: Payment received
    PAYMENT_CONFIRMED,         // Tenant: Payment confirmed
    PAYMENT_DUE,               // Tenant: Payment due soon
    PAYMENT_OVERDUE,           // Tenant: Payment overdue
    
    // Property-related notifications
    PROPERTY_APPROVED,         // Landlord: Property approved by admin
    PROPERTY_REJECTED,         // Landlord: Property rejected by admin
    
    // User-related notifications
    PROFILE_UPDATED,           // User: Profile updated successfully
    ACCOUNT_SUSPENDED,         // User: Account suspended by admin
    ACCOUNT_REACTIVATED,       // User: Account reactivated
    ROLE_CHANGED,              // User: Role changed by admin
    
    // General notifications
    SYSTEM_ANNOUNCEMENT,       // All: System-wide announcement
    GENERAL_MESSAGE            // User: General message
}
