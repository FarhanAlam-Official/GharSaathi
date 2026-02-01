package com.gharsaathi.lease.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gharsaathi.lease.service.LeaseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Scheduler for automatically processing expired leases
 * Runs daily at 2:00 AM to mark expired leases and update property status
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class LeaseExpirationScheduler {
    
    private final LeaseService leaseService;
    
    /**
     * Check for expired leases daily at 2:00 AM
     * Cron format: second minute hour day month weekday
     * "0 0 2 * * *" = At 02:00:00 every day
     */
    @Scheduled(cron = "0 0 2 * * *")
    public void checkExpiredLeases() {
        log.info("=== Lease Expiration Scheduler Started ===");
        
        try {
            leaseService.processExpiredLeases();
            log.info("=== Lease Expiration Scheduler Completed Successfully ===");
        } catch (Exception e) {
            log.error("=== Lease Expiration Scheduler Failed ===", e);
        }
    }
    
    /**
     * Optional: Check for leases expiring soon (for notifications)
     * Runs daily at 9:00 AM
     * "0 0 9 * * *" = At 09:00:00 every day
     */
    @Scheduled(cron = "0 0 9 * * *")
    public void checkLeasesExpiringSoon() {
        log.info("=== Lease Expiration Warning Scheduler Started ===");
        
        try {
            var expiringLeases = leaseService.getLeasesExpiringSoon(30); // 30 days ahead
            log.info("Found {} leases expiring within 30 days", expiringLeases.size());
            
            // TODO: Send notifications to landlords and tenants
            // This can be implemented when notification module is added
            
            log.info("=== Lease Expiration Warning Scheduler Completed ===");
        } catch (Exception e) {
            log.error("=== Lease Expiration Warning Scheduler Failed ===", e);
        }
    }
}
