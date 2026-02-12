/**
 * Application Route Constants
 */

export const ROUTES = {
  // Public Routes
  HOME: '/',
  PROPERTIES: '/properties',
  PROPERTY_DETAIL: (id: number) => `/properties/${id}`,
  
  // Auth Routes
  LOGIN: '/auth/login',
  REGISTER: '/auth/register',
  
  // Tenant Routes
  TENANT_DASHBOARD: '/tenant/dashboard',
  TENANT_APPLICATIONS: '/tenant/applications',
  TENANT_APPLICATION_NEW: '/tenant/applications/new',
  TENANT_APPLICATION_DETAIL: (id: number) => `/tenant/applications/${id}`,
  TENANT_LEASES: '/tenant/leases',
  TENANT_LEASE_DETAIL: (id: number) => `/tenant/leases/${id}`,
  TENANT_PAYMENTS: '/tenant/payments',
  TENANT_SAVED: '/tenant/saved',
  TENANT_PROFILE: '/tenant/profile',
  
  // Landlord Routes
  LANDLORD_DASHBOARD: '/landlord/dashboard',
  LANDLORD_PROPERTIES: '/landlord/properties',
  LANDLORD_PROPERTY_NEW: '/landlord/properties/new',
  LANDLORD_PROPERTY_EDIT: (id: number) => `/landlord/properties/${id}/edit`,
  LANDLORD_PROPERTY_DETAIL: (id: number) => `/landlord/properties/${id}`,
  LANDLORD_APPLICATIONS: '/landlord/applications',
  LANDLORD_APPLICATION_DETAIL: (id: number) => `/landlord/applications/${id}`,
  LANDLORD_LEASES: '/landlord/leases',
  LANDLORD_LEASE_DETAIL: (id: number) => `/landlord/leases/${id}`,
  LANDLORD_PAYMENTS: '/landlord/payments',
  LANDLORD_PROFILE: '/landlord/profile',
  
  // Admin Routes
  ADMIN_DASHBOARD: '/admin/dashboard',
  ADMIN_USERS: '/admin/users',
  ADMIN_USER_DETAIL: (id: number) => `/admin/users/${id}`,
  ADMIN_PROPERTIES: '/admin/properties',
  ADMIN_PROPERTY_DETAIL: (id: number) => `/admin/properties/${id}`,
  
  // Error Routes
  UNAUTHORIZED: '/401',
  NOT_FOUND: '/404',
  SERVER_ERROR: '/500',
} as const

// Public routes that don't require authentication
export const PUBLIC_ROUTES = [
  ROUTES.HOME,
  ROUTES.PROPERTIES,
  ROUTES.LOGIN,
  ROUTES.REGISTER,
  ROUTES.NOT_FOUND,
  ROUTES.SERVER_ERROR,
]

// Role-based route mapping
export const ROLE_ROUTES = {
  TENANT: [
    ROUTES.TENANT_DASHBOARD,
    ROUTES.TENANT_APPLICATIONS,
    ROUTES.TENANT_LEASES,
    ROUTES.TENANT_PAYMENTS,
    ROUTES.TENANT_SAVED,
    ROUTES.TENANT_PROFILE,
  ],
  LANDLORD: [
    ROUTES.LANDLORD_DASHBOARD,
    ROUTES.LANDLORD_PROPERTIES,
    ROUTES.LANDLORD_APPLICATIONS,
    ROUTES.LANDLORD_LEASES,
    ROUTES.LANDLORD_PAYMENTS,
    ROUTES.LANDLORD_PROFILE,
  ],
  ADMIN: [
    ROUTES.ADMIN_DASHBOARD,
    ROUTES.ADMIN_USERS,
    ROUTES.ADMIN_PROPERTIES,
  ],
} as const

// Default redirect after login based on role
export const DEFAULT_ROLE_REDIRECT = {
  TENANT: ROUTES.TENANT_DASHBOARD,
  LANDLORD: ROUTES.LANDLORD_DASHBOARD,
  ADMIN: ROUTES.ADMIN_DASHBOARD,
} as const
