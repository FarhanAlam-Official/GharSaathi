package com.gharsaathi.lease.model;

/**
 * Enum representing the various states of a lease agreement
 */
public enum LeaseStatus {
    /**
     * Lease has been created but not yet activated (future feature)
     */
    DRAFT,
    
    /**
     * Lease is currently active and in effect
     */
    ACTIVE,
    
    /**
     * Lease ended naturally after reaching the end date
     */
    EXPIRED,
    
    /**
     * Lease was terminated early by either party
     */
    TERMINATED,
    
    /**
     * Lease was renewed (for historical tracking)
     */
    RENEWED
}
