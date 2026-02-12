/**
 * API Configuration Constants
 */

// Base Configuration
export const API_CONFIG = {
  baseURL: process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080/api',
  timeout: parseInt(process.env.NEXT_PUBLIC_API_TIMEOUT || '10000'),
  headers: {
    'Content-Type': 'application/json',
  },
} as const

// API Endpoints
export const API_ENDPOINTS = {
  // Authentication
  AUTH: {
    REGISTER: '/auth/register',
    LOGIN: '/auth/login',
    LOGOUT: '/auth/logout',
    LOGOUT_ALL: '/auth/logout/all',
    REFRESH: '/auth/refresh',
    HEALTH: '/auth/health',
  },

  // Properties (Public)
  PROPERTIES: {
    BASE: '/properties',
    LIST: '/properties',
    SEARCH: '/properties/search',
    BY_ID: (id: number) => `/properties/${id}`,
    DETAIL: (id: number) => `/properties/${id}`,
    LANDLORD_BASE: '/landlord/properties',
    LANDLORD_BY_ID: (id: number) => `/landlord/properties/${id}`,
    LANDLORD_STATUS: (id: number) => `/landlord/properties/${id}/status`,
    ADMIN_DELETE: (id: number) => `/admin/properties/${id}`,
  },

  // Landlord Properties
  LANDLORD: {
    PROPERTIES: '/landlord/properties',
    PROPERTY_DETAIL: (id: number) => `/landlord/properties/${id}`,
    UPDATE_PROPERTY: (id: number) => `/landlord/properties/${id}`,
    DELETE_PROPERTY: (id: number) => `/landlord/properties/${id}`,
    UPDATE_STATUS: (id: number) => `/landlord/properties/${id}/status`,
    APPLICATIONS: '/landlord/applications',
    APPLICATION_DETAIL: (id: number) => `/landlord/applications/${id}`,
    APPROVE_APPLICATION: (id: number) => `/landlord/applications/${id}/approve`,
    REJECT_APPLICATION: (id: number) => `/landlord/applications/${id}/reject`,
    LEASES: '/landlord/leases',
    LEASE_DETAIL: (id: number) => `/landlord/leases/${id}`,
    DASHBOARD: '/landlord/dashboard',
    PAYMENTS: '/landlord/payments',
    PAYMENT_DETAIL: (id: number) => `/landlord/payments/${id}`,
    CONFIRM_PAYMENT: (id: number) => `/landlord/payments/${id}/confirm`,
  },

  // Tenant
  TENANT: {
    APPLICATIONS: '/tenant/applications',
    APPLICATION_DETAIL: (id: number) => `/tenant/applications/${id}`,
    CREATE_APPLICATION: '/tenant/applications',
    WITHDRAW_APPLICATION: (id: number) => `/tenant/applications/${id}/withdraw`,
    LEASES: '/tenant/leases',
    LEASE_DETAIL: (id: number) => `/tenant/leases/${id}`,
    ACTIVE_LEASE: '/tenant/leases/active',
    DASHBOARD: '/tenant/dashboard',
    PAYMENTS: '/tenant/payments',
    PAYMENT_DETAIL: (id: number) => `/tenant/payments/${id}`,
    SAVED_PROPERTIES: '/tenant/saved-properties',
    SAVE_PROPERTY: (propertyId: number) => `/tenant/saved-properties/${propertyId}`,
    UNSAVE_PROPERTY: (propertyId: number) => `/tenant/saved-properties/${propertyId}`,
  },

  // Profile
  PROFILE: {
    GET: '/profile',
    UPDATE: '/profile',
    CHANGE_PASSWORD: '/profile/password',
  },

  // Notifications
  NOTIFICATIONS: {
    LIST: '/notifications',
    DETAIL: (id: number) => `/notifications/${id}`,
    MARK_READ: (id: number) => `/notifications/${id}/mark-read`,
    MARK_ALL_READ: '/notifications/mark-all-read',
    UNREAD_COUNT: '/notifications/unread-count',
  },

  // Reviews
  REVIEWS: {
    PROPERTY_REVIEWS: (propertyId: number) => `/properties/${propertyId}/reviews`,
    CREATE_REVIEW: (propertyId: number) => `/properties/${propertyId}/reviews`,
    UPDATE_REVIEW: (propertyId: number, reviewId: number) => `/properties/${propertyId}/reviews/${reviewId}`,
    DELETE_REVIEW: (propertyId: number, reviewId: number) => `/properties/${propertyId}/reviews/${reviewId}`,
  },

  // File Upload
  FILE_UPLOAD: {
    UPLOAD: '/files/upload',
    DELETE: (fileId: number) => `/files/${fileId}`,
  },

  // Admin
  ADMIN: {
    DASHBOARD: '/admin/dashboard',
    USERS: '/admin/users',
    USER_DETAIL: (id: number) => `/admin/users/${id}`,
    SUSPEND_USER: (id: number) => `/admin/users/${id}/suspend`,
    UNSUSPEND_USER: (id: number) => `/admin/users/${id}/unsuspend`,
    CHANGE_ROLE: (id: number) => `/admin/users/${id}/role`,
    PROPERTIES: '/admin/properties',
    PROPERTY_DETAIL: (id: number) => `/admin/properties/${id}`,
    UPDATE_PROPERTY_STATUS: (id: number) => `/admin/properties/${id}/status`,
  },
} as const

// HTTP Status Codes
export const HTTP_STATUS = {
  OK: 200,
  CREATED: 201,
  NO_CONTENT: 204,
  BAD_REQUEST: 400,
  UNAUTHORIZED: 401,
  FORBIDDEN: 403,
  NOT_FOUND: 404,
  CONFLICT: 409,
  INTERNAL_SERVER_ERROR: 500,
  SERVICE_UNAVAILABLE: 503,
} as const

// Request Timeout
export const REQUEST_TIMEOUT = parseInt(process.env.NEXT_PUBLIC_API_TIMEOUT || '10000')

// Retry Configuration
export const RETRY_CONFIG = {
  maxRetries: 3,
  retryDelay: 1000,
  retryableStatuses: [408, 429, 500, 502, 503, 504],
} as const
