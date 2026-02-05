package com.gharsaathi.payment.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gharsaathi.payment.service.PaymentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Scheduler for detecting and processing overdue payments
 * Runs daily at 3:00 AM
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentOverdueScheduler {
    
    private final PaymentService paymentService;

    /**
     * Check for overdue payments and mark them
     * Runs every day at 3:00 AM
     */
    @Scheduled(cron = "0 0 3 * * *")
    public void checkOverduePayments() {
        log.info("Starting overdue payment check...");
        
        try {
            paymentService.processOverduePayments();
            log.info("Overdue payment check completed successfully");
        } catch (Exception e) {
            log.error("Error during overdue payment check", e);
        }
    }
}
