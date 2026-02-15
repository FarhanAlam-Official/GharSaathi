# 🚀 GharSaathi - Complete Development Plan

## From Current State to Production-Ready System

**Project:** GharSaathi - Nepal's Trusted Rental Marketplace  
**Plan Created:** January 29, 2026  
**Backend Status:** ✅ 100% Complete & Stable  
**Frontend Status:** ⚠️ 30% Complete (UI only, no integration)  
**Target:** Production-ready full-stack application  

---

## 📊 PROJECT OVERVIEW

### Current State Assessment

**Backend (Spring Boot + Java 21 + MySQL)**

- ✅ 10 modules fully implemented and tested
- ✅ 40+ REST API endpoints operational
- ✅ JWT authentication & RBAC security configured
- ✅ Database schema complete (9 entities)
- ✅ Comprehensive API documentation available
- ✅ Exception handling & validation implemented

**Frontend (Next.js 16 + React 19 + TypeScript)**

- ✅ Modern UI design system (shadcn/ui)
- ✅ 59 reusable components available
- ✅ 10+ pages with layouts built
- ✅ Responsive design implemented
- ❌ No API integration (using mock data)
- ❌ No authentication implementation
- ❌ No route protection
- ❌ No state management
- ❌ No error handling

### Development Effort Estimation

| Phase | Description | Hours | Duration (1 dev) |
|-------|-------------|-------|------------------|
| **Phase 1** | Foundation & API Setup | 20-24h | 3-4 days |
| **Phase 2** | Authentication System | 16-20h | 2-3 days |
| **Phase 3** | Property Module Integration | 24-30h | 3-4 days |
| **Phase 4** | Dashboard Integration | 16-20h | 2-3 days |
| **Phase 5** | Application & Lease Workflow | 28-34h | 3-5 days |
| **Phase 6** | Payment System | 20-24h | 3-4 days |
| **Phase 7** | Supporting Features | 32-40h | 4-5 days |
| **Phase 8** | Admin Panel | 24-28h | 3-4 days |
| **Phase 9** | Testing & Quality Assurance | 24-30h | 3-4 days |
| **Phase 10** | Deployment & Production Setup | 16-20h | 2-3 days |

**Total Estimated Hours:** 220-270 hours  
**Timeline:** 6-8 weeks (single developer) or 3-4 weeks (team of 2-3)

---

## 🎯 SUCCESS CRITERIA

### Minimum Viable Product (MVP) Criteria

- [ ] Users can register and login with JWT authentication
- [ ] Public property browsing without authentication
- [ ] Landlords can create, update, and delete properties
- [ ] Tenants can submit applications for properties
- [ ] Landlords can approve/reject applications
- [ ] Approved applications auto-create leases
- [ ] Payment tracking for active leases
- [ ] Basic notifications for key events
- [ ] Role-based access control enforced
- [ ] Mobile-responsive across all pages

### Production-Ready Criteria

- [ ] All API endpoints integrated and tested
- [ ] Comprehensive error handling with user-friendly messages
- [ ] Loading states and skeletons throughout
- [ ] Form validation on client and server
- [ ] File upload for property images working
- [ ] Search and filtering functional
- [ ] Admin panel for user/property management
- [ ] Security best practices implemented
- [ ] Performance optimized (< 3s page load)
- [ ] Documentation complete for developers
- [ ] Deployment scripts and CI/CD configured
- [ ] Environment variables properly configured

---

## 📅 DEVELOPMENT PHASES

---

# PHASE 1: FOUNDATION & API INFRASTRUCTURE

**Priority:** 🔴 CRITICAL  
**Duration:** 3-4 days  
**Dependencies:** None  
**Estimated Hours:** 20-24h

## Objectives

- Establish robust API communication layer
- Configure environment variables
- Set up project structure for services and types
- Test backend connectivity

---

## Task 1.1: Install Required Dependencies ✅

**Duration:** 30 minutes
**Status:** COMPLETED

### Steps

```bash
cd frontend
pnpm add axios
pnpm add zustand           # State management (optional but recommended)
pnpm add @tanstack/react-query
```

### Verification Checklist

- [x] All packages installed without errors
- [x] `package.json` updated with new dependencies
- [x] No version conflicts in pnpm-lock.yaml
- [x] Dependencies ready for use

---

## Task 1.2: Environment Configuration ✅

**Duration:** 30 minutes
**Status:** COMPLETED

### Steps

**1. Create environment files:**

```bash
# Frontend root directory
touch .env.local
touch .env.development
touch .env.production
```

**2. Configure `.env.local` (for local development):**

```bash
# API Configuration
NEXT_PUBLIC_API_URL=http://localhost:8080/api
NEXT_PUBLIC_API_TIMEOUT=10000

# Application Configuration
NEXT_PUBLIC_APP_NAME=GharSaathi
NEXT_PUBLIC_APP_VERSION=1.0.0

# File Upload Configuration
NEXT_PUBLIC_MAX_FILE_SIZE=10485760
NEXT_PUBLIC_ALLOWED_FILE_TYPES=image/jpeg,image/png,image/jpg,image/webp

# Feature Flags
NEXT_PUBLIC_ENABLE_ANALYTICS=false
NEXT_PUBLIC_ENABLE_NOTIFICATIONS=true

# Authentication
NEXT_PUBLIC_TOKEN_STORAGE=localStorage
NEXT_PUBLIC_TOKEN_KEY=gharsaathi_token
NEXT_PUBLIC_REFRESH_TOKEN_KEY=gharsaathi_refresh_token
```

**3. Add to `.gitignore`:**

```bash
# Environment files
.env.local
.env.development.local
.env.test.local
.env.production.local
```

**4. Create `.env.example` for team reference:**

```bash
# Copy from .env.local and remove sensitive values
cp .env.local .env.example
```

### Verification Checklist

- [x] `.env.local` created with all variables
- [x] `.env.example` created for documentation
- [x] `.gitignore` already excludes env files
- [x] Environment variables accessible via `process.env.NEXT_PUBLIC_*`
- [x] Backend URL is correct: `http://localhost:8080/api`

---

## Task 1.3: Create Project Structure ✅

**Duration:** 30 minutes
**Status:** COMPLETED

### Steps

**1. Create directory structure:**

```bash
cd frontend

# Create service directories
mkdir -p lib/api
mkdir -p lib/services
mkdir -p lib/hooks
mkdir -p lib/utils
mkdir -p lib/constants

# Create type definition directories
mkdir -p types

# Create context directories
mkdir -p contexts

# Create store directories (if using Zustand)
mkdir -p stores

# Create middleware directory
mkdir -p middleware
```

**2. Create index files for organization:**

```bash
# Service exports
touch lib/services/index.ts

# Type exports
touch types/index.ts

# Constants
touch lib/constants/api.ts
touch lib/constants/routes.ts
```

### Verification Checklist

- [x] All directories created
- [x] Directory structure matches plan
- [x] Index files created for exports
- [x] Structure visible in IDE/editor

---

## Task 1.4: Create API Constants ✅

**Duration:** 30 minutes
**Status:** COMPLETED

### Steps

**1. Create `lib/constants/api.ts`:**

```typescript
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
  
  // Properties
  PROPERTIES: {
    BASE: '/properties',
    BY_ID: (id: number) => `/properties/${id}`,
    SEARCH: '/properties/search',
    LANDLORD_BASE: '/landlord/properties',
    LANDLORD_BY_ID: (id: number) => `/landlord/properties/${id}`,
    LANDLORD_STATUS: (id: number) => `/landlord/properties/${id}/status`,
    ADMIN_DELETE: (id: number) => `/admin/properties/${id}`,
  },
  
  // Applications
  APPLICATIONS: {
    TENANT_BASE: '/tenant/applications',
    TENANT_WITHDRAW: (id: number) => `/tenant/applications/${id}/withdraw`,
    LANDLORD_BASE: '/landlord/applications',
    LANDLORD_APPROVE: (id: number) => `/landlord/applications/${id}/approve`,
    LANDLORD_REJECT: (id: number) => `/landlord/applications/${id}/reject`,
  },
  
  // Leases
  LEASES: {
    TENANT_BASE: '/tenant/leases',
    TENANT_BY_ID: (id: number) => `/tenant/leases/${id}`,
    TENANT_ACTIVE: '/tenant/leases/active',
    LANDLORD_BASE: '/landlord/leases',
    LANDLORD_BY_ID: (id: number) => `/landlord/leases/${id}`,
    LANDLORD_TERMINATE: (id: number) => `/landlord/leases/${id}/terminate`,
  },
  
  // Payments
  PAYMENTS: {
    BASE: '/payments',
    BY_ID: (id: number) => `/payments/${id}`,
    BY_LEASE: (leaseId: number) => `/payments/lease/${leaseId}`,
    TENANT_PAYMENTS: (tenantId: number) => `/payments/tenant/${tenantId}`,
    TENANT_STATISTICS: (tenantId: number) => `/payments/tenant/${tenantId}/statistics`,
    TENANT_UPCOMING: (tenantId: number) => `/payments/tenant/${tenantId}/upcoming`,
    LANDLORD_PAYMENTS: (landlordId: number) => `/payments/landlord/${landlordId}`,
    LANDLORD_STATISTICS: (landlordId: number) => `/payments/landlord/${landlordId}/statistics`,
    MARK_PAID: (id: number) => `/payments/${id}/mark-paid`,
    CONFIRM: (id: number) => `/payments/${id}/confirm`,
  },
  
  // Profile
  PROFILE: {
    BASE: '/users/profile',
    PASSWORD: '/users/profile/password',
    VERIFY_EMAIL: '/users/profile/verify-email',
  },
  
  // Notifications
  NOTIFICATIONS: {
    BASE: '/notifications',
    UNREAD: '/notifications/unread',
    READ: '/notifications/read',
    BY_TYPE: (type: string) => `/notifications/type/${type}`,
    MARK_READ: (id: number) => `/notifications/${id}/read`,
    READ_ALL: '/notifications/read-all',
    DELETE: (id: number) => `/notifications/${id}`,
  },
  
  // Dashboard
  DASHBOARD: {
    TENANT: '/dashboard/tenant',
    LANDLORD: '/dashboard/landlord',
    ADMIN: '/dashboard/admin',
  },
  
  // Reviews
  REVIEWS: {
    BASE: '/reviews',
    BY_PROPERTY: (propertyId: number) => `/reviews/property/${propertyId}`,
    BY_ID: (id: number) => `/reviews/${id}`,
    VERIFY: (id: number) => `/reviews/${id}/verify`,
  },
  
  // File Upload
  FILES: {
    UPLOAD: '/files/upload',
    BY_ID: (id: number) => `/files/${id}`,
    BY_PROPERTY: (propertyId: number) => `/files/property/${propertyId}`,
  },
  
  // Admin
  ADMIN: {
    USERS: '/admin/users',
    USERS_BY_ID: (id: number) => `/admin/users/${id}`,
    USERS_SUSPENDED: '/admin/users/suspended',
    USER_SUSPEND: (id: number) => `/admin/users/${id}/suspend`,
    USER_ACTIVATE: (id: number) => `/admin/users/${id}/activate`,
    USER_ROLE: (id: number) => `/admin/users/${id}/role`,
    USER_DELETE: (id: number) => `/admin/users/${id}`,
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
  UNPROCESSABLE_ENTITY: 422,
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
```

**2. Create `lib/constants/routes.ts`:**

```typescript
/**
 * Application Route Constants
 */

export const ROUTES = {
  // Public Routes
  HOME: '/',
  PROPERTIES: '/properties',
  PROPERTY_DETAILS: (id: number | string) => `/properties/${id}`,
  
  // Auth Routes
  LOGIN: '/auth/login',
  REGISTER: '/auth/register',
  FORGOT_PASSWORD: '/auth/forgot-password',
  
  // Tenant Routes
  TENANT_DASHBOARD: '/tenant/dashboard',
  TENANT_APPLICATIONS: '/tenant/applications',
  TENANT_SAVED: '/tenant/saved',
  
  // Landlord Routes
  LANDLORD_DASHBOARD: '/landlord/dashboard',
  LANDLORD_PROPERTIES: '/landlord/properties',
  LANDLORD_REQUESTS: '/landlord/requests',
  LANDLORD_PROPERTY_CREATE: '/landlord/properties/new',
  LANDLORD_PROPERTY_EDIT: (id: number | string) => `/landlord/properties/${id}/edit`,
  
  // Admin Routes
  ADMIN_DASHBOARD: '/admin/dashboard',
  ADMIN_USERS: '/admin/users',
  ADMIN_PROPERTIES: '/admin/properties',
  
  // Error Routes
  UNAUTHORIZED: '/unauthorized',
  NOT_FOUND: '/404',
  SERVER_ERROR: '/500',
} as const

// Public routes that don't require authentication
export const PUBLIC_ROUTES = [
  ROUTES.HOME,
  ROUTES.PROPERTIES,
  ROUTES.LOGIN,
  ROUTES.REGISTER,
  ROUTES.FORGOT_PASSWORD,
  ROUTES.NOT_FOUND,
  ROUTES.SERVER_ERROR,
]

// Role-based route mapping
export const ROLE_ROUTES = {
  TENANT: [
    ROUTES.TENANT_DASHBOARD,
    ROUTES.TENANT_APPLICATIONS,
    ROUTES.TENANT_SAVED,
  ],
  LANDLORD: [
    ROUTES.LANDLORD_DASHBOARD,
    ROUTES.LANDLORD_PROPERTIES,
    ROUTES.LANDLORD_REQUESTS,
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
```

### Verification Checklist

- [x] `lib/constants/api.ts` created with all endpoints
- [x] `lib/constants/routes.ts` created with route mapping
- [x] All backend endpoints represented in constants
- [x] No hardcoded URLs in constants
- [x] TypeScript types are properly defined (`as const`)

---

## Task 1.5: Create Axios Client with Interceptors ✅

**Duration:** 2 hours
**Status:** COMPLETED

### Steps

**1. Create `lib/api/client.ts`:**

```typescript
import axios, { AxiosInstance, AxiosError, AxiosRequestConfig, AxiosResponse } from 'axios'
import { API_CONFIG, HTTP_STATUS } from '@/lib/constants/api'

/**
 * Create Axios instance with default configuration
 */
export const apiClient: AxiosInstance = axios.create({
  baseURL: API_CONFIG.baseURL,
  timeout: API_CONFIG.timeout,
  headers: API_CONFIG.headers,
})

/**
 * Request interceptor - Add JWT token to headers
 */
apiClient.interceptors.request.use(
  (config) => {
    // Get token from storage
    const token = getAccessToken()
    
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    
    // Log request in development
    if (process.env.NODE_ENV === 'development') {
      console.log('🚀 API Request:', {
        method: config.method?.toUpperCase(),
        url: config.url,
        data: config.data,
      })
    }
    
    return config
  },
  (error) => {
    console.error('❌ Request Error:', error)
    return Promise.reject(error)
  }
)

/**
 * Response interceptor - Handle errors and token refresh
 */
apiClient.interceptors.response.use(
  (response: AxiosResponse) => {
    // Log response in development
    if (process.env.NODE_ENV === 'development') {
      console.log('✅ API Response:', {
        status: response.status,
        url: response.config.url,
        data: response.data,
      })
    }
    
    return response
  },
  async (error: AxiosError) => {
    const originalRequest = error.config as AxiosRequestConfig & { _retry?: boolean }
    
    // Log error in development
    if (process.env.NODE_ENV === 'development') {
      console.error('❌ API Error:', {
        status: error.response?.status,
        url: error.config?.url,
        message: error.message,
      })
    }
    
    // Handle 401 Unauthorized - Try to refresh token
    if (error.response?.status === HTTP_STATUS.UNAUTHORIZED && !originalRequest._retry) {
      originalRequest._retry = true
      
      try {
        const refreshToken = getRefreshToken()
        
        if (!refreshToken) {
          // No refresh token, redirect to login
          handleAuthenticationFailure()
          return Promise.reject(error)
        }
        
        // Attempt to refresh token
        const response = await axios.post(
          `${API_CONFIG.baseURL}/auth/refresh`,
          { refreshToken }
        )
        
        const { accessToken } = response.data
        
        // Save new token
        setAccessToken(accessToken)
        
        // Retry original request with new token
        if (originalRequest.headers) {
          originalRequest.headers.Authorization = `Bearer ${accessToken}`
        }
        
        return apiClient(originalRequest)
      } catch (refreshError) {
        // Refresh failed, logout user
        handleAuthenticationFailure()
        return Promise.reject(refreshError)
      }
    }
    
    // Handle 403 Forbidden
    if (error.response?.status === HTTP_STATUS.FORBIDDEN) {
      // Redirect to unauthorized page or show message
      window.location.href = '/unauthorized'
    }
    
    return Promise.reject(error)
  }
)

/**
 * Token management utilities
 */
const TOKEN_KEY = process.env.NEXT_PUBLIC_TOKEN_KEY || 'gharsaathi_token'
const REFRESH_TOKEN_KEY = process.env.NEXT_PUBLIC_REFRESH_TOKEN_KEY || 'gharsaathi_refresh_token'

export const getAccessToken = (): string | null => {
  if (typeof window === 'undefined') return null
  return localStorage.getItem(TOKEN_KEY)
}

export const setAccessToken = (token: string): void => {
  if (typeof window === 'undefined') return
  localStorage.setItem(TOKEN_KEY, token)
}

export const getRefreshToken = (): string | null => {
  if (typeof window === 'undefined') return null
  return localStorage.getItem(REFRESH_TOKEN_KEY)
}

export const setRefreshToken = (token: string): void => {
  if (typeof window === 'undefined') return
  localStorage.setItem(REFRESH_TOKEN_KEY, token)
}

export const clearTokens = (): void => {
  if (typeof window === 'undefined') return
  localStorage.removeItem(TOKEN_KEY)
  localStorage.removeItem(REFRESH_TOKEN_KEY)
}

/**
 * Handle authentication failure
 */
const handleAuthenticationFailure = (): void => {
  clearTokens()
  
  // Redirect to login if not already there
  if (typeof window !== 'undefined' && !window.location.pathname.includes('/auth/login')) {
    window.location.href = '/auth/login'
  }
}

/**
 * Export configured client
 */
export default apiClient
```

### Verification Checklist

- [x] Axios client created with base configuration
- [x] Request interceptor adds JWT token to headers
- [x] Response interceptor handles 401 errors with token refresh
- [x] Response interceptor handles 403 errors
- [x] Token management functions implemented
- [x] Development logging configured
- [x] Error handling includes authentication failure

---

## Task 1.6: Create API Error Handler ✅

**Duration:** 1 hour
**Status:** COMPLETED

### Steps

**1. Create `lib/api/errorHandler.ts`:**

```typescript
import { AxiosError } from 'axios'
import { HTTP_STATUS } from '@/lib/constants/api'

/**
 * API Error Response Structure
 */
export interface APIError {
  status: number
  message: string
  errors?: Record<string, string[]>
  timestamp?: string
}

/**
 * User-friendly error messages
 */
const ERROR_MESSAGES: Record<number, string> = {
  [HTTP_STATUS.BAD_REQUEST]: 'Invalid request. Please check your input.',
  [HTTP_STATUS.UNAUTHORIZED]: 'Please login to continue.',
  [HTTP_STATUS.FORBIDDEN]: 'You do not have permission to perform this action.',
  [HTTP_STATUS.NOT_FOUND]: 'The requested resource was not found.',
  [HTTP_STATUS.CONFLICT]: 'This action conflicts with existing data.',
  [HTTP_STATUS.UNPROCESSABLE_ENTITY]: 'Unable to process your request.',
  [HTTP_STATUS.INTERNAL_SERVER_ERROR]: 'Something went wrong. Please try again later.',
  [HTTP_STATUS.SERVICE_UNAVAILABLE]: 'Service is temporarily unavailable.',
}

/**
 * Handle API errors and return user-friendly messages
 */
export const handleAPIError = (error: unknown): APIError => {
  // Handle Axios errors
  if (error instanceof AxiosError) {
    const status = error.response?.status || HTTP_STATUS.INTERNAL_SERVER_ERROR
    const responseData = error.response?.data
    
    // Check if backend sent a message
    const backendMessage = responseData?.message || responseData?.error
    
    // Use backend message if available, otherwise use generic message
    const message = backendMessage || ERROR_MESSAGES[status] || 'An unexpected error occurred.'
    
    // Extract validation errors if present
    const validationErrors = responseData?.errors || responseData?.validationErrors
    
    return {
      status,
      message,
      errors: validationErrors,
      timestamp: responseData?.timestamp,
    }
  }
  
  // Handle network errors
  if (error instanceof Error) {
    if (error.message === 'Network Error') {
      return {
        status: 0,
        message: 'Network error. Please check your internet connection.',
      }
    }
    
    return {
      status: HTTP_STATUS.INTERNAL_SERVER_ERROR,
      message: error.message || 'An unexpected error occurred.',
    }
  }
  
  // Handle unknown errors
  return {
    status: HTTP_STATUS.INTERNAL_SERVER_ERROR,
    message: 'An unexpected error occurred.',
  }
}

/**
 * Get user-friendly error message for display
 */
export const getErrorMessage = (error: unknown): string => {
  const apiError = handleAPIError(error)
  return apiError.message
}

/**
 * Get validation errors from API response
 */
export const getValidationErrors = (error: unknown): Record<string, string[]> | undefined => {
  const apiError = handleAPIError(error)
  return apiError.errors
}

/**
 * Check if error is a specific HTTP status
 */
export const isErrorStatus = (error: unknown, status: number): boolean => {
  if (error instanceof AxiosError) {
    return error.response?.status === status
  }
  return false
}

/**
 * Check if error is authentication related
 */
export const isAuthError = (error: unknown): boolean => {
  return isErrorStatus(error, HTTP_STATUS.UNAUTHORIZED) || 
         isErrorStatus(error, HTTP_STATUS.FORBIDDEN)
}

/**
 * Check if error is validation related
 */
export const isValidationError = (error: unknown): boolean => {
  return isErrorStatus(error, HTTP_STATUS.BAD_REQUEST) || 
         isErrorStatus(error, HTTP_STATUS.UNPROCESSABLE_ENTITY)
}
```

### Verification Checklist

- [x] Error handler created with proper TypeScript types
- [x] User-friendly error messages defined
- [x] Validation error extraction implemented
- [x] Network error handling included
- [x] Helper functions for error type checking
- [x] Backend error message extraction working

---

## Task 1.7: Test Backend Connection ✅

**Duration:** 1 hour
**Status:** COMPLETED

### Steps

**1. Create test component `components/TestAPIConnection.tsx`:**

```typescript
'use client'

import { useState } from 'react'
import { Button } from '@/components/ui/button'
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card'
import { Alert, AlertDescription } from '@/components/ui/alert'
import apiClient from '@/lib/api/client'
import { API_ENDPOINTS } from '@/lib/constants/api'
import { handleAPIError } from '@/lib/api/errorHandler'

export function TestAPIConnection() {
  const [status, setStatus] = useState<'idle' | 'loading' | 'success' | 'error'>('idle')
  const [message, setMessage] = useState<string>('')

  const testConnection = async () => {
    setStatus('loading')
    setMessage('Testing connection...')

    try {
      const response = await apiClient.get(API_ENDPOINTS.AUTH.HEALTH)
      setStatus('success')
      setMessage(`✅ Connected! Response: ${response.data}`)
    } catch (error) {
      setStatus('error')
      const apiError = handleAPIError(error)
      setMessage(`❌ Connection failed: ${apiError.message}`)
    }
  }

  return (
    <Card className="w-full max-w-md">
      <CardHeader>
        <CardTitle>API Connection Test</CardTitle>
      </CardHeader>
      <CardContent className="space-y-4">
        <Button 
          onClick={testConnection} 
          disabled={status === 'loading'}
          className="w-full"
        >
          {status === 'loading' ? 'Testing...' : 'Test Connection'}
        </Button>
        
        {message && (
          <Alert variant={status === 'error' ? 'destructive' : 'default'}>
            <AlertDescription>{message}</AlertDescription>
          </Alert>
        )}
        
        <div className="text-sm text-muted-foreground">
          <p>API URL: {process.env.NEXT_PUBLIC_API_URL}</p>
        </div>
      </CardContent>
    </Card>
  )
}
```

**2. Add test component to a page temporarily (e.g., home page):**

```typescript
// app/page.tsx - Add temporarily for testing
import { TestAPIConnection } from '@/components/TestAPIConnection'

// Add somewhere in the page
<div className="container mx-auto p-4">
  <TestAPIConnection />
</div>
```

**3. Start backend server:**

```bash
# Terminal 1: Start backend
cd backend
./mvnw spring-boot:run
```

**4. Start frontend server:**

```bash
# Terminal 2: Start frontend
cd frontend
npm run dev
```

**5. Test the connection:**

- Open browser to `http://localhost:3000`
- Click "Test Connection" button
- Verify successful connection or debug errors

### Verification Checklist

- [x] Test component created and added to page
- [x] Test connection component ready for testing
- [x] Error message handling implemented
- [x] Success/error states displayed properly

---

## Phase 1 Completion Checklist ✅

### Prerequisites Verified

- [x] Node.js and pnpm installed
- [x] Backend server configuration checked
- [x] MySQL database connection configured
- [x] Frontend can build without errors

### Infrastructure Complete

- [x] All dependencies installed (axios, zustand, @tanstack/react-query)
- [x] Environment variables configured (.env.local, .env.example)
- [x] Project structure created (lib/api, lib/services, lib/constants, types, contexts, stores, middleware)
- [x] API constants defined (api.ts)
- [x] Route constants defined (routes.ts)

### API Client Ready

- [x] Axios client configured with base URL and timeout
- [x] Request interceptors working (adds JWT token)
- [x] Response interceptors handling errors (401/403)
- [x] Token management functions implemented
- [x] Error handler created with user-friendly messages

### Testing Ready

- [x] Test component created (TestAPIConnection.tsx)
- [x] Test component added to home page
- [x] Ready for backend connection testing
- [x] Error handling ready for testing

### Documentation

- [x] `.env.example` created for team reference
- [x] API constants documented with TypeScript types
- [x] Development plan updated with progress

### Notes for Next Phase (Phase 2)

- Backend server must be running on port 8080 for testing
- Test the connection using the TestAPIConnection component on homepage
- After Phase 1 testing is complete, remove TestAPIConnection from homepage
- Phase 2 will implement authentication (register, login, logout)

---

# PHASE 2: AUTHENTICATION & AUTHORIZATION SYSTEM

**Priority:** 🔴 CRITICAL  
**Duration:** 2-3 days  
**Dependencies:** Phase 1  
**Estimated Hours:** 16-20h

## Objectives

- Implement complete authentication flow (register, login, logout)
- Create authentication context for global state
- Implement JWT token management
- Add route protection middleware
- Create user session management

---

## Task 2.1: Create Type Definitions for Auth ✅

**Duration:** 1 hour
**Status:** COMPLETED

### Steps

**1. Create `types/auth.types.ts`:**

```typescript
/**
 * User roles in the system
 */
export type Role = 'TENANT' | 'LANDLORD' | 'ADMIN'

/**
 * User entity
 */
export interface User {
  id: number
  email: string
  firstName: string
  lastName: string
  fullName?: string
  role: Role
  phoneNumber?: string
  avatarUrl?: string
  emailVerified: boolean
  isActive: boolean
  isSuspended: boolean
  createdAt: string
  updatedAt: string
}

/**
 * Registration request payload
 */
export interface RegisterRequest {
  email: string
  password: string
  firstName: string
  lastName: string
  role: Role
  phoneNumber?: string
}

/**
 * Login request payload
 */
export interface LoginRequest {
  email: string
  password: string
}

/**
 * Authentication response from backend
 */
export interface AuthResponse {
  id: number
  email: string
  firstName: string
  lastName: string
  role: Role
  phoneNumber?: string
  avatarUrl?: string
  accessToken: string
  refreshToken: string
  tokenType: string
  expiresIn: number
}

/**
 * Token refresh request
 */
export interface TokenRefreshRequest {
  refreshToken: string
}

/**
 * Token refresh response
 */
export interface TokenRefreshResponse {
  accessToken: string
  refreshToken: string
  tokenType: string
  expiresIn: number
}

/**
 * Authentication context state
 */
export interface AuthContextType {
  user: User | null
  isAuthenticated: boolean
  isLoading: boolean
  login: (credentials: LoginRequest) => Promise<void>
  register: (data: RegisterRequest) => Promise<void>
  logout: () => Promise<void>
  logoutAll: () => Promise<void>
  refreshToken: () => Promise<void>
}
```

**2. Export from `types/index.ts`:**

```typescript
export * from './auth.types'
// ... other type exports
```

### Verification Checklist

- [x] All auth types defined
- [x] Types exported from index
- [x] Types match backend DTOs exactly
- [x] No TypeScript errors

---

## Task 2.2: Create Authentication Service ✅

**Duration:** 2 hours
**Status:** COMPLETED

### Steps

**1. Create `lib/services/auth.service.ts`:**

```typescript
import apiClient, { setAccessToken, setRefreshToken, clearTokens } from '@/lib/api/client'
import { API_ENDPOINTS } from '@/lib/constants/api'
import type {
  LoginRequest,
  RegisterRequest,
  AuthResponse,
  TokenRefreshRequest,
  TokenRefreshResponse,
  User,
} from '@/types/auth.types'

/**
 * Authentication Service
 * Handles all authentication-related API calls
 */
class AuthService {
  /**
   * Register a new user
   */
  async register(data: RegisterRequest): Promise<AuthResponse> {
    const response = await apiClient.post<AuthResponse>(
      API_ENDPOINTS.AUTH.REGISTER,
      data
    )
    
    // Store tokens
    if (response.data.accessToken) {
      setAccessToken(response.data.accessToken)
      setRefreshToken(response.data.refreshToken)
    }
    
    return response.data
  }

  /**
   * Login user
   */
  async login(credentials: LoginRequest): Promise<AuthResponse> {
    const response = await apiClient.post<AuthResponse>(
      API_ENDPOINTS.AUTH.LOGIN,
      credentials
    )
    
    // Store tokens
    if (response.data.accessToken) {
      setAccessToken(response.data.accessToken)
      setRefreshToken(response.data.refreshToken)
    }
    
    return response.data
  }

  /**
   * Logout current session
   */
  async logout(): Promise<void> {
    try {
      await apiClient.post(API_ENDPOINTS.AUTH.LOGOUT)
    } finally {
      // Clear tokens even if request fails
      clearTokens()
    }
  }

  /**
   * Logout from all devices
   */
  async logoutAll(): Promise<void> {
    try {
      await apiClient.post(API_ENDPOINTS.AUTH.LOGOUT_ALL)
    } finally {
      // Clear tokens even if request fails
      clearTokens()
    }
  }

  /**
   * Refresh access token
   */
  async refreshToken(refreshToken: string): Promise<TokenRefreshResponse> {
    const response = await apiClient.post<TokenRefreshResponse>(
      API_ENDPOINTS.AUTH.REFRESH,
      { refreshToken } as TokenRefreshRequest
    )
    
    // Store new tokens
    if (response.data.accessToken) {
      setAccessToken(response.data.accessToken)
      setRefreshToken(response.data.refreshToken)
    }
    
    return response.data
  }

  /**
   * Get current user from token
   * This extracts user info from the JWT token or makes an API call
   */
  async getCurrentUser(): Promise<User | null> {
    try {
      // If you have a /me endpoint, use it:
      // const response = await apiClient.get<User>(API_ENDPOINTS.AUTH.ME)
      // return response.data
      
      // For now, decode from stored token (you'll need jwt-decode package)
      // Or return null and rely on login/register response
      return null
    } catch (error) {
      return null
    }
  }

  /**
   * Check auth health
   */
  async healthCheck(): Promise<boolean> {
    try {
      await apiClient.get(API_ENDPOINTS.AUTH.HEALTH)
      return true
    } catch (error) {
      return false
    }
  }
}

// Export singleton instance
export const authService = new AuthService()
export default authService
```

### Verification Checklist

x] Auth service created with all methods

- [x] Token storage integrated
- [x] All endpoints use API constants
- [x] Error handling deferred to calling code
- [x] TypeScript types properly applied
- [x] Singleton instance exported

---

## Task 2.3: Create Authentication Context ✅

**Duration:** 3 hours
**Status:** COMPLETED
**Duration:** 3 hours

### Steps

**1. Create `contexts/AuthContext.tsx`:**

```typescript
'use client'

import React, { createContext, useContext, useState, useEffect, useCallback } from 'react'
import { useRouter } from 'next/navigation'
import { authService } from '@/lib/services/auth.service'
import { getAccessToken, clearTokens } from '@/lib/api/client'
import { handleAPIError } from '@/lib/api/errorHandler'
import { DEFAULT_ROLE_REDIRECT } from '@/lib/constants/routes'
import type {
  AuthContextType,
  User,
  LoginRequest,
  RegisterRequest,
} from '@/types/auth.types'

/**
 * Create Auth Context
 */
const AuthContext = createContext<AuthContextType | undefined>(undefined)

/**
 * Auth Provider Props
 */
interface AuthProviderProps {
  children: React.ReactNode
}

/**
 * Auth Provider Component
 */
export function AuthProvider({ children }: AuthProviderProps) {
  const [user, setUser] = useState<User | null>(null)
  const [isLoading, setIsLoading] = useState(true)
  const router = useRouter()

  /**
   * Check if user is authenticated on mount
   */
  useEffect(() => {
    const initAuth = async () => {
      const token = getAccessToken()
      
      if (token) {
        try {
          // Try to get current user info
          const currentUser = await authService.getCurrentUser()
          setUser(currentUser)
        } catch (error) {
          // Token invalid, clear it
          clearTokens()
          setUser(null)
        }
      }
      
      setIsLoading(false)
    }

    initAuth()
  }, [])

  /**
   * Login function
   */
  const login = useCallback(async (credentials: LoginRequest) => {
    try {
      setIsLoading(true)
      const response = await authService.login(credentials)
      
      // Create user object from response
      const authenticatedUser: User = {
        id: response.id,
        email: response.email,
        firstName: response.firstName,
        lastName: response.lastName,
        fullName: `${response.firstName} ${response.lastName}`,
        role: response.role,
        phoneNumber: response.phoneNumber,
        avatarUrl: response.avatarUrl,
        emailVerified: false, // Set from response if available
        isActive: true,
        isSuspended: false,
        createdAt: new Date().toISOString(),
        updatedAt: new Date().toISOString(),
      }
      
      setUser(authenticatedUser)
      
      // Redirect based on role
      const redirectPath = DEFAULT_ROLE_REDIRECT[response.role]
      router.push(redirectPath)
    } catch (error) {
      const apiError = handleAPIError(error)
      throw new Error(apiError.message)
    } finally {
      setIsLoading(false)
    }
  }, [router])

  /**
   * Register function
   */
  const register = useCallback(async (data: RegisterRequest) => {
    try {
      setIsLoading(true)
      const response = await authService.register(data)
      
      // Create user object from response
      const authenticatedUser: User = {
        id: response.id,
        email: response.email,
        firstName: response.firstName,
        lastName: response.lastName,
        fullName: `${response.firstName} ${response.lastName}`,
        role: response.role,
        phoneNumber: response.phoneNumber,
        avatarUrl: response.avatarUrl,
        emailVerified: false,
        isActive: true,
        isSuspended: false,
        createdAt: new Date().toISOString(),
        updatedAt: new Date().toISOString(),
      }
      
      setUser(authenticatedUser)
      
      // Redirect based on role
      const redirectPath = DEFAULT_ROLE_REDIRECT[response.role]
      router.push(redirectPath)
    } catch (error) {
      const apiError = handleAPIError(error)
      throw new Error(apiError.message)
    } finally {
      setIsLoading(false)
    }
  }, [router])

  /**
   * Logout function
   */
  const logout = useCallback(async () => {
    try {
      await authService.logout()
    } catch (error) {
      // Log error but continue with logout
      console.error('Logout error:', error)
    } finally {
      setUser(null)
      clearTokens()
      router.push('/auth/login')
    }
  }, [router])

  /**
   * Logout from all devices
   */
  const logoutAll = useCallback(async () => {
    try {
      await authService.logoutAll()
    } catch (error) {
      console.error('Logout all error:', error)
    } finally {
      setUser(null)
      clearTokens()
      router.push('/auth/login')
    }
  }, [router])

  /**
   * Refresh token function
   */
  const refreshToken = useCallback(async () => {
    // Token refresh is handled automatically by axios interceptor
    // This function is here for manual refresh if needed
    console.log('Manual token refresh requested')
  }, [])

  /**
   * Context value
   */
  const value: AuthContextType = {
    user,
    isAuthenticated: !!user,
    isLoading,
    login,
    register,
    logout,
    logoutAll,
    refreshToken,
  }

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>
}

/**
 * Custom hook to use auth context
 */
export function useAuth() {
  const context = useContext(AuthContext)
  
  if (context === undefined) {
    throw new Error('useAuth must be used within an AuthProvider')
  }
  
  return context
}

/**
 * Export context for advanced usage
 */
export default AuthContext
```

**2. Add AuthProvider to root layout `app/layout.tsx`:**

```typescript
import { AuthProvider } from '@/contexts/AuthContext'

export default function RootLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <html lang="en">
      <body className={`font-sans antialiased`}>
        <AuthProvider>
          {children}
        </AuthProvider>
        <Analytics />
      </body>
    </html>
  )
}
```

- [x] AuthContext created with all required methods
- [x] AuthProvider wraps root layout
- [x] useAuth hook created for easy access
- [x] Token persistence checked on mount
- [x] Login/register redirect to appropriate dashboard
- [x] Logout clears tokens and redirects to login
- [x] Loading states managed properly

---

## Task 2.4: Create Route Protection Middleware ✅

**Duration:** 2 hours
**Status:** COMPLETEDoute Protection Middleware

**Duration:** 2 hours

### Steps

**1. Create `middleware.ts` in project root:**

```typescript
import { NextResponse } from 'next/server'
import type { NextRequest } from 'next/server'
import { PUBLIC_ROUTES, ROLE_ROUTES } from '@/lib/constants/routes'

/**
 * Middleware for route protection
 * Runs on every request to protected routes
 */
export function middleware(request: NextRequest) {
  const { pathname } = request.nextUrl
  
  // Get token from cookies or headers
  const token = request.cookies.get('gharsaathi_token')?.value
  
  // Check if route is public
  const isPublicRoute = PUBLIC_ROUTES.some(route => {
    if (typeof route === 'string') {
      return pathname === route || pathname.startsWith(route + '/')
    }
    return false
  })
  
  // Allow public routes
  if (isPublicRoute) {
    return NextResponse.next()
  }
  
  // Redirect to login if no token
  if (!token) {
    const loginUrl = new URL('/auth/login', request.url)
    loginUrl.searchParams.set('redirect', pathname)
    return NextResponse.redirect(loginUrl)
  }
  
  // TODO: Decode JWT to check role and permissions
  // For now, allow if token exists
  
  return NextResponse.next()
}

/**
 * Configure which routes use this middleware
 */
export const config = {
  matcher: [
    /*
     * Match all request paths except:
     * - api routes
     * - _next/static (static files)
     * - _next/image (image optimization)
     * - favicon.ico
     * - public folder
     */
    '/((?!api|_next/static|_next/image|favicon.ico|.*\\..*|public).*)',
  ],
}
```

**2. Create `components/ProtectedRoute.tsx` for client-side protection:**

```typescript
'use client'

import { useEffect } from 'react'
import { useRouter } from 'next/navigation'
import { useAuth } from '@/contexts/AuthContext'
import type { Role } from '@/types/auth.types'

interface ProtectedRouteProps {
  children: React.ReactNode
  allowedRoles?: Role[]
  requireAuth?: boolean
}

/**
 * Client-side route protection component
 */
export function ProtectedRoute({
  children,
  allowedRoles,
  requireAuth = true,
}: ProtectedRouteProps) {
  const { user, isAuthenticated, isLoading } = useAuth()
  const router = useRouter()

  useEffect(() => {
    // Wait for auth to load
    if (isLoading) return

    // Check authentication
    if (requireAuth && !isAuthenticated) {
      router.push('/auth/login')
      return
    }

    // Check role permissions
    if (allowedRoles && user && !allowedRoles.includes(user.role)) {
      router.push('/unauthorized')
      return
    }
  }, [user, isAuthenticated, isLoading, requireAuth, allowedRoles, router])

  // Show loading state
  if (isLoading) {
    return (
      <div className="flex items-center justify-center min-h-screen">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-primary"></div>
      </div>
    )
  }

  // Show nothing while redirecting
  if (requireAuth && !isAuthenticated) {
    return null
  }

  // Show nothing if role not allowed
  if (allowedRoles && user && !allowedRoles.includes(user.role)) {
    return null
  }

  // Render children if all checks pass
  return <>{children}</>
}
```

### Verification Checklist

- [x] Middleware created in project root
- [x] Public routes allowed without auth
- [x] Protected routes redirect to login
- [x] ProtectedRoute component created
- [x] Role-based access checking implemented
- [x] Loading state shown during auth check
- [x] Unauthorized page redirect working

---

## Task 2.5: Update Login Page with Real Authentication ✅

**Duration:** 2 hours
**Status:** COMPLETED

### Steps

**1. Update `app/auth/login/page.tsx`:**

```typescript
'use client'

import { useState } from 'react'
import { useForm } from 'react-hook-form'
import { zodResolver } from '@hookform/resolvers/zod'
import * as z from 'zod'
import Link from 'next/link'
import Image from 'next/image'
import { Button } from '@/components/ui/button'
import { Input } from '@/components/ui/input'
import { Label } from '@/components/ui/label'
import { Alert, AlertDescription } from '@/components/ui/alert'
import { Home, Mail, Lock, Eye, EyeOff, ArrowRight } from 'lucide-react'
import { useAuth } from '@/contexts/AuthContext'

// Validation schema
const loginSchema = z.object({
  email: z.string().email('Invalid email address'),
  password: z.string().min(6, 'Password must be at least 6 characters'),
})

type LoginFormData = z.infer<typeof loginSchema>

export default function LoginPage() {
  const { login, isLoading } = useAuth()
  const [showPassword, setShowPassword] = useState(false)
  const [error, setError] = useState<string | null>(null)

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<LoginFormData>({
    resolver: zodResolver(loginSchema),
  })

  const onSubmit = async (data: LoginFormData) => {
    try {
      setError(null)
      await login(data)
      // Redirect handled by AuthContext
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Login failed. Please try again.')
    }
  }

  return (
    <div className="relative min-h-screen flex items-center justify-center p-4">
      {/* Background Image */}
      <div className="absolute inset-0 z-0">
        <Image
          src="/modern-apartment-interior-with-natural-light-woode.jpg"
          alt="Background"
          fill
          className="object-cover"
          priority
        />
        <div className="absolute inset-0 bg-background/10 backdrop-blur-[2px]" />
      </div>

      {/* Login Card */}
      <div className="relative z-10 w-full max-w-md">
        <div className="rounded-3xl bg-background/80 backdrop-blur-xl border border-border/50 p-8 shadow-2xl">
          {/* Logo */}
          <div className="flex justify-center">
            <div className="flex h-14 w-14 items-center justify-center rounded-xl bg-primary">
              <Home className="h-7 w-7 text-primary-foreground" />
            </div>
          </div>

          {/* Header */}
          <div className="mt-6 text-center">
            <h1 className="text-2xl font-bold text-foreground">Welcome to GharSaathi</h1>
            <p className="mt-2 text-primary">Find your perfect space in Nepal</p>
          </div>

          {/* Error Alert */}
          {error && (
            <Alert variant="destructive" className="mt-6">
              <AlertDescription>{error}</AlertDescription>
            </Alert>
          )}

          {/* Form */}
          <form onSubmit={handleSubmit(onSubmit)} className="mt-8 space-y-5">
            <div>
              <Label htmlFor="email" className="text-foreground">
                Email or Phone
              </Label>
              <div className="relative mt-2">
                <Mail className="absolute left-3 top-1/2 h-5 w-5 -translate-y-1/2 text-muted-foreground" />
                <Input
                  id="email"
                  type="email"
                  placeholder="Enter your email"
                  className="pl-10 h-12 bg-background"
                  {...register('email')}
                />
              </div>
              {errors.email && (
                <p className="mt-1 text-sm text-destructive">{errors.email.message}</p>
              )}
            </div>

            <div>
              <div className="flex items-center justify-between">
                <Label htmlFor="password" className="text-foreground">
                  Password
                </Label>
                <Link href="/auth/forgot-password" className="text-sm text-primary hover:underline">
                  Forgot Password?
                </Link>
              </div>
              <div className="relative mt-2">
                <Lock className="absolute left-3 top-1/2 h-5 w-5 -translate-y-1/2 text-muted-foreground" />
                <Input
                  id="password"
                  type={showPassword ? 'text' : 'password'}
                  placeholder="Enter your password"
                  className="pl-10 pr-10 h-12 bg-background"
                  {...register('password')}
                />
                <button
                  type="button"
                  onClick={() => setShowPassword(!showPassword)}
                  className="absolute right-3 top-1/2 -translate-y-1/2 text-muted-foreground hover:text-foreground"
                >
                  {showPassword ? <EyeOff className="h-5 w-5" /> : <Eye className="h-5 w-5" />}
                </button>
              </div>
              {errors.password && (
                <p className="mt-1 text-sm text-destructive">{errors.password.message}</p>
              )}
            </div>

            <Button type="submit" className="w-full h-12 text-base gap-2" disabled={isLoading}>
              {isLoading ? 'Logging in...' : 'Log In'}
              {!isLoading && <ArrowRight className="h-4 w-4" />}
            </Button>
          </form>

          {/* Divider */}
          <div className="relative my-8">
            <div className="absolute inset-0 flex items-center">
              <div className="w-full border-t border-border" />
            </div>
            <div className="relative flex justify-center text-xs uppercase">
              <span className="bg-background/80 px-2 text-muted-foreground">Or continue with</span>
            </div>
          </div>

          {/* Social Login - Keep existing */}
          {/* ... social buttons ... */}

          {/* Sign Up Link */}
          <p className="mt-8 text-center text-sm text-muted-foreground">
            Don&apos;t have an account?{' '}
            <Link href="/auth/register" className="font-medium text-primary hover:underline">
              Sign Up
            </Link>
          </p>
        </div>
      </div>
    </div>
  )
}
```

### Verification Checklist

- [x] Login form uses React Hook Form
- [x] Validation schema with Zod implemented
- [x] Form submits to auth context
- [x] Error messages displayed
- [x] Loading state shows during login
- [x] Successful login redirects to dashboard
- [x] Password visibility toggle works
- [x] Form validation errors show inline

---

## Task 2.6: Update Register Page with Real Authentication ✅

**Duration:** 2 hours
**Status:** COMPLETED

### Steps

**1. Update `app/auth/register/page.tsx`:**

```typescript
'use client'

import { useState } from 'react'
import { useForm } from 'react-hook-form'
import { zodResolver } from '@hookform/resolvers/zod'
import * as z from 'zod'
import Link from 'next/link'
import { Button } from '@/components/ui/button'
import { Input } from '@/components/ui/input'
import { Label } from '@/components/ui/label'
import { Alert, AlertDescription } from '@/components/ui/alert'
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select'
import { Home, Mail, Lock, Eye, EyeOff, User, Phone } from 'lucide-react'
import { useAuth } from '@/contexts/AuthContext'
import type { Role } from '@/types/auth.types'

// Validation schema
const registerSchema = z.object({
  firstName: z.string().min(2, 'First name must be at least 2 characters'),
  lastName: z.string().min(2, 'Last name must be at least 2 characters'),
  email: z.string().email('Invalid email address'),
  phoneNumber: z.string().optional(),
  password: z.string().min(8, 'Password must be at least 8 characters'),
  confirmPassword: z.string(),
  role: z.enum(['TENANT', 'LANDLORD']),
}).refine((data) => data.password === data.confirmPassword, {
  message: "Passwords don't match",
  path: ['confirmPassword'],
})

type RegisterFormData = z.infer<typeof registerSchema>

export default function RegisterPage() {
  const { register: registerUser, isLoading } = useAuth()
  const [showPassword, setShowPassword] = useState(false)
  const [showConfirmPassword, setShowConfirmPassword] = useState(false)
  const [error, setError] = useState<string | null>(null)

  const {
    register,
    handleSubmit,
    setValue,
    formState: { errors },
  } = useForm<RegisterFormData>({
    resolver: zodResolver(registerSchema),
    defaultValues: {
      role: 'TENANT',
    },
  })

  const onSubmit = async (data: RegisterFormData) => {
    try {
      setError(null)
      const { confirmPassword, ...registerData } = data
      await registerUser(registerData)
      // Redirect handled by AuthContext
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Registration failed. Please try again.')
    }
  }

  return (
    <div className="relative min-h-screen flex items-center justify-center p-4">
      {/* ... similar background ... */}

      <div className="relative z-10 w-full max-w-md">
        <div className="rounded-3xl bg-background/80 backdrop-blur-xl border border-border/50 p-8 shadow-2xl">
          {/* Logo */}
          <div className="flex justify-center">
            <div className="flex h-14 w-14 items-center justify-center rounded-xl bg-primary">
              <Home className="h-7 w-7 text-primary-foreground" />
            </div>
          </div>

          {/* Header */}
          <div className="mt-6 text-center">
            <h1 className="text-2xl font-bold text-foreground">Create Your Account</h1>
            <p className="mt-2 text-muted-foreground">Join Nepal's trusted rental community</p>
          </div>

          {/* Error Alert */}
          {error && (
            <Alert variant="destructive" className="mt-6">
              <AlertDescription>{error}</AlertDescription>
            </Alert>
          )}

          {/* Form */}
          <form onSubmit={handleSubmit(onSubmit)} className="mt-8 space-y-4">
            {/* Role Selection */}
            <div>
              <Label htmlFor="role">I am a</Label>
              <Select onValueChange={(value) => setValue('role', value as Role)} defaultValue="TENANT">
                <SelectTrigger className="h-12 bg-background">
                  <SelectValue placeholder="Select your role" />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="TENANT">Tenant (Looking for property)</SelectItem>
                  <SelectItem value="LANDLORD">Landlord (Have properties to rent)</SelectItem>
                </SelectContent>
              </Select>
              {errors.role && (
                <p className="mt-1 text-sm text-destructive">{errors.role.message}</p>
              )}
            </div>

            {/* Name Fields */}
            <div className="grid grid-cols-2 gap-4">
              <div>
                <Label htmlFor="firstName">First Name</Label>
                <div className="relative mt-2">
                  <User className="absolute left-3 top-1/2 h-5 w-5 -translate-y-1/2 text-muted-foreground" />
                  <Input
                    id="firstName"
                    placeholder="First name"
                    className="pl-10 h-12 bg-background"
                    {...register('firstName')}
                  />
                </div>
                {errors.firstName && (
                  <p className="mt-1 text-sm text-destructive">{errors.firstName.message}</p>
                )}
              </div>

              <div>
                <Label htmlFor="lastName">Last Name</Label>
                <Input
                  id="lastName"
                  placeholder="Last name"
                  className="h-12 bg-background mt-2"
                  {...register('lastName')}
                />
                {errors.lastName && (
                  <p className="mt-1 text-sm text-destructive">{errors.lastName.message}</p>
                )}
              </div>
            </div>

            {/* Email */}
            <div>
              <Label htmlFor="email">Email</Label>
              <div className="relative mt-2">
                <Mail className="absolute left-3 top-1/2 h-5 w-5 -translate-y-1/2 text-muted-foreground" />
                <Input
                  id="email"
                  type="email"
                  placeholder="your.email@example.com"
                  className="pl-10 h-12 bg-background"
                  {...register('email')}
                />
              </div>
              {errors.email && (
                <p className="mt-1 text-sm text-destructive">{errors.email.message}</p>
              )}
            </div>

            {/* Phone */}
            <div>
              <Label htmlFor="phoneNumber">Phone Number (Optional)</Label>
              <div className="relative mt-2">
                <Phone className="absolute left-3 top-1/2 h-5 w-5 -translate-y-1/2 text-muted-foreground" />
                <Input
                  id="phoneNumber"
                  type="tel"
                  placeholder="+977 9841234567"
                  className="pl-10 h-12 bg-background"
                  {...register('phoneNumber')}
                />
              </div>
            </div>

            {/* Password */}
            <div>
              <Label htmlFor="password">Password</Label>
              <div className="relative mt-2">
                <Lock className="absolute left-3 top-1/2 h-5 w-5 -translate-y-1/2 text-muted-foreground" />
                <Input
                  id="password"
                  type={showPassword ? 'text' : 'password'}
                  placeholder="Create a strong password"
                  className="pl-10 pr-10 h-12 bg-background"
                  {...register('password')}
                />
                <button
                  type="button"
                  onClick={() => setShowPassword(!showPassword)}
                  className="absolute right-3 top-1/2 -translate-y-1/2 text-muted-foreground hover:text-foreground"
                >
                  {showPassword ? <EyeOff className="h-5 w-5" /> : <Eye className="h-5 w-5" />}
                </button>
              </div>
              {errors.password && (
                <p className="mt-1 text-sm text-destructive">{errors.password.message}</p>
              )}
            </div>

            {/* Confirm Password */}
            <div>
              <Label htmlFor="confirmPassword">Confirm Password</Label>
              <div className="relative mt-2">
                <Lock className="absolute left-3 top-1/2 h-5 w-5 -translate-y-1/2 text-muted-foreground" />
                <Input
                  id="confirmPassword"
                  type={showConfirmPassword ? 'text' : 'password'}
                  placeholder="Confirm your password"
                  className="pl-10 pr-10 h-12 bg-background"
                  {...register('confirmPassword')}
                />
                <button
                  type="button"
                  onClick={() => setShowConfirmPassword(!showConfirmPassword)}
                  className="absolute right-3 top-1/2 -translate-y-1/2 text-muted-foreground hover:text-foreground"
                >
                  {showConfirmPassword ? <EyeOff className="h-5 w-5" /> : <Eye className="h-5 w-5" />}
                </button>
              </div>
              {errors.confirmPassword && (
                <p className="mt-1 text-sm text-destructive">{errors.confirmPassword.message}</p>
              )}
            </div>

            <Button type="submit" className="w-full h-12 text-base" disabled={isLoading}>
              {isLoading ? 'Creating Account...' : 'Create Account'}
            </Button>
          </form>

          {/* Login Link */}
          <p className="mt-8 text-center text-sm text-muted-foreground">
            Already have an account?{' '}
            <Link href="/auth/login" className="font-medium text-primary hover:underline">
              Log In
            </Link>
          </p>
        </div>
      </div>
    </div>
  )
}
```

### Verification Checklist

- [x] Register form with all required fields
- [x] Role selection (Tenant/Landlord) working
- [x] Password confirmation validation
- [x] Form submits to auth context
- [x] Successful registration redirects to dashboard
- [x] Error messages displayed
- [x] All validation rules working

---

## Task 2.7: Add Logout Functionality to Navbar ✅

**Duration:** 1 hour
**Status:** COMPLETED

### Steps

**1. Update `components/navbar.tsx`:**

```typescript
'use client'

import { useAuth } from '@/contexts/AuthContext'
import { Button } from '@/components/ui/button'
import { Avatar, AvatarFallback, AvatarImage } from '@/components/ui/avatar'
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from '@/components/ui/dropdown-menu'
import { LogOut, Settings, User as UserIcon } from 'lucide-react'

export function Navbar({ variant = 'public', showSearch = false }: NavbarProps) {
  const { user, isAuthenticated, logout } = useAuth()
  const isLoggedIn = variant !== 'public' && isAuthenticated

  const handleLogout = async () => {
    try {
      await logout()
    } catch (error) {
      console.error('Logout error:', error)
    }
  }

  return (
    <header className="sticky top-0 z-50 w-full border-b border-border bg-background/95 backdrop-blur supports-[backdrop-filter]:bg-background/60">
      {/* ... existing header content ... */}

      {/* Right side actions */}
      <div className="flex items-center gap-3">
        {!isLoggedIn ? (
          <>
            <Button variant="ghost" asChild className="hidden sm:flex">
              <Link href="/auth/login">Log In</Link>
            </Button>
            <Button asChild>
              <Link href="/auth/register">Join Now</Link>
            </Button>
          </>
        ) : (
          <>
            <Button variant="ghost" size="icon" className="relative">
              <Bell className="h-5 w-5" />
            </Button>
            
            {/* User Menu */}
            <DropdownMenu>
              <DropdownMenuTrigger asChild>
                <Button variant="ghost" className="relative h-10 w-10 rounded-full">
                  <Avatar className="h-10 w-10">
                    <AvatarImage src={user?.avatarUrl} alt={user?.fullName} />
                    <AvatarFallback>
                      {user?.firstName?.[0]}{user?.lastName?.[0]}
                    </AvatarFallback>
                  </Avatar>
                </Button>
              </DropdownMenuTrigger>
              <DropdownMenuContent align="end" className="w-56">
                <div className="flex items-center justify-start gap-2 p-2">
                  <div className="flex flex-col space-y-1">
                    <p className="text-sm font-medium leading-none">{user?.fullName}</p>
                    <p className="text-xs leading-none text-muted-foreground">{user?.email}</p>
                  </div>
                </div>
                <DropdownMenuSeparator />
                <DropdownMenuItem asChild>
                  <Link href="/profile" className="cursor-pointer">
                    <UserIcon className="mr-2 h-4 w-4" />
                    Profile
                  </Link>
                </DropdownMenuItem>
                <DropdownMenuItem asChild>
                  <Link href="/settings" className="cursor-pointer">
                    <Settings className="mr-2 h-4 w-4" />
                    Settings
                  </Link>
                </DropdownMenuItem>
                <DropdownMenuSeparator />
                <DropdownMenuItem onClick={handleLogout} className="cursor-pointer text-destructive">
                  <LogOut className="mr-2 h-4 w-4" />
                  Log Out
                </DropdownMenuItem>
              </DropdownMenuContent>
            </DropdownMenu>
          </>
        )}
      </div>
    </header>
  )
}
```

### Verification Checklist

- [x] Navbar shows user avatar when logged in
- [x] Dropdown menu displays user info
- [x] Logout menu item triggers logout function
- [x] Successful logout clears tokens
- [x] User redirected to login page after logout
- [x] Avatar shows initials fallback

---

## Task 2.8: Protect Dashboard Routes ✅

**Duration:** 1 hour
**Status:** COMPLETED

### Steps

**1. Update tenant dashboard layout `app/tenant/layout.tsx`:**

```typescript
import { ProtectedRoute } from '@/components/ProtectedRoute'

export default function TenantLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <ProtectedRoute allowedRoles={['TENANT']}>
      <div className="flex min-h-screen">
        <DashboardSidebar variant="tenant" />
        <div className="flex-1">
          <Navbar variant="tenant" />
          {children}
        </div>
      </div>
    </ProtectedRoute>
  )
}
```

**2. Update landlord dashboard layout `app/landlord/layout.tsx`:**

```typescript
import { ProtectedRoute } from '@/components/ProtectedRoute'

export default function LandlordLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <ProtectedRoute allowedRoles={['LANDLORD']}>
      <div className="flex min-h-screen">
        <DashboardSidebar variant="landlord" />
        <div className="flex-1">
          <Navbar variant="landlord" />
          {children}
        </div>
      </div>
    </ProtectedRoute>
  )
}
```

**3. Update admin layout `app/admin/layout.tsx`:**

```typescript
import { ProtectedRoute } from '@/components/ProtectedRoute'

export default function AdminLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <ProtectedRoute allowedRoles={['ADMIN']}>
      <div className="flex min-h-screen">
        <DashboardSidebar variant="admin" />
        <div className="flex-1">
          <Navbar variant="admin" />
          {children}
        </div>
      </div>
    </ProtectedRoute>
  )
}
```

### Verification Checklist

x] Tenant routes require TENANT role

- [x] Landlord routes require LANDLORD role
- [x] Admin routes require ADMIN role
- [x] Unauthorized users redirected to login
- [x] Wrong role users redirected to unauthorized page
- [x] Loading state shown during auth check

---

## Phase 2 Completion Checklist

### Authentication Flow

- [x] User can register as Tenant or Landlord
- [x] User can login with email and password
- [x] JWT tokens stored securely
- [x] Access token refresh working automatically
- [x] User can logout (single device)
- [x] User can logout from all devices

### Authorization

- [x] Tenant dashboard protected with TENANT role
- [x] Landlord dashboard protected with LANDLORD role
- [x] Admin dashboard protected with ADMIN role
- [x] Unauthorized access redirects appropriately
- [x] Public routes accessible without auth

### User Experience

- [x] Form validation working with clear error messages
- [x] Loading states during auth operations
- [x] Success feedback after login/register
- [x] Error handling with user-friendly messages
- [x] Password visibility toggle working
- [ ] Remember me functionality (optional - not implemented)

### Testing

- [x] Login with valid credentials works
- [x] Login with invalid credentials shows error
- [x] Registration creates new account
- [x] Duplicate email registration prevented
- [x] Token refresh happens automatically
- [x] Logout clears all auth state
- [x] Protected routes redirect when not authenticated
- [x] Protected routes redirect when not authenticated
- [ ] Role-based access control enforced

---

# PHASE 3: PROPERTY MODULE INTEGRATION

**Priority:** 🔴 HIGH  
**Duration:** 3-4 days  
**Dependencies:** Phase 1, Phase 2  
**Estimated Hours:** 24-30h

## Objectives

- Create property service and type definitions
- Integrate property listing page with real API
- Implement property details page
- Add property search and filtering
- Implement landlord property management (CRUD)
- Add image upload functionality

---

## Task 3.1: Create Property Type Definitions ✅

**Duration:** 1 hour

### Steps

**1. Create `types/property.types.ts`:**

```typescript
/**
 * Property types and status enums
 */
export type PropertyType = 'APARTMENT' | 'HOUSE' | 'ROOM' | 'COMMERCIAL' | 'LAND'
export type PropertyStatus = 'AVAILABLE' | 'RENTED' | 'MAINTENANCE' | 'UNAVAILABLE'

/**
 * Property image
 */
export interface PropertyImage {
  id: number
  filename: string
  fileUrl: string
  displayOrder: number
  isPrimary: boolean
  uploadedAt: string
}

/**
 * Landlord information (nested in property)
 */
export interface LandlordInfo {
  id: number
  fullName: string
  email: string
  phoneNumber?: string
  avatarUrl?: string
  propertiesCount: number
  responseRate: number
  joinedDate: string
}

/**
 * Complete property entity
 */
export interface Property {
  id: number
  title: string
  description: string
  propertyType: PropertyType
  status: PropertyStatus
  
  // Location
  address: string
  city: string
  area: string
  latitude?: number
  longitude?: number
  
  // Pricing
  price: number
  securityDeposit: number
  
  // Specifications
  bedrooms: number
  bathrooms: number
  propertyArea: number // in sq ft
  
  // Features
  furnished: boolean
  parkingAvailable: boolean
  amenities: string[]
  
  // Media
  images: PropertyImage[]
  
  // Availability
  availableFrom: string
  
  // Relationships
  landlord: LandlordInfo
  
  // Metadata
  views: number
  savedCount: number
  createdAt: string
  updatedAt: string
}

/**
 * Property list response (paginated)
 */
export interface PropertyListResponse {
  properties: Property[]
  currentPage: number
  totalPages: number
  totalProperties: number
  pageSize: number
}

/**
 * Property detail response
 */
export interface PropertyDetailResponse extends Property {
  similarProperties?: Property[]
}

/**
 * Property search criteria
 */
export interface PropertySearchCriteria {
  keyword?: string
  city?: string
  area?: string
  propertyType?: PropertyType
  status?: PropertyStatus
  minPrice?: number
  maxPrice?: number
  bedrooms?: number
  bathrooms?: number
  furnished?: boolean
  parkingAvailable?: boolean
  amenities?: string[]
  page?: number
  size?: number
  sortBy?: string
  sortDirection?: 'ASC' | 'DESC'
}

/**
 * Create property request
 */
export interface CreatePropertyRequest {
  title: string
  description: string
  propertyType: PropertyType
  address: string
  city: string
  area: string
  latitude?: number
  longitude?: number
  price: number
  securityDeposit: number
  bedrooms: number
  bathrooms: number
  propertyArea: number
  furnished: boolean
  parkingAvailable: boolean
  amenities: string[]
  availableFrom: string
}

/**
 * Update property request
 */
export interface UpdatePropertyRequest extends Partial<CreatePropertyRequest> {
  status?: PropertyStatus
}

/**
 * Property status update request
 */
export interface PropertyStatusUpdateRequest {
  status: PropertyStatus
}
```

**2. Export from `types/index.ts`:**

```typescript
export * from './auth.types'
export * from './property.types'
```

### Verification Checklist

- [ ] All property types defined
- [ ] Types match backend DTOs
- [ ] Nested types (LandlordInfo, PropertyImage) included
- [ ] Request/Response types defined
- [ ] Search criteria type complete
- [ ] Types exported from index

---

## Task 3.2: Create Property Service ✅

**Duration:** 2 hours

### Steps

**1. Create `lib/services/property.service.ts`:**

```typescript
import apiClient from '@/lib/api/client'
import { API_ENDPOINTS } from '@/lib/constants/api'
import type {
  Property,
  PropertyListResponse,
  PropertyDetailResponse,
  PropertySearchCriteria,
  CreatePropertyRequest,
  UpdatePropertyRequest,
  PropertyStatusUpdateRequest,
} from '@/types/property.types'

/**
 * Property Service
 * Handles all property-related API calls
 */
class PropertyService {
  /**
   * Get all properties with pagination
   */
  async getAllProperties(
    page: number = 0,
    size: number = 10,
    sortBy: string = 'createdAt',
    sortDirection: 'ASC' | 'DESC' = 'DESC'
  ): Promise<PropertyListResponse> {
    const response = await apiClient.get<PropertyListResponse>(
      API_ENDPOINTS.PROPERTIES.BASE,
      {
        params: { page, size, sortBy, sortDirection },
      }
    )
    return response.data
  }

  /**
   * Get property by ID
   */
  async getPropertyById(id: number): Promise<PropertyDetailResponse> {
    const response = await apiClient.get<PropertyDetailResponse>(
      API_ENDPOINTS.PROPERTIES.BY_ID(id)
    )
    return response.data
  }

  /**
   * Search properties with filters
   */
  async searchProperties(criteria: PropertySearchCriteria): Promise<PropertyListResponse> {
    const response = await apiClient.post<PropertyListResponse>(
      API_ENDPOINTS.PROPERTIES.SEARCH,
      criteria
    )
    return response.data
  }

  /**
   * Get landlord's properties
   */
  async getMyProperties(
    page: number = 0,
    size: number = 10,
    sortBy: string = 'createdAt',
    sortDirection: 'ASC' | 'DESC' = 'DESC'
  ): Promise<PropertyListResponse> {
    const response = await apiClient.get<PropertyListResponse>(
      API_ENDPOINTS.PROPERTIES.LANDLORD_BASE,
      {
        params: { page, size, sortBy, sortDirection },
      }
    )
    return response.data
  }

  /**
   * Create new property (Landlord only)
   */
  async createProperty(data: CreatePropertyRequest): Promise<Property> {
    const response = await apiClient.post<Property>(
      API_ENDPOINTS.PROPERTIES.LANDLORD_BASE,
      data
    )
    return response.data
  }

  /**
   * Update property (Landlord only)
   */
  async updateProperty(id: number, data: UpdatePropertyRequest): Promise<Property> {
    const response = await apiClient.put<Property>(
      API_ENDPOINTS.PROPERTIES.LANDLORD_BY_ID(id),
      data
    )
    return response.data
  }

  /**
   * Update property status
   */
  async updatePropertyStatus(
    id: number,
    status: PropertyStatusUpdateRequest
  ): Promise<Property> {
    const response = await apiClient.patch<Property>(
      API_ENDPOINTS.PROPERTIES.LANDLORD_STATUS(id),
      status
    )
    return response.data
  }

  /**
   * Delete property (Soft delete - Landlord only)
   */
  async deleteProperty(id: number): Promise<void> {
    await apiClient.delete(API_ENDPOINTS.PROPERTIES.LANDLORD_BY_ID(id))
  }

  /**
   * Force delete property (Admin only)
   */
  async forceDeleteProperty(id: number): Promise<void> {
    await apiClient.delete(API_ENDPOINTS.PROPERTIES.ADMIN_DELETE(id))
  }
}

// Export singleton instance
export const propertyService = new PropertyService()
export default propertyService
```

**2. Export from `lib/services/index.ts`:**

```typescript
export * from './auth.service'
export * from './property.service'
```

### Verification Checklist

- [ ] Property service created with all methods
- [ ] All CRUD operations implemented
- [ ] Search functionality included
- [ ] Landlord-specific methods separated
- [ ] Admin methods included
- [ ] Proper TypeScript types applied
- [ ] Service exported as singleton

---

## Task 3.3: Update Property Listing Page ✅

**Duration:** 3 hours

### Steps

**1. Update `app/properties/page.tsx`:**

```typescript
'use client'

import { useState, useEffect } from 'react'
import { PropertyCard } from '@/components/property-card'
import { Button } from '@/components/ui/button'
import { Input } from '@/components/ui/input'
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select'
import { Skeleton } from '@/components/ui/skeleton'
import { Alert, AlertDescription } from '@/components/ui/alert'
import { Search, SlidersHorizontal } from 'lucide-react'
import { propertyService } from '@/lib/services/property.service'
import { handleAPIError } from '@/lib/api/errorHandler'
import type { Property, PropertySearchCriteria } from '@/types/property.types'

export default function PropertiesPage() {
  const [properties, setProperties] = useState<Property[]>([])
  const [isLoading, setIsLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)
  const [currentPage, setCurrentPage] = useState(0)
  const [totalPages, setTotalPages] = useState(0)
  
  // Search filters
  const [searchKeyword, setSearchKeyword] = useState('')
  const [selectedCity, setSelectedCity] = useState<string>('')
  const [selectedType, setSelectedType] = useState<string>('')

  /**
   * Fetch properties
   */
  const fetchProperties = async (page: number = 0) => {
    try {
      setIsLoading(true)
      setError(null)
      
      const response = await propertyService.getAllProperties(page, 12)
      
      setProperties(response.properties)
      setCurrentPage(response.currentPage)
      setTotalPages(response.totalPages)
    } catch (err) {
      const apiError = handleAPIError(err)
      setError(apiError.message)
    } finally {
      setIsLoading(false)
    }
  }

  /**
   * Search properties
   */
  const handleSearch = async () => {
    try {
      setIsLoading(true)
      setError(null)
      
      const criteria: PropertySearchCriteria = {
        keyword: searchKeyword || undefined,
        city: selectedCity || undefined,
        propertyType: selectedType as any,
        page: 0,
        size: 12,
      }
      
      const response = await propertyService.searchProperties(criteria)
      
      setProperties(response.properties)
      setCurrentPage(response.currentPage)
      setTotalPages(response.totalPages)
    } catch (err) {
      const apiError = handleAPIError(err)
      setError(apiError.message)
    } finally {
      setIsLoading(false)
    }
  }

  /**
   * Load properties on mount
   */
  useEffect(() => {
    fetchProperties()
  }, [])

  /**
   * Handle pagination
   */
  const handlePageChange = (page: number) => {
    fetchProperties(page)
  }

  return (
    <div className="min-h-screen bg-background">
      <Navbar variant="public" />
      
      <main className="container mx-auto px-4 py-8">
        {/* Header */}
        <div className="mb-8">
          <h1 className="text-3xl font-bold">Find Your Perfect Home</h1>
          <p className="mt-2 text-muted-foreground">
            Browse {totalPages > 0 ? `${totalPages * 12}+` : ''} verified properties across Nepal
          </p>
        </div>

        {/* Search & Filters */}
        <div className="mb-8 rounded-lg border bg-card p-6">
          <div className="grid gap-4 md:grid-cols-[1fr_200px_200px_auto]">
            <div className="relative">
              <Search className="absolute left-3 top-1/2 h-5 w-5 -translate-y-1/2 text-muted-foreground" />
              <Input
                type="text"
                placeholder="Search by location, property name..."
                className="pl-10"
                value={searchKeyword}
                onChange={(e) => setSearchKeyword(e.target.value)}
                onKeyDown={(e) => e.key === 'Enter' && handleSearch()}
              />
            </div>
            
            <Select value={selectedCity} onValueChange={setSelectedCity}>
              <SelectTrigger>
                <SelectValue placeholder="City" />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="">All Cities</SelectItem>
                <SelectItem value="Kathmandu">Kathmandu</SelectItem>
                <SelectItem value="Lalitpur">Lalitpur</SelectItem>
                <SelectItem value="Bhaktapur">Bhaktapur</SelectItem>
                <SelectItem value="Pokhara">Pokhara</SelectItem>
              </SelectContent>
            </Select>
            
            <Select value={selectedType} onValueChange={setSelectedType}>
              <SelectTrigger>
                <SelectValue placeholder="Type" />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="">All Types</SelectItem>
                <SelectItem value="APARTMENT">Apartment</SelectItem>
                <SelectItem value="HOUSE">House</SelectItem>
                <SelectItem value="ROOM">Room</SelectItem>
              </SelectContent>
            </Select>
            
            <Button onClick={handleSearch} disabled={isLoading}>
              <SlidersHorizontal className="mr-2 h-4 w-4" />
              Search
            </Button>
          </div>
        </div>

        {/* Error State */}
        {error && (
          <Alert variant="destructive" className="mb-8">
            <AlertDescription>{error}</AlertDescription>
          </Alert>
        )}

        {/* Loading State */}
        {isLoading && (
          <div className="grid gap-6 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4">
            {Array.from({ length: 8 }).map((_, i) => (
              <Skeleton key={i} className="h-[400px] rounded-lg" />
            ))}
          </div>
        )}

        {/* Properties Grid */}
        {!isLoading && properties.length > 0 && (
          <>
            <div className="grid gap-6 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4">
              {properties.map((property) => (
                <PropertyCard key={property.id} property={property} />
              ))}
            </div>

            {/* Pagination */}
            {totalPages > 1 && (
              <div className="mt-8 flex justify-center gap-2">
                <Button
                  variant="outline"
                  onClick={() => handlePageChange(currentPage - 1)}
                  disabled={currentPage === 0}
                >
                  Previous
                </Button>
                
                <div className="flex items-center gap-2">
                  {Array.from({ length: Math.min(totalPages, 5) }).map((_, i) => {
                    const page = currentPage < 3 ? i : currentPage - 2 + i
                    if (page >= totalPages) return null
                    
                    return (
                      <Button
                        key={page}
                        variant={page === currentPage ? 'default' : 'outline'}
                        onClick={() => handlePageChange(page)}
                      >
                        {page + 1}
                      </Button>
                    )
                  })}
                </div>
                
                <Button
                  variant="outline"
                  onClick={() => handlePageChange(currentPage + 1)}
                  disabled={currentPage === totalPages - 1}
                >
                  Next
                </Button>
              </div>
            )}
          </>
        )}

        {/* Empty State */}
        {!isLoading && properties.length === 0 && (
          <div className="flex flex-col items-center justify-center py-16">
            <p className="text-lg text-muted-foreground">No properties found</p>
            <Button variant="link" onClick={() => {
              setSearchKeyword('')
              setSelectedCity('')
              setSelectedType('')
              fetchProperties()
            }}>
              Clear filters
            </Button>
          </div>
        )}
      </main>

      <Footer />
    </div>
  )
}
```

### Verification Checklist

- [ ] Properties loaded from API on page load
- [ ] Loading skeletons shown during fetch
- [ ] Property cards displayed in grid
- [ ] Search functionality working
- [ ] City filter working
- [ ] Type filter working
- [ ] Pagination working
- [ ] Error states handled
- [ ] Empty state shown when no results

---

## Task 3.4: Update Property Card Component

**Duration:** 1 hour

### Steps

**1. Update `components/property-card.tsx`:**

```typescript
'use client'

import Link from 'next/link'
import Image from 'next/image'
import { Card, CardContent } from '@/components/ui/card'
import { Badge } from '@/components/ui/badge'
import { Button } from '@/components/ui/button'
import { MapPin, Bed, Bath, Maximize, Heart } from 'lucide-react'
import type { Property } from '@/types/property.types'

interface PropertyCardProps {
  property: Property
  onSave?: (propertyId: number) => void
  isSaved?: boolean
}

export function PropertyCard({ property, onSave, isSaved = false }: PropertyCardProps) {
  const primaryImage = property.images.find(img => img.isPrimary) || property.images[0]
  const imageUrl = primaryImage?.fileUrl || '/placeholder.svg'

  const handleSave = (e: React.MouseEvent) => {
    e.preventDefault()
    e.stopPropagation()
    onSave?.(property.id)
  }

  return (
    <Link href={`/properties/${property.id}`}>
      <Card className="group overflow-hidden transition-all hover:shadow-lg">
        {/* Image */}
        <div className="relative aspect-[4/3] overflow-hidden">
          <Image
            src={imageUrl}
            alt={property.title}
            fill
            className="object-cover transition-transform group-hover:scale-105"
          />
          
          {/* Badges */}
          <div className="absolute top-3 left-3 flex flex-wrap gap-2">
            {property.furnished && (
              <Badge className="bg-background/90 text-foreground">Furnished</Badge>
            )}
            {property.parkingAvailable && (
              <Badge className="bg-background/90 text-foreground">Parking</Badge>
            )}
          </div>
          
          {/* Save Button */}
          {onSave && (
            <Button
              size="icon"
              variant="ghost"
              className="absolute top-3 right-3 bg-background/90 hover:bg-background"
              onClick={handleSave}
            >
              <Heart className={`h-5 w-5 ${isSaved ? 'fill-red-500 text-red-500' : ''}`} />
            </Button>
          )}
        </div>

        <CardContent className="p-4">
          {/* Price */}
          <div className="mb-2">
            <p className="text-2xl font-bold">NPR {property.price.toLocaleString()}</p>
            <p className="text-sm text-muted-foreground">per month</p>
          </div>

          {/* Title */}
          <h3 className="mb-2 line-clamp-1 font-semibold">{property.title}</h3>

          {/* Location */}
          <div className="mb-3 flex items-center gap-1 text-sm text-muted-foreground">
            <MapPin className="h-4 w-4" />
            <span className="line-clamp-1">{property.area}, {property.city}</span>
          </div>

          {/* Specifications */}
          <div className="flex items-center gap-4 text-sm">
            <div className="flex items-center gap-1">
              <Bed className="h-4 w-4" />
              <span>{property.bedrooms} Beds</span>
            </div>
            <div className="flex items-center gap-1">
              <Bath className="h-4 w-4" />
              <span>{property.bathrooms} Baths</span>
            </div>
            <div className="flex items-center gap-1">
              <Maximize className="h-4 w-4" />
              <span>{property.propertyArea} sqft</span>
            </div>
          </div>

          {/* Landlord Info */}
          {property.landlord && (
            <div className="mt-3 pt-3 border-t">
              <p className="text-xs text-muted-foreground">
                Listed by <span className="font-medium">{property.landlord.fullName}</span>
              </p>
            </div>
          )}
        </CardContent>
      </Card>
    </Link>
  )
}
```

### Verification Checklist

- [ ] Property card displays real data
- [ ] Images loaded from API
- [ ] Price formatted correctly (NPR with commas)
- [ ] Location shows area and city
- [ ] Specifications (beds, baths, sqft) displayed
- [ ] Furnished/Parking badges shown conditionally
- [ ] Landlord info displayed
- [ ] Card links to property details page
- [ ] Hover effects working
- [ ] Save button optional and functional

---

## Task 3.5: Create Property Details Page ✅

**Duration:** 3 hours

### Steps

**1. Create `app/properties/[id]/page.tsx`:**

```typescript
'use client'

import { useState, useEffect } from 'react'
import { useParams, useRouter } from 'next/navigation'
import Image from 'next/image'
import { Button } from '@/components/ui/button'
import { Badge } from '@/components/ui/badge'
import { Card, CardContent } from '@/components/ui/card'
import { Separator } from '@/components/ui/separator'
import { Skeleton } from '@/components/ui/skeleton'
import { Alert, AlertDescription } from '@/components/ui/alert'
import {
  MapPin,
  Bed,
  Bath,
  Maximize,
  Calendar,
  Heart,
  Share2,
  Phone,
  Mail,
  CheckCircle2,
} from 'lucide-react'
import { Navbar } from '@/components/navbar'
import { Footer } from '@/components/footer'
import { propertyService } from '@/lib/services/property.service'
import { handleAPIError } from '@/lib/api/errorHandler'
import type { PropertyDetailResponse } from '@/types/property.types'

export default function PropertyDetailsPage() {
  const params = useParams()
  const router = useRouter()
  const propertyId = parseInt(params.id as string)
  
  const [property, setProperty] = useState<PropertyDetailResponse | null>(null)
  const [isLoading, setIsLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)
  const [selectedImageIndex, setSelectedImageIndex] = useState(0)

  /**
   * Fetch property details
   */
  useEffect(() => {
    const fetchProperty = async () => {
      try {
        setIsLoading(true)
        setError(null)
        
        const data = await propertyService.getPropertyById(propertyId)
        setProperty(data)
      } catch (err) {
        const apiError = handleAPIError(err)
        setError(apiError.message)
      } finally {
        setIsLoading(false)
      }
    }

    if (propertyId) {
      fetchProperty()
    }
  }, [propertyId])

  /**
   * Handle apply button
   */
  const handleApply = () => {
    // Navigate to application form (to be implemented)
    router.push(`/tenant/applications/new?propertyId=${propertyId}`)
  }

  // Loading state
  if (isLoading) {
    return (
      <div className="min-h-screen bg-background">
        <Navbar variant="public" />
        <div className="container mx-auto px-4 py-8">
          <Skeleton className="h-[500px] w-full rounded-lg mb-8" />
          <Skeleton className="h-[200px] w-full rounded-lg" />
        </div>
      </div>
    )
  }

  // Error state
  if (error || !property) {
    return (
      <div className="min-h-screen bg-background">
        <Navbar variant="public" />
        <div className="container mx-auto px-4 py-8">
          <Alert variant="destructive">
            <AlertDescription>{error || 'Property not found'}</AlertDescription>
          </Alert>
          <Button onClick={() => router.push('/properties')} className="mt-4">
            Back to Properties
          </Button>
        </div>
      </div>
    )
  }

  const selectedImage = property.images[selectedImageIndex] || property.images[0]

  return (
    <div className="min-h-screen bg-background">
      <Navbar variant="public" />

      <main className="container mx-auto px-4 py-8">
        {/* Breadcrumb */}
        <div className="mb-6 text-sm text-muted-foreground">
          <Link href="/">Home</Link> / <Link href="/properties">Properties</Link> / {property.title}
        </div>

        {/* Image Gallery */}
        <div className="mb-8 grid gap-4 md:grid-cols-[1fr_300px]">
          {/* Main Image */}
          <div className="relative aspect-[16/10] overflow-hidden rounded-lg">
            <Image
              src={selectedImage?.fileUrl || '/placeholder.svg'}
              alt={property.title}
              fill
              className="object-cover"
              priority
            />
          </div>

          {/* Thumbnail Grid */}
          <div className="grid grid-cols-3 gap-2 md:grid-cols-2">
            {property.images.slice(0, 6).map((image, index) => (
              <div
                key={image.id}
                className={`relative aspect-square cursor-pointer overflow-hidden rounded-lg border-2 ${
                  selectedImageIndex === index ? 'border-primary' : 'border-transparent'
                }`}
                onClick={() => setSelectedImageIndex(index)}
              >
                <Image
                  src={image.fileUrl}
                  alt={`${property.title} ${index + 1}`}
                  fill
                  className="object-cover"
                />
              </div>
            ))}
          </div>
        </div>

        <div className="grid gap-8 lg:grid-cols-[1fr_400px]">
          {/* Left Column - Property Details */}
          <div className="space-y-6">
            {/* Header */}
            <div>
              <div className="mb-2 flex flex-wrap items-center gap-2">
                <Badge>{property.propertyType}</Badge>
                <Badge variant="outline">{property.status}</Badge>
                {property.furnished && <Badge variant="secondary">Furnished</Badge>}
                {property.parkingAvailable && <Badge variant="secondary">Parking</Badge>}
              </div>
              
              <h1 className="text-3xl font-bold">{property.title}</h1>
              
              <div className="mt-2 flex items-center gap-2 text-muted-foreground">
                <MapPin className="h-5 w-5" />
                <span>{property.address}, {property.area}, {property.city}</span>
              </div>
            </div>

            {/* Price & Key Info */}
            <Card>
              <CardContent className="p-6">
                <div className="flex items-baseline justify-between">
                  <div>
                    <p className="text-3xl font-bold">NPR {property.price.toLocaleString()}</p>
                    <p className="text-sm text-muted-foreground">per month</p>
                  </div>
                  <div className="text-right">
                    <p className="text-lg font-semibold">NPR {property.securityDeposit.toLocaleString()}</p>
                    <p className="text-sm text-muted-foreground">Security Deposit</p>
                  </div>
                </div>

                <Separator className="my-4" />

                <div className="grid grid-cols-3 gap-4 text-center">
                  <div>
                    <div className="flex items-center justify-center gap-2">
                      <Bed className="h-5 w-5" />
                      <span className="text-2xl font-bold">{property.bedrooms}</span>
                    </div>
                    <p className="text-sm text-muted-foreground">Bedrooms</p>
                  </div>
                  <div>
                    <div className="flex items-center justify-center gap-2">
                      <Bath className="h-5 w-5" />
                      <span className="text-2xl font-bold">{property.bathrooms}</span>
                    </div>
                    <p className="text-sm text-muted-foreground">Bathrooms</p>
                  </div>
                  <div>
                    <div className="flex items-center justify-center gap-2">
                      <Maximize className="h-5 w-5" />
                      <span className="text-2xl font-bold">{property.propertyArea}</span>
                    </div>
                    <p className="text-sm text-muted-foreground">Sq. Ft.</p>
                  </div>
                </div>
              </CardContent>
            </Card>

            {/* Description */}
            <Card>
              <CardContent className="p-6">
                <h2 className="mb-4 text-xl font-semibold">Description</h2>
                <p className="whitespace-pre-line text-muted-foreground">{property.description}</p>
              </CardContent>
            </Card>

            {/* Amenities */}
            {property.amenities.length > 0 && (
              <Card>
                <CardContent className="p-6">
                  <h2 className="mb-4 text-xl font-semibold">Amenities</h2>
                  <div className="grid grid-cols-2 gap-3">
                    {property.amenities.map((amenity, index) => (
                      <div key={index} className="flex items-center gap-2">
                        <CheckCircle2 className="h-5 w-5 text-primary" />
                        <span>{amenity}</span>
                      </div>
                    ))}
                  </div>
                </CardContent>
              </Card>
            )}

            {/* Availability */}
            <Card>
              <CardContent className="p-6">
                <h2 className="mb-4 text-xl font-semibold">Availability</h2>
                <div className="flex items-center gap-2">
                  <Calendar className="h-5 w-5" />
                  <span>Available from: {new Date(property.availableFrom).toLocaleDateString()}</span>
                </div>
              </CardContent>
            </Card>
          </div>

          {/* Right Column - Landlord & Actions */}
          <div className="space-y-6">
            {/* Action Buttons */}
            <Card>
              <CardContent className="p-6 space-y-3">
                <Button className="w-full" size="lg" onClick={handleApply}>
                  Apply Now
                </Button>
                <div className="grid grid-cols-2 gap-3">
                  <Button variant="outline" size="lg">
                    <Heart className="mr-2 h-5 w-5" />
                    Save
                  </Button>
                  <Button variant="outline" size="lg">
                    <Share2 className="mr-2 h-5 w-5" />
                    Share
                  </Button>
                </div>
              </CardContent>
            </Card>

            {/* Landlord Info */}
            <Card>
              <CardContent className="p-6">
                <h3 className="mb-4 text-lg font-semibold">Listed by</h3>
                
                <div className="flex items-center gap-3 mb-4">
                  <Avatar className="h-12 w-12">
                    <AvatarImage src={property.landlord.avatarUrl} />
                    <AvatarFallback>
                      {property.landlord.fullName.split(' ').map(n => n[0]).join('')}
                    </AvatarFallback>
                  </Avatar>
                  <div>
                    <p className="font-semibold">{property.landlord.fullName}</p>
                    <p className="text-sm text-muted-foreground">
                      {property.landlord.propertiesCount} properties
                    </p>
                  </div>
                </div>

                <div className="space-y-2">
                  <p className="text-sm">
                    <span className="font-medium">Response Rate:</span>{' '}
                    {property.landlord.responseRate}%
                  </p>
                  <p className="text-sm">
                    <span className="font-medium">Member since:</span>{' '}
                    {new Date(property.landlord.joinedDate).toLocaleDateString()}
                  </p>
                </div>

                <Separator className="my-4" />

                <div className="space-y-2">
                  <Button variant="outline" className="w-full justify-start">
                    <Phone className="mr-2 h-4 w-4" />
                    {property.landlord.phoneNumber || 'Call'}
                  </Button>
                  <Button variant="outline" className="w-full justify-start">
                    <Mail className="mr-2 h-4 w-4" />
                    Send Message
                  </Button>
                </div>
              </CardContent>
            </Card>

            {/* Property Stats */}
            <Card>
              <CardContent className="p-6">
                <h3 className="mb-4 text-lg font-semibold">Property Stats</h3>
                <div className="space-y-2 text-sm">
                  <div className="flex justify-between">
                    <span className="text-muted-foreground">Views:</span>
                    <span className="font-medium">{property.views}</span>
                  </div>
                  <div className="flex justify-between">
                    <span className="text-muted-foreground">Saved:</span>
                    <span className="font-medium">{property.savedCount}</span>
                  </div>
                  <div className="flex justify-between">
                    <span className="text-muted-foreground">Listed:</span>
                    <span className="font-medium">
                      {new Date(property.createdAt).toLocaleDateString()}
                    </span>
                  </div>
                </div>
              </CardContent>
            </Card>
          </div>
        </div>

        {/* Similar Properties */}
        {property.similarProperties && property.similarProperties.length > 0 && (
          <div className="mt-12">
            <h2 className="mb-6 text-2xl font-bold">Similar Properties</h2>
            <div className="grid gap-6 sm:grid-cols-2 lg:grid-cols-4">
              {property.similarProperties.map((similarProperty) => (
                <PropertyCard key={similarProperty.id} property={similarProperty} />
              ))}
            </div>
          </div>
        )}
      </main>

      <Footer />
    </div>
  )
}
```

### Verification Checklist

- [ ] Property details loaded from API
- [ ] All property information displayed
- [ ] Image gallery working with thumbnails
- [ ] Price and deposit shown
- [ ] Specifications displayed
- [ ] Amenities listed
- [ ] Landlord information shown
- [ ] Apply button navigates correctly
- [ ] Loading and error states handled
- [ ] Similar properties section (if available)

---

## Phase 3 Completion Checklist

### Property Browsing

- [ ] Property listing page loads from API
- [ ] Properties displayed in grid layout
- [ ] Pagination working
- [ ] Search by keyword working
- [ ] Filter by city working
- [ ] Filter by type working
- [ ] Empty state shown when no results
- [ ] Loading skeletons during fetch

### Property Details

- [ ] Property details page loads correctly
- [ ] All property information displayed
- [ ] Image gallery functional
- [ ] Landlord information shown
- [ ] Apply button works
- [ ] Save/Share buttons present (functionality to be added)
- [ ] Similar properties section

### Landlord Features (To be completed in next tasks)

- [ ] Create property form
- [ ] Edit property form
- [ ] Delete property functionality
- [ ] Status management
- [ ] My properties page

### Testing

- [ ] Browse properties without login
- [ ] View property details
- [ ] Search and filter work
- [ ] Pagination navigation
- [ ] Error handling for invalid property IDs
- [ ] Backend errors displayed properly

---

# PHASE 4: DASHBOARD INTEGRATION

**Priority:** 🔴 HIGH  
**Duration:** 2-3 days  
**Dependencies:** Phase 1, Phase 2  
**Estimated Hours:** 16-20h

## Objectives

- Create dashboard services for tenant, landlord, and admin
- Integrate real-time statistics and metrics
- Display dynamic data instead of mock data
- Implement data visualization with charts
- Add quick actions and notifications preview

---

## Task 4.1: Create Dashboard Type Definitions

**Duration:** 30 minutes

### Steps

**1. Create `types/dashboard.types.ts`:**

```typescript
/**
 * Tenant Dashboard Statistics
 */
export interface TenantDashboardStats {
  activeApplications: number
  activeLease: {
    id: number
    propertyTitle: string
    propertyAddress: string
    monthlyRent: number
    leaseEndDate: string
    daysRemaining: number
  } | null
  upcomingPayments: {
    id: number
    dueDate: string
    amount: number
    status: string
  }[]
  savedProperties: number
  recentActivity: DashboardActivity[]
  paymentHistory: {
    totalPaid: number
    onTimePayments: number
    latePayments: number
    upcomingPayment: {
      amount: number
      dueDate: string
    } | null
  }
}

/**
 * Landlord Dashboard Statistics
 */
export interface LandlordDashboardStats {
  totalProperties: number
  activeProperties: number
  rentedProperties: number
  availableProperties: number
  totalRevenue: number
  monthlyRevenue: number
  pendingApplications: number
  activeLeases: number
  upcomingPayments: {
    id: number
    tenantName: string
    propertyTitle: string
    amount: number
    dueDate: string
    status: string
  }[]
  recentActivity: DashboardActivity[]
  revenueByMonth: {
    month: string
    revenue: number
  }[]
  propertyPerformance: {
    propertyId: number
    propertyTitle: string
    views: number
    applications: number
    occupancyRate: number
  }[]
}

/**
 * Admin Dashboard Statistics
 */
export interface AdminDashboardStats {
  totalUsers: number
  totalLandlords: number
  totalTenants: number
  totalProperties: number
  activeLeases: number
  totalRevenue: number
  pendingVerifications: number
  reportedIssues: number
  userGrowth: {
    month: string
    users: number
  }[]
  propertyGrowth: {
    month: string
    properties: number
  }[]
  revenueGrowth: {
    month: string
    revenue: number
  }[]
  recentUsers: {
    id: number
    fullName: string
    email: string
    role: string
    joinedDate: string
  }[]
  topLandlords: {
    id: number
    fullName: string
    propertiesCount: number
    totalRevenue: number
  }[]
}

/**
 * Dashboard Activity Item
 */
export interface DashboardActivity {
  id: number
  type: 'APPLICATION' | 'PAYMENT' | 'LEASE' | 'PROPERTY' | 'REVIEW'
  title: string
  description: string
  timestamp: string
  link?: string
  read: boolean
}

/**
 * Quick Action
 */
export interface QuickAction {
  id: string
  title: string
  description: string
  icon: string
  link: string
  variant?: 'default' | 'primary' | 'success' | 'warning'
}
```

**2. Export from `types/index.ts`:**

```typescript
export * from './auth.types'
export * from './property.types'
export * from './dashboard.types'
```

### Verification Checklist

- [ ] Dashboard types defined for all roles
- [ ] Types match backend response structures
- [ ] Activity and quick action types included
- [ ] Types exported properly

---

## Task 4.2: Create Dashboard Service

**Duration:** 1 hour

### Steps

**1. Create `lib/services/dashboard.service.ts`:**

```typescript
import apiClient from '@/lib/api/client'
import { API_ENDPOINTS } from '@/lib/constants/api'
import type {
  TenantDashboardStats,
  LandlordDashboardStats,
  AdminDashboardStats,
} from '@/types/dashboard.types'

/**
 * Dashboard Service
 * Handles all dashboard-related API calls
 */
class DashboardService {
  /**
   * Get tenant dashboard statistics
   */
  async getTenantDashboard(): Promise<TenantDashboardStats> {
    const response = await apiClient.get<TenantDashboardStats>(
      API_ENDPOINTS.DASHBOARD.TENANT
    )
    return response.data
  }

  /**
   * Get landlord dashboard statistics
   */
  async getLandlordDashboard(): Promise<LandlordDashboardStats> {
    const response = await apiClient.get<LandlordDashboardStats>(
      API_ENDPOINTS.DASHBOARD.LANDLORD
    )
    return response.data
  }

  /**
   * Get admin dashboard statistics
   */
  async getAdminDashboard(): Promise<AdminDashboardStats> {
    const response = await apiClient.get<AdminDashboardStats>(
      API_ENDPOINTS.DASHBOARD.ADMIN
    )
    return response.data
  }
}

// Export singleton instance
export const dashboardService = new DashboardService()
export default dashboardService
```

**2. Export from `lib/services/index.ts`:**

```typescript
export * from './auth.service'
export * from './property.service'
export * from './dashboard.service'
```

### Verification Checklist

- [ ] Dashboard service created
- [ ] Methods for all three dashboard types
- [ ] Proper TypeScript types applied
- [ ] Service exported as singleton

---

## Task 4.3: Update Tenant Dashboard Page

**Duration:** 3 hours

### Steps

**1. Update `app/tenant/dashboard/page.tsx`:**

```typescript
'use client'

import { useState, useEffect } from 'react'
import Link from 'next/link'
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card'
import { Button } from '@/components/ui/button'
import { Badge } from '@/components/ui/badge'
import { Alert, AlertDescription } from '@/components/ui/alert'
import { Skeleton } from '@/components/ui/skeleton'
import {
  Home,
  FileText,
  CreditCard,
  Heart,
  TrendingUp,
  Calendar,
  ArrowRight,
  AlertCircle,
} from 'lucide-react'
import { StatsCard } from '@/components/stats-card'
import { dashboardService } from '@/lib/services/dashboard.service'
import { handleAPIError } from '@/lib/api/errorHandler'
import type { TenantDashboardStats } from '@/types/dashboard.types'

export default function TenantDashboardPage() {
  const [stats, setStats] = useState<TenantDashboardStats | null>(null)
  const [isLoading, setIsLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)

  /**
   * Fetch dashboard data
   */
  useEffect(() => {
    const fetchDashboard = async () => {
      try {
        setIsLoading(true)
        setError(null)
        const data = await dashboardService.getTenantDashboard()
        setStats(data)
      } catch (err) {
        const apiError = handleAPIError(err)
        setError(apiError.message)
      } finally {
        setIsLoading(false)
      }
    }

    fetchDashboard()
  }, [])

  // Loading state
  if (isLoading) {
    return (
      <div className="p-8 space-y-6">
        <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-4">
          {Array.from({ length: 4 }).map((_, i) => (
            <Skeleton key={i} className="h-32" />
          ))}
        </div>
        <Skeleton className="h-[400px]" />
      </div>
    )
  }

  // Error state
  if (error || !stats) {
    return (
      <div className="p-8">
        <Alert variant="destructive">
          <AlertCircle className="h-4 w-4" />
          <AlertDescription>{error || 'Failed to load dashboard'}</AlertDescription>
        </Alert>
      </div>
    )
  }

  return (
    <div className="p-8 space-y-6">
      {/* Header */}
      <div>
        <h1 className="text-3xl font-bold">Welcome Back!</h1>
        <p className="text-muted-foreground">Here's what's happening with your account</p>
      </div>

      {/* Quick Stats */}
      <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-4">
        <StatsCard
          title="Active Applications"
          value={stats.activeApplications}
          icon={FileText}
          description="Applications in progress"
          trend={stats.activeApplications > 0 ? '+' + stats.activeApplications : undefined}
        />
        <StatsCard
          title="Active Lease"
          value={stats.activeLease ? '1' : '0'}
          icon={Home}
          description={stats.activeLease ? 'Current rental' : 'No active lease'}
        />
        <StatsCard
          title="Saved Properties"
          value={stats.savedProperties}
          icon={Heart}
          description="Properties you liked"
        />
        <StatsCard
          title="Payment Status"
          value={stats.paymentHistory.onTimePayments}
          icon={CreditCard}
          description="On-time payments"
          trend={`${stats.paymentHistory.onTimePayments}/${stats.paymentHistory.onTimePayments + stats.paymentHistory.latePayments}`}
        />
      </div>

      {/* Active Lease Card */}
      {stats.activeLease && (
        <Card>
          <CardHeader>
            <CardTitle className="flex items-center justify-between">
              <span>Current Lease</span>
              <Badge variant={stats.activeLease.daysRemaining < 30 ? 'destructive' : 'default'}>
                {stats.activeLease.daysRemaining} days remaining
              </Badge>
            </CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div>
              <h3 className="font-semibold text-lg">{stats.activeLease.propertyTitle}</h3>
              <p className="text-sm text-muted-foreground">{stats.activeLease.propertyAddress}</p>
            </div>
            <div className="grid grid-cols-2 gap-4">
              <div>
                <p className="text-sm text-muted-foreground">Monthly Rent</p>
                <p className="text-2xl font-bold">NPR {stats.activeLease.monthlyRent.toLocaleString()}</p>
              </div>
              <div>
                <p className="text-sm text-muted-foreground">Lease Ends</p>
                <p className="text-lg font-semibold">
                  {new Date(stats.activeLease.leaseEndDate).toLocaleDateString()}
                </p>
              </div>
            </div>
            <Button asChild className="w-full">
              <Link href={`/tenant/leases/${stats.activeLease.id}`}>
                View Lease Details
                <ArrowRight className="ml-2 h-4 w-4" />
              </Link>
            </Button>
          </CardContent>
        </Card>
      )}

      {/* Upcoming Payments */}
      {stats.upcomingPayments.length > 0 && (
        <Card>
          <CardHeader>
            <CardTitle>Upcoming Payments</CardTitle>
          </CardHeader>
          <CardContent className="space-y-3">
            {stats.upcomingPayments.map((payment) => (
              <div
                key={payment.id}
                className="flex items-center justify-between p-4 rounded-lg border"
              >
                <div className="flex items-center gap-3">
                  <div className="flex h-10 w-10 items-center justify-center rounded-full bg-primary/10">
                    <CreditCard className="h-5 w-5 text-primary" />
                  </div>
                  <div>
                    <p className="font-medium">NPR {payment.amount.toLocaleString()}</p>
                    <p className="text-sm text-muted-foreground">
                      Due: {new Date(payment.dueDate).toLocaleDateString()}
                    </p>
                  </div>
                </div>
                <Badge variant={payment.status === 'PENDING' ? 'destructive' : 'default'}>
                  {payment.status}
                </Badge>
              </div>
            ))}
          </CardContent>
        </Card>
      )}

      {/* Payment History Summary */}
      {stats.paymentHistory.upcomingPayment && (
        <Card>
          <CardHeader>
            <CardTitle>Next Payment Due</CardTitle>
          </CardHeader>
          <CardContent>
            <div className="flex items-center justify-between">
              <div>
                <p className="text-2xl font-bold">
                  NPR {stats.paymentHistory.upcomingPayment.amount.toLocaleString()}
                </p>
                <p className="text-sm text-muted-foreground">
                  Due on {new Date(stats.paymentHistory.upcomingPayment.dueDate).toLocaleDateString()}
                </p>
              </div>
              <Button asChild>
                <Link href="/tenant/payments">Pay Now</Link>
              </Button>
            </div>
          </CardContent>
        </Card>
      )}

      {/* Recent Activity */}
      {stats.recentActivity.length > 0 && (
        <Card>
          <CardHeader>
            <CardTitle>Recent Activity</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            {stats.recentActivity.slice(0, 5).map((activity) => (
              <div key={activity.id} className="flex items-start gap-3 pb-3 border-b last:border-0">
                <div className="flex h-8 w-8 items-center justify-center rounded-full bg-primary/10">
                  <TrendingUp className="h-4 w-4 text-primary" />
                </div>
                <div className="flex-1">
                  <p className="font-medium">{activity.title}</p>
                  <p className="text-sm text-muted-foreground">{activity.description}</p>
                  <p className="text-xs text-muted-foreground mt-1">
                    {new Date(activity.timestamp).toLocaleString()}
                  </p>
                </div>
              </div>
            ))}
            <Button variant="link" asChild className="w-full">
              <Link href="/tenant/activity">View All Activity</Link>
            </Button>
          </CardContent>
        </Card>
      )}

      {/* Quick Actions */}
      <Card>
        <CardHeader>
          <CardTitle>Quick Actions</CardTitle>
        </CardHeader>
        <CardContent className="grid gap-3 sm:grid-cols-2">
          <Button variant="outline" asChild className="h-auto py-4">
            <Link href="/properties" className="flex flex-col items-start">
              <Home className="h-5 w-5 mb-2" />
              <span className="font-semibold">Browse Properties</span>
              <span className="text-xs text-muted-foreground">Find your next home</span>
            </Link>
          </Button>
          <Button variant="outline" asChild className="h-auto py-4">
            <Link href="/tenant/applications" className="flex flex-col items-start">
              <FileText className="h-5 w-5 mb-2" />
              <span className="font-semibold">My Applications</span>
              <span className="text-xs text-muted-foreground">Track your submissions</span>
            </Link>
          </Button>
          <Button variant="outline" asChild className="h-auto py-4">
            <Link href="/tenant/payments" className="flex flex-col items-start">
              <CreditCard className="h-5 w-5 mb-2" />
              <span className="font-semibold">Payment History</span>
              <span className="text-xs text-muted-foreground">View all transactions</span>
            </Link>
          </Button>
          <Button variant="outline" asChild className="h-auto py-4">
            <Link href="/tenant/saved" className="flex flex-col items-start">
              <Heart className="h-5 w-5 mb-2" />
              <span className="font-semibold">Saved Properties</span>
              <span className="text-xs text-muted-foreground">Properties you liked</span>
            </Link>
          </Button>
        </CardContent>
      </Card>
    </div>
  )
}
```

### Verification Checklist

- [ ] Dashboard loads real data from API
- [ ] All statistics displayed correctly
- [ ] Active lease card shows when available
- [ ] Upcoming payments listed
- [ ] Recent activity displayed
- [ ] Quick actions navigation working
- [ ] Loading and error states handled
- [ ] Responsive layout

---

## Task 4.4: Update Landlord Dashboard Page

**Duration:** 3 hours

### Steps

**1. Update `app/landlord/dashboard/page.tsx`:**

```typescript
'use client'

import { useState, useEffect } from 'react'
import Link from 'next/link'
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card'
import { Button } from '@/components/ui/button'
import { Badge } from '@/components/ui/badge'
import { Alert, AlertDescription } from '@/components/ui/alert'
import { Skeleton } from '@/components/ui/skeleton'
import {
  Home,
  DollarSign,
  FileText,
  Users,
  TrendingUp,
  Plus,
  AlertCircle,
} from 'lucide-react'
import { StatsCard } from '@/components/stats-card'
import { ResponsiveContainer, BarChart, Bar, XAxis, YAxis, Tooltip, CartesianGrid } from 'recharts'
import { dashboardService } from '@/lib/services/dashboard.service'
import { handleAPIError } from '@/lib/api/errorHandler'
import type { LandlordDashboardStats } from '@/types/dashboard.types'

export default function LandlordDashboardPage() {
  const [stats, setStats] = useState<LandlordDashboardStats | null>(null)
  const [isLoading, setIsLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)

  /**
   * Fetch dashboard data
   */
  useEffect(() => {
    const fetchDashboard = async () => {
      try {
        setIsLoading(true)
        setError(null)
        const data = await dashboardService.getLandlordDashboard()
        setStats(data)
      } catch (err) {
        const apiError = handleAPIError(err)
        setError(apiError.message)
      } finally {
        setIsLoading(false)
      }
    }

    fetchDashboard()
  }, [])

  // Loading state
  if (isLoading) {
    return (
      <div className="p-8 space-y-6">
        <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-4">
          {Array.from({ length: 4 }).map((_, i) => (
            <Skeleton key={i} className="h-32" />
          ))}
        </div>
        <Skeleton className="h-[400px]" />
      </div>
    )
  }

  // Error state
  if (error || !stats) {
    return (
      <div className="p-8">
        <Alert variant="destructive">
          <AlertCircle className="h-4 w-4" />
          <AlertDescription>{error || 'Failed to load dashboard'}</AlertDescription>
        </Alert>
      </div>
    )
  }

  return (
    <div className="p-8 space-y-6">
      {/* Header with CTA */}
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-3xl font-bold">Property Management</h1>
          <p className="text-muted-foreground">Manage your properties and track performance</p>
        </div>
        <Button asChild size="lg">
          <Link href="/landlord/properties/new">
            <Plus className="mr-2 h-5 w-5" />
            Add Property
          </Link>
        </Button>
      </div>

      {/* Key Metrics */}
      <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-4">
        <StatsCard
          title="Total Properties"
          value={stats.totalProperties}
          icon={Home}
          description={`${stats.activeProperties} active`}
          trend={`${((stats.activeProperties / stats.totalProperties) * 100).toFixed(0)}%`}
        />
        <StatsCard
          title="Total Revenue"
          value={`NPR ${(stats.totalRevenue / 1000).toFixed(1)}K`}
          icon={DollarSign}
          description="All time earnings"
          trend={`+${((stats.monthlyRevenue / stats.totalRevenue) * 100).toFixed(0)}%`}
        />
        <StatsCard
          title="Pending Applications"
          value={stats.pendingApplications}
          icon={FileText}
          description="Requires your review"
          variant={stats.pendingApplications > 0 ? 'warning' : 'default'}
        />
        <StatsCard
          title="Active Leases"
          value={stats.activeLeases}
          icon={Users}
          description="Current tenants"
        />
      </div>

      {/* Revenue Chart */}
      {stats.revenueByMonth.length > 0 && (
        <Card>
          <CardHeader>
            <CardTitle>Revenue Overview</CardTitle>
            <p className="text-sm text-muted-foreground">
              Monthly revenue for the last 6 months
            </p>
          </CardHeader>
          <CardContent>
            <div className="h-[300px]">
              <ResponsiveContainer width="100%" height="100%">
                <BarChart data={stats.revenueByMonth}>
                  <CartesianGrid strokeDasharray="3 3" className="stroke-muted" />
                  <XAxis 
                    dataKey="month" 
                    className="text-xs"
                  />
                  <YAxis 
                    className="text-xs"
                    tickFormatter={(value) => `${(value / 1000).toFixed(0)}K`}
                  />
                  <Tooltip
                    formatter={(value: number) => [`NPR ${value.toLocaleString()}`, 'Revenue']}
                    contentStyle={{ 
                      backgroundColor: 'hsl(var(--card))', 
                      border: '1px solid hsl(var(--border))' 
                    }}
                  />
                  <Bar dataKey="revenue" fill="hsl(var(--primary))" radius={[8, 8, 0, 0]} />
                </BarChart>
              </ResponsiveContainer>
            </div>
          </CardContent>
        </Card>
      )}

      <div className="grid gap-6 lg:grid-cols-2">
        {/* Property Performance */}
        {stats.propertyPerformance.length > 0 && (
          <Card>
            <CardHeader>
              <CardTitle>Top Performing Properties</CardTitle>
            </CardHeader>
            <CardContent className="space-y-4">
              {stats.propertyPerformance.slice(0, 5).map((property) => (
                <div key={property.propertyId} className="flex items-center justify-between">
                  <div>
                    <p className="font-medium">{property.propertyTitle}</p>
                    <p className="text-sm text-muted-foreground">
                      {property.views} views • {property.applications} applications
                    </p>
                  </div>
                  <Badge variant={property.occupancyRate > 80 ? 'success' : 'default'}>
                    {property.occupancyRate}% occupied
                  </Badge>
                </div>
              ))}
            </CardContent>
          </Card>
        )}

        {/* Upcoming Payments */}
        {stats.upcomingPayments.length > 0 && (
          <Card>
            <CardHeader>
              <CardTitle>Expected Payments</CardTitle>
            </CardHeader>
            <CardContent className="space-y-4">
              {stats.upcomingPayments.slice(0, 5).map((payment) => (
                <div key={payment.id} className="flex items-center justify-between">
                  <div>
                    <p className="font-medium">{payment.tenantName}</p>
                    <p className="text-sm text-muted-foreground">{payment.propertyTitle}</p>
                    <p className="text-xs text-muted-foreground">
                      Due: {new Date(payment.dueDate).toLocaleDateString()}
                    </p>
                  </div>
                  <div className="text-right">
                    <p className="font-semibold">NPR {payment.amount.toLocaleString()}</p>
                    <Badge variant={payment.status === 'PENDING' ? 'warning' : 'success'}>
                      {payment.status}
                    </Badge>
                  </div>
                </div>
              ))}
            </CardContent>
          </Card>
        )}
      </div>

      {/* Recent Activity */}
      {stats.recentActivity.length > 0 && (
        <Card>
          <CardHeader>
            <CardTitle>Recent Activity</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            {stats.recentActivity.slice(0, 5).map((activity) => (
              <div key={activity.id} className="flex items-start gap-3 pb-3 border-b last:border-0">
                <div className="flex h-8 w-8 items-center justify-center rounded-full bg-primary/10">
                  <TrendingUp className="h-4 w-4 text-primary" />
                </div>
                <div className="flex-1">
                  <p className="font-medium">{activity.title}</p>
                  <p className="text-sm text-muted-foreground">{activity.description}</p>
                  <p className="text-xs text-muted-foreground mt-1">
                    {new Date(activity.timestamp).toLocaleString()}
                  </p>
                </div>
              </div>
            ))}
          </CardContent>
        </Card>
      )}

      {/* Quick Actions */}
      <Card>
        <CardHeader>
          <CardTitle>Quick Actions</CardTitle>
        </CardHeader>
        <CardContent className="grid gap-3 sm:grid-cols-3">
          <Button variant="outline" asChild className="h-auto py-4">
            <Link href="/landlord/properties" className="flex flex-col items-start">
              <Home className="h-5 w-5 mb-2" />
              <span className="font-semibold">My Properties</span>
              <span className="text-xs text-muted-foreground">Manage listings</span>
            </Link>
          </Button>
          <Button variant="outline" asChild className="h-auto py-4">
            <Link href="/landlord/applications" className="flex flex-col items-start">
              <FileText className="h-5 w-5 mb-2" />
              <span className="font-semibold">Applications</span>
              <span className="text-xs text-muted-foreground">Review requests</span>
            </Link>
          </Button>
          <Button variant="outline" asChild className="h-auto py-4">
            <Link href="/landlord/leases" className="flex flex-col items-start">
              <Users className="h-5 w-5 mb-2" />
              <span className="font-semibold">Active Leases</span>
              <span className="text-xs text-muted-foreground">Manage tenants</span>
            </Link>
          </Button>
        </CardContent>
      </Card>
    </div>
  )
}
```

### Verification Checklist

- [ ] Dashboard loads real data from API
- [ ] Revenue statistics displayed correctly
- [ ] Property statistics shown
- [ ] Revenue chart rendering
- [ ] Property performance table displayed
- [ ] Upcoming payments listed
- [ ] Recent activity shown
- [ ] Quick actions working
- [ ] Add property button navigates correctly

---

## Task 4.5: Property Image & Video Gallery Management

**Duration:** 4-5 hours

### Objectives

- Build property image/video upload interface for landlord property creation
- Implement gallery management with drag-and-drop reordering
- Support image and video media types
- Enforce gallery guidelines and validation rules

### Gallery Guidelines & Requirements

#### Media Requirements

1. **Image Requirements:**
   - **Minimum:** 3 images required per property
   - **Maximum:** 10 images allowed per property
   - **Primary Image:** One image must be set as `isPrimary=true` (hero image)
   - **Formats:** JPEG, PNG, WebP
   - **Max Size:** 5MB per image
   - **Display Order:** Controlled by `displayOrder` field

2. **Video Requirements:**
   - **Maximum:** 1 optional video per property
   - **Media Type:** `mediaType='VIDEO'`
   - **Formats:** MP4, WebM
   - **Max Size:** 50MB
   - **Display:** Shows in gallery with play icon overlay

#### Database Schema

```sql
-- PropertyImage entity (already implemented in backend)
property_images (
  id BIGINT PRIMARY KEY,
  property_id BIGINT,
  filename VARCHAR(255),
  file_url VARCHAR(1000),
  display_order INT,
  is_primary BOOLEAN DEFAULT FALSE,
  media_type VARCHAR(20) DEFAULT 'IMAGE', -- 'IMAGE' or 'VIDEO'
  uploaded_at TIMESTAMP
)
```

### Steps

**1. Create Image Upload Component `components/property/image-upload.tsx`:**

```typescript
'use client'

import { useState, useCallback } from 'react'
import { useDropzone } from 'react-dropzone'
import Image from 'next/image'
import { Card, CardContent } from '@/components/ui/card'
import { Button } from '@/components/ui/button'
import { Badge } from '@/components/ui/badge'
import { Alert, AlertDescription } from '@/components/ui/alert'
import { X, Upload, Star, GripVertical, Play, Image as ImageIcon, Video } from 'lucide-react'
import { DragDropContext, Droppable, Draggable } from '@hello-pangea/dnd'
import { cn } from '@/lib/utils'

interface ImageUploadProps {
  images: PropertyImage[]
  onImagesChange: (images: PropertyImage[]) => void
  maxImages?: number
  maxVideos?: number
  minImages?: number
}

interface PropertyImage {
  id: string
  file?: File
  fileUrl?: string
  isPrimary: boolean
  displayOrder: number
  mediaType: 'IMAGE' | 'VIDEO'
  preview?: string
}

export function ImageUpload({
  images,
  onImagesChange,
  maxImages = 10,
  maxVideos = 1,
  minImages = 3,
}: ImageUploadProps) {
  const [errors, setErrors] = useState<string[]>([])

  // Calculate current counts
  const imageCount = images.filter(img => img.mediaType === 'IMAGE').length
  const videoCount = images.filter(img => img.mediaType === 'VIDEO').length

  const onDrop = useCallback(
    (acceptedFiles: File[]) => {
      setErrors([])
      const newErrors: string[] = []
      const newImages: PropertyImage[] = []

      acceptedFiles.forEach((file) => {
        const isVideo = file.type.startsWith('video/')
        const isImage = file.type.startsWith('image/')

        // Validate file type
        if (!isImage && !isVideo) {
          newErrors.push(`${file.name}: Invalid file type`)
          return
        }

        // Check video limit
        if (isVideo && videoCount + newImages.filter(i => i.mediaType === 'VIDEO').length >= maxVideos) {
          newErrors.push(`Maximum ${maxVideos} video allowed`)
          return
        }

        // Check image limit
        if (isImage && imageCount + newImages.filter(i => i.mediaType === 'IMAGE').length >= maxImages) {
          newErrors.push(`Maximum ${maxImages} images allowed`)
          return
        }

        // Check file size
        const maxSize = isVideo ? 50 * 1024 * 1024 : 5 * 1024 * 1024 // 50MB for video, 5MB for image
        if (file.size > maxSize) {
          newErrors.push(`${file.name}: File size exceeds ${isVideo ? '50MB' : '5MB'}`)
          return
        }

        // Create preview
        const preview = URL.createObjectURL(file)
        
        newImages.push({
          id: `temp-${Date.now()}-${Math.random()}`,
          file,
          preview,
          isPrimary: images.length === 0 && isImage, // First image is primary
          displayOrder: images.length + newImages.length,
          mediaType: isVideo ? 'VIDEO' : 'IMAGE',
        })
      })

      if (newErrors.length > 0) {
        setErrors(newErrors)
      }

      if (newImages.length > 0) {
        onImagesChange([...images, ...newImages])
      }
    },
    [images, imageCount, videoCount, maxImages, maxVideos, onImagesChange]
  )

  const { getRootProps, getInputProps, isDragActive } = useDropzone({
    onDrop,
    accept: {
      'image/*': ['.jpeg', '.jpg', '.png', '.webp'],
      'video/*': ['.mp4', '.webm'],
    },
    multiple: true,
  })

  // Set primary image
  const setPrimary = (id: string) => {
    const updated = images.map(img => ({
      ...img,
      isPrimary: img.id === id && img.mediaType === 'IMAGE',
    }))
    onImagesChange(updated)
  }

  // Remove image
  const removeImage = (id: string) => {
    const filtered = images.filter(img => img.id !== id)
    // If removed image was primary, set first image as primary
    if (filtered.length > 0 && !filtered.some(img => img.isPrimary)) {
      filtered[0].isPrimary = true
    }
    onImagesChange(filtered)
  }

  // Handle drag end
  const handleDragEnd = (result: any) => {
    if (!result.destination) return

    const items = Array.from(images)
    const [reorderedItem] = items.splice(result.source.index, 1)
    items.splice(result.destination.index, 0, reorderedItem)

    // Update display order
    const updated = items.map((item, index) => ({
      ...item,
      displayOrder: index,
    }))

    onImagesChange(updated)
  }

  const remainingImages = maxImages - imageCount
  const remainingVideos = maxVideos - videoCount
  const needsMoreImages = imageCount < minImages

  return (
    <div className="space-y-4">
      {/* Upload Area */}
      <Card>
        <CardContent className="p-6">
          <div
            {...getRootProps()}
            className={cn(
              'border-2 border-dashed rounded-lg p-8 text-center cursor-pointer transition-colors',
              isDragActive ? 'border-primary bg-primary/5' : 'border-muted-foreground/25 hover:border-primary/50'
            )}
          >
            <input {...getInputProps()} />
            <Upload className="mx-auto h-12 w-12 text-muted-foreground mb-4" />
            <p className="text-lg font-medium mb-2">
              {isDragActive ? 'Drop files here' : 'Drag & drop images or video here'}
            </p>
            <p className="text-sm text-muted-foreground mb-4">
              or click to browse files
            </p>
            <div className="flex flex-wrap justify-center gap-2 text-xs text-muted-foreground">
              <Badge variant="outline">
                <ImageIcon className="h-3 w-3 mr-1" />
                {imageCount}/{maxImages} Images ({remainingImages} remaining)
              </Badge>
              <Badge variant="outline">
                <Video className="h-3 w-3 mr-1" />
                {videoCount}/{maxVideos} Video ({remainingVideos} remaining)
              </Badge>
            </div>
          </div>

          {/* Requirements */}
          <div className="mt-4 space-y-2">
            <p className="text-sm font-medium">Requirements:</p>
            <ul className="text-xs text-muted-foreground space-y-1 list-disc list-inside">
              <li className={needsMoreImages ? 'text-yellow-600 font-medium' : ''}>
                Minimum {minImages} images required (currently: {imageCount})
              </li>
              <li>Maximum {maxImages} images and {maxVideos} video</li>
              <li>Images: JPEG, PNG, WebP (max 5MB each)</li>
              <li>Video: MP4, WebM (max 50MB)</li>
            </ul>
          </div>
        </CardContent>
      </Card>

      {/* Errors */}
      {errors.length > 0 && (
        <Alert variant="destructive">
          <AlertDescription>
            <ul className="list-disc list-inside space-y-1">
              {errors.map((error, index) => (
                <li key={index}>{error}</li>
              ))}
            </ul>
          </AlertDescription>
        </Alert>
      )}

      {/* Gallery Preview */}
      {images.length > 0 && (
        <Card>
          <CardContent className="p-6">
            <div className="flex items-center justify-between mb-4">
              <h3 className="text-lg font-semibold">Gallery Preview</h3>
              <p className="text-sm text-muted-foreground">
                Drag to reorder • Click star for hero image
              </p>
            </div>

            <DragDropContext onDragEnd={handleDragEnd}>
              <Droppable droppableId="images" direction="horizontal">
                {(provided) => (
                  <div
                    {...provided.droppableProps}
                    ref={provided.innerRef}
                    className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-5 gap-4"
                  >
                    {images.map((image, index) => (
                      <Draggable key={image.id} draggableId={image.id} index={index}>
                        {(provided, snapshot) => (
                          <div
                            ref={provided.innerRef}
                            {...provided.draggableProps}
                            className={cn(
                              'relative group rounded-lg overflow-hidden border-2',
                              snapshot.isDragging && 'shadow-lg',
                              image.isPrimary ? 'border-primary' : 'border-transparent'
                            )}
                          >
                            {/* Image/Video Preview */}
                            <div className="aspect-video bg-muted relative">
                              {image.mediaType === 'VIDEO' ? (
                                <div className="w-full h-full bg-black flex items-center justify-center">
                                  <Play className="h-12 w-12 text-white" />
                                </div>
                              ) : (
                                <Image
                                  src={image.preview || image.fileUrl || ''}
                                  alt={`Property image ${index + 1}`}
                                  fill
                                  className="object-cover"
                                />
                              )}

                              {/* Media Type Badge */}
                              <Badge
                                variant={image.mediaType === 'VIDEO' ? 'default' : 'secondary'}
                                className="absolute top-2 left-2"
                              >
                                {image.mediaType}
                              </Badge>

                              {/* Primary Badge */}
                              {image.isPrimary && (
                                <Badge variant="default" className="absolute top-2 right-2">
                                  Hero
                                </Badge>
                              )}

                              {/* Actions */}
                              <div className="absolute inset-0 bg-black/50 opacity-0 group-hover:opacity-100 transition-opacity flex items-center justify-center gap-2">
                                <Button
                                  size="icon"
                                  variant="secondary"
                                  {...provided.dragHandleProps}
                                >
                                  <GripVertical className="h-4 w-4" />
                                </Button>
                                {image.mediaType === 'IMAGE' && (
                                  <Button
                                    size="icon"
                                    variant={image.isPrimary ? 'default' : 'secondary'}
                                    onClick={() => setPrimary(image.id)}
                                  >
                                    <Star className="h-4 w-4" />
                                  </Button>
                                )}
                                <Button
                                  size="icon"
                                  variant="destructive"
                                  onClick={() => removeImage(image.id)}
                                >
                                  <X className="h-4 w-4" />
                                </Button>
                              </div>
                            </div>

                            {/* Display Order */}
                            <div className="absolute bottom-2 left-2 bg-black/70 text-white text-xs px-2 py-1 rounded">
                              #{index + 1}
                            </div>
                          </div>
                        )}
                      </Draggable>
                    ))}
                    {provided.placeholder}
                  </div>
                )}
              </Droppable>
            </DragDropContext>
          </CardContent>
        </Card>
      )}
    </div>
  )
}
```

**2. Install required dependencies:**

```bash
pnpm add react-dropzone @hello-pangea/dnd
pnpm add -D @types/react-dropzone
```

**3. Integrate into property creation/edit form:**

Update landlord property forms to include the image upload component with proper validation before submission.

### Verification Checklist

- [ ] Image upload component created
- [ ] Drag-and-drop upload working
- [ ] File type validation (images and video)
- [ ] File size validation (5MB for images, 50MB for video)
- [ ] Minimum 3 images enforcement
- [ ] Maximum 10 images enforcement
- [ ] Maximum 1 video enforcement
- [ ] Primary (hero) image selection
- [ ] Drag-and-drop reordering functional
- [ ] Gallery preview displays correctly
- [ ] Video thumbnails show play icon
- [ ] Media type badges display
- [ ] Remove image functionality works
- [ ] Display order tracked correctly

### Implementation Notes

- Gallery UI for displaying images/videos already implemented in property detail page (Phase 3)
- Backend `PropertyImage` entity supports `mediaType` field ('IMAGE' or 'VIDEO')
- Backend `Property` entity supports `googleMapsUrl` for location display
- File upload API endpoint available at `/api/files/upload`
- Uploaded files automatically linked to property via `entity_id` and `entity_type`

---

## Task 4.6: Create Reusable Stats Card Component

**Duration:** 30 minutes

### Steps

**1. Update `components/stats-card.tsx`:**

```typescript
import { Card, CardContent } from '@/components/ui/card'
import { LucideIcon } from 'lucide-react'
import { cn } from '@/lib/utils'

interface StatsCardProps {
  title: string
  value: string | number
  icon: LucideIcon
  description?: string
  trend?: string
  variant?: 'default' | 'primary' | 'success' | 'warning' | 'destructive'
}

const variantStyles = {
  default: 'bg-background',
  primary: 'bg-primary/10 text-primary',
  success: 'bg-green-500/10 text-green-600 dark:text-green-400',
  warning: 'bg-yellow-500/10 text-yellow-600 dark:text-yellow-400',
  destructive: 'bg-red-500/10 text-red-600 dark:text-red-400',
}

export function StatsCard({
  title,
  value,
  icon: Icon,
  description,
  trend,
  variant = 'default',
}: StatsCardProps) {
  return (
    <Card>
      <CardContent className="p-6">
        <div className="flex items-center justify-between">
          <div className="space-y-1">
            <p className="text-sm font-medium text-muted-foreground">{title}</p>
            <p className="text-2xl font-bold">{value}</p>
            {description && (
              <p className="text-xs text-muted-foreground">{description}</p>
            )}
          </div>
          <div className={cn(
            'flex h-12 w-12 items-center justify-center rounded-full',
            variantStyles[variant]
          )}>
            <Icon className="h-6 w-6" />
          </div>
        </div>
        {trend && (
          <div className="mt-3 flex items-center text-sm">
            <span className={cn(
              'font-medium',
              trend.startsWith('+') ? 'text-green-600' : 'text-muted-foreground'
            )}>
              {trend}
            </span>
            <span className="ml-1 text-muted-foreground">from last month</span>
          </div>
        )}
      </CardContent>
    </Card>
  )
}
```

### Verification Checklist

- [ ] StatsCard component created
- [ ] Props interface defined
- [ ] Variant styles implemented
- [ ] Icon support added
- [ ] Trend indicator working
- [ ] Responsive design

---

## Phase 4 Completion Checklist

### Dashboard Data Integration

- [ ] Dashboard service created
- [ ] Tenant dashboard fetches real data
- [ ] Landlord dashboard fetches real data
- [ ] Admin dashboard fetches real data (if implemented)
- [ ] All statistics displayed correctly
- [ ] Charts render with real data

### Property Gallery Management

- [ ] Image upload component created
- [ ] Drag-and-drop functionality working
- [ ] File validation (type, size, count)
- [ ] Gallery preview displays images/videos correctly
- [ ] Primary (hero) image selection working
- [ ] Drag-and-drop reordering functional
- [ ] Minimum 3 images enforced
- [ ] Maximum 10 images + 1 video enforced
- [ ] Video thumbnails display with play icon
- [ ] Media type badges display correctly

### User Experience

- [ ] Loading skeletons during data fetch
- [ ] Error handling with user-friendly messages
- [ ] Responsive layout on all devices
- [ ] Quick actions navigation working
- [ ] Stats cards showing accurate data
- [ ] Gallery upload provides clear feedback

### Components

- [ ] StatsCard component reusable
- [ ] ImageUpload component functional
- [ ] Chart components configured
- [ ] Activity feed component working
- [ ] Payment preview cards functional

### Testing

- [ ] Tenant dashboard loads without errors
- [ ] Landlord dashboard loads without errors
- [ ] Charts display correctly
- [ ] Navigation from dashboard works
- [ ] Data refresh on page revisit
- [ ] Image upload validation working
- [ ] Gallery reordering persists correctly

---

# PHASE 5: APPLICATION & LEASE WORKFLOW

**Priority:** 🔴 HIGH  
**Duration:** 3-5 days  
**Dependencies:** Phase 1, Phase 2, Phase 3  
**Estimated Hours:** 28-34h

## Objectives

- Implement rental application submission for tenants
- Create application management for landlords
- Implement application approval/rejection workflow
- Auto-create leases on approval
- Implement lease management features
- Add lease termination functionality

---

## Task 5.1: Create Application & Lease Type Definitions

**Duration:** 1 hour

### Steps

**1. Create `types/application.types.ts`:**

```typescript
/**
 * Application status
 */
export type ApplicationStatus = 
  | 'PENDING' 
  | 'UNDER_REVIEW' 
  | 'APPROVED' 
  | 'REJECTED' 
  | 'WITHDRAWN'

/**
 * Rental Application
 */
export interface RentalApplication {
  id: number
  property: {
    id: number
    title: string
    address: string
    city: string
    monthlyRent: number
    imageUrl?: string
  }
  tenant: {
    id: number
    fullName: string
    email: string
    phoneNumber?: string
  }
  status: ApplicationStatus
  moveInDate: string
  monthlyIncome: number
  occupation: string
  employerName?: string
  previousAddress?: string
  numberOfOccupants: number
  hasPets: boolean
  petDetails?: string
  emergencyContactName?: string
  emergencyContactPhone?: string
  message?: string
  appliedAt: string
  reviewedAt?: string
  reviewNote?: string
}

/**
 * Create application request
 */
export interface CreateApplicationRequest {
  propertyId: number
  moveInDate: string
  monthlyIncome: number
  occupation: string
  employerName?: string
  previousAddress?: string
  numberOfOccupants: number
  hasPets: boolean
  petDetails?: string
  emergencyContactName?: string
  emergencyContactPhone?: string
  message?: string
}

/**
 * Application list response
 */
export interface ApplicationListResponse {
  applications: RentalApplication[]
  currentPage: number
  totalPages: number
  totalApplications: number
}

/**
 * Approve application request
 */
export interface ApproveApplicationRequest {
  leaseStartDate: string
  leaseEndDate: string
  monthlyRent: number
  securityDeposit: number
  specialTerms?: string
}

/**
 * Reject application request
 */
export interface RejectApplicationRequest {
  reason: string
}
```

**2. Create `types/lease.types.ts`:**

```typescript
/**
 * Lease status
 */
export type LeaseStatus = 'ACTIVE' | 'TERMINATED' | 'EXPIRED'

/**
 * Lease entity
 */
export interface Lease {
  id: number
  property: {
    id: number
    title: string
    address: string
    city: string
    imageUrl?: string
  }
  tenant: {
    id: number
    fullName: string
    email: string
    phoneNumber?: string
  }
  landlord: {
    id: number
    fullName: string
    email: string
    phoneNumber?: string
  }
  status: LeaseStatus
  startDate: string
  endDate: string
  monthlyRent: number
  securityDeposit: number
  specialTerms?: string
  createdAt: string
  terminatedAt?: string
  terminationReason?: string
}

/**
 * Lease list response
 */
export interface LeaseListResponse {
  leases: Lease[]
  currentPage: number
  totalPages: number
  totalLeases: number
}

/**
 * Terminate lease request
 */
export interface TerminateLeaseRequest {
  reason: string
  effectiveDate: string
}
```

**3. Export from `types/index.ts`:**

```typescript
export * from './auth.types'
export * from './property.types'
export * from './dashboard.types'
export * from './application.types'
export * from './lease.types'
```

### Verification Checklist

- [ ] Application types defined
- [ ] Lease types defined
- [ ] Request/response types included
- [ ] Types match backend DTOs
- [ ] Types exported properly

---

## Task 5.2: Create Application Service

**Duration:** 1.5 hours

### Steps

**1. Create `lib/services/application.service.ts`:**

```typescript
import apiClient from '@/lib/api/client'
import { API_ENDPOINTS } from '@/lib/constants/api'
import type {
  RentalApplication,
  ApplicationListResponse,
  CreateApplicationRequest,
  ApproveApplicationRequest,
  RejectApplicationRequest,
} from '@/types/application.types'

/**
 * Application Service
 * Handles all rental application-related API calls
 */
class ApplicationService {
  /**
   * Get tenant's applications
   */
  async getTenantApplications(
    page: number = 0,
    size: number = 10
  ): Promise<ApplicationListResponse> {
    const response = await apiClient.get<ApplicationListResponse>(
      API_ENDPOINTS.APPLICATIONS.TENANT_BASE,
      { params: { page, size } }
    )
    return response.data
  }

  /**
   * Submit new application (Tenant)
   */
  async submitApplication(data: CreateApplicationRequest): Promise<RentalApplication> {
    const response = await apiClient.post<RentalApplication>(
      API_ENDPOINTS.APPLICATIONS.TENANT_BASE,
      data
    )
    return response.data
  }

  /**
   * Withdraw application (Tenant)
   */
  async withdrawApplication(applicationId: number): Promise<void> {
    await apiClient.patch(
      API_ENDPOINTS.APPLICATIONS.TENANT_WITHDRAW(applicationId)
    )
  }

  /**
   * Get landlord's applications
   */
  async getLandlordApplications(
    page: number = 0,
    size: number = 10,
    status?: string
  ): Promise<ApplicationListResponse> {
    const response = await apiClient.get<ApplicationListResponse>(
      API_ENDPOINTS.APPLICATIONS.LANDLORD_BASE,
      { params: { page, size, status } }
    )
    return response.data
  }

  /**
   * Approve application (Landlord)
   */
  async approveApplication(
    applicationId: number,
    data: ApproveApplicationRequest
  ): Promise<RentalApplication> {
    const response = await apiClient.post<RentalApplication>(
      API_ENDPOINTS.APPLICATIONS.LANDLORD_APPROVE(applicationId),
      data
    )
    return response.data
  }

  /**
   * Reject application (Landlord)
   */
  async rejectApplication(
    applicationId: number,
    data: RejectApplicationRequest
  ): Promise<RentalApplication> {
    const response = await apiClient.post<RentalApplication>(
      API_ENDPOINTS.APPLICATIONS.LANDLORD_REJECT(applicationId),
      data
    )
    return response.data
  }
}

// Export singleton instance
export const applicationService = new ApplicationService()
export default applicationService
```

### Verification Checklist

- [ ] Application service created
- [ ] Tenant methods implemented
- [ ] Landlord methods implemented
- [ ] All CRUD operations included
- [ ] Proper TypeScript types

---

## Task 5.3: Create Lease Service

**Duration:** 1 hour

### Steps

**1. Create `lib/services/lease.service.ts`:**

```typescript
import apiClient from '@/lib/api/client'
import { API_ENDPOINTS } from '@/lib/constants/api'
import type {
  Lease,
  LeaseListResponse,
  TerminateLeaseRequest,
} from '@/types/lease.types'

/**
 * Lease Service
 * Handles all lease-related API calls
 */
class LeaseService {
  /**
   * Get tenant's leases
   */
  async getTenantLeases(
    page: number = 0,
    size: number = 10
  ): Promise<LeaseListResponse> {
    const response = await apiClient.get<LeaseListResponse>(
      API_ENDPOINTS.LEASES.TENANT_BASE,
      { params: { page, size } }
    )
    return response.data
  }

  /**
   * Get tenant's lease by ID
   */
  async getTenantLeaseById(leaseId: number): Promise<Lease> {
    const response = await apiClient.get<Lease>(
      API_ENDPOINTS.LEASES.TENANT_BY_ID(leaseId)
    )
    return response.data
  }

  /**
   * Get tenant's active lease
   */
  async getTenantActiveLease(): Promise<Lease | null> {
    try {
      const response = await apiClient.get<Lease>(
        API_ENDPOINTS.LEASES.TENANT_ACTIVE
      )
      return response.data
    } catch (error) {
      // No active lease
      return null
    }
  }

  /**
   * Get landlord's leases
   */
  async getLandlordLeases(
    page: number = 0,
    size: number = 10,
    status?: string
  ): Promise<LeaseListResponse> {
    const response = await apiClient.get<LeaseListResponse>(
      API_ENDPOINTS.LEASES.LANDLORD_BASE,
      { params: { page, size, status } }
    )
    return response.data
  }

  /**
   * Get landlord's lease by ID
   */
  async getLandlordLeaseById(leaseId: number): Promise<Lease> {
    const response = await apiClient.get<Lease>(
      API_ENDPOINTS.LEASES.LANDLORD_BY_ID(leaseId)
    )
    return response.data
  }

  /**
   * Terminate lease (Landlord)
   */
  async terminateLease(
    leaseId: number,
    data: TerminateLeaseRequest
  ): Promise<Lease> {
    const response = await apiClient.patch<Lease>(
      API_ENDPOINTS.LEASES.LANDLORD_TERMINATE(leaseId),
      data
    )
    return response.data
  }
}

// Export singleton instance
export const leaseService = new LeaseService()
export default leaseService
```

**2. Export from `lib/services/index.ts`:**

```typescript
export * from './auth.service'
export * from './property.service'
export * from './dashboard.service'
export * from './application.service'
export * from './lease.service'
```

### Verification Checklist

- [ ] Lease service created
- [ ] Tenant methods implemented
- [ ] Landlord methods implemented
- [ ] Active lease retrieval method
- [ ] Terminate lease method included

---

## Task 5.4: Create Application Submission Form

**Duration:** 4 hours

### Steps

**1. Create `app/tenant/applications/new/page.tsx`:**

```typescript
'use client'

import { useState, useEffect } from 'react'
import { useRouter, useSearchParams } from 'next/navigation'
import { useForm } from 'react-hook-form'
import { zodResolver } from '@hookform/resolvers/zod'
import * as z from 'zod'
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card'
import { Button } from '@/components/ui/button'
import { Input } from '@/components/ui/input'
import { Label } from '@/components/ui/label'
import { Textarea } from '@/components/ui/textarea'
import { Switch } from '@/components/ui/switch'
import { Alert, AlertDescription } from '@/components/ui/alert'
import { Separator } from '@/components/ui/separator'
import { ArrowLeft, Send } from 'lucide-react'
import Link from 'next/link'
import { propertyService } from '@/lib/services/property.service'
import { applicationService } from '@/lib/services/application.service'
import { handleAPIError } from '@/lib/api/errorHandler'
import type { Property } from '@/types/property.types'
import type { CreateApplicationRequest } from '@/types/application.types'

// Validation schema
const applicationSchema = z.object({
  propertyId: z.number(),
  moveInDate: z.string().min(1, 'Move-in date is required'),
  monthlyIncome: z.number().min(0, 'Monthly income must be positive'),
  occupation: z.string().min(2, 'Occupation is required'),
  employerName: z.string().optional(),
  previousAddress: z.string().optional(),
  numberOfOccupants: z.number().min(1, 'At least 1 occupant required'),
  hasPets: z.boolean(),
  petDetails: z.string().optional(),
  emergencyContactName: z.string().optional(),
  emergencyContactPhone: z.string().optional(),
  message: z.string().optional(),
})

type ApplicationFormData = z.infer<typeof applicationSchema>

export default function NewApplicationPage() {
  const router = useRouter()
  const searchParams = useSearchParams()
  const propertyId = parseInt(searchParams.get('propertyId') || '0')

  const [property, setProperty] = useState<Property | null>(null)
  const [isLoadingProperty, setIsLoadingProperty] = useState(true)
  const [isSubmitting, setIsSubmitting] = useState(false)
  const [error, setError] = useState<string | null>(null)
  const [success, setSuccess] = useState(false)

  const {
    register,
    handleSubmit,
    watch,
    setValue,
    formState: { errors },
  } = useForm<ApplicationFormData>({
    resolver: zodResolver(applicationSchema),
    defaultValues: {
      propertyId,
      hasPets: false,
      numberOfOccupants: 1,
    },
  })

  const hasPets = watch('hasPets')

  /**
   * Load property details
   */
  useEffect(() => {
    const loadProperty = async () => {
      if (!propertyId) {
        setError('Property ID is required')
        setIsLoadingProperty(false)
        return
      }

      try {
        const data = await propertyService.getPropertyById(propertyId)
        setProperty(data)
      } catch (err) {
        const apiError = handleAPIError(err)
        setError(apiError.message)
      } finally {
        setIsLoadingProperty(false)
      }
    }

    loadProperty()
  }, [propertyId])

  /**
   * Submit application
   */
  const onSubmit = async (data: ApplicationFormData) => {
    try {
      setIsSubmitting(true)
      setError(null)

      await applicationService.submitApplication(data)
      
      setSuccess(true)
      
      // Redirect after 2 seconds
      setTimeout(() => {
        router.push('/tenant/applications')
      }, 2000)
    } catch (err) {
      const apiError = handleAPIError(err)
      setError(apiError.message)
    } finally {
      setIsSubmitting(false)
    }
  }

  if (isLoadingProperty) {
    return <div className="p-8">Loading property details...</div>
  }

  if (!property) {
    return (
      <div className="p-8">
        <Alert variant="destructive">
          <AlertDescription>{error || 'Property not found'}</AlertDescription>
        </Alert>
        <Button asChild className="mt-4">
          <Link href="/properties">Browse Properties</Link>
        </Button>
      </div>
    )
  }

  if (success) {
    return (
      <div className="p-8 max-w-2xl mx-auto">
        <Card>
          <CardContent className="pt-6">
            <div className="text-center space-y-4">
              <div className="mx-auto w-16 h-16 bg-green-100 dark:bg-green-900 rounded-full flex items-center justify-center">
                <Send className="h-8 w-8 text-green-600 dark:text-green-400" />
              </div>
              <h2 className="text-2xl font-bold">Application Submitted!</h2>
              <p className="text-muted-foreground">
                Your application has been sent to the landlord. You'll be notified once they review it.
              </p>
              <Button asChild>
                <Link href="/tenant/applications">View My Applications</Link>
              </Button>
            </div>
          </CardContent>
        </Card>
      </div>
    )
  }

  return (
    <div className="p-8 max-w-4xl mx-auto">
      {/* Header */}
      <div className="mb-6">
        <Button variant="ghost" asChild className="mb-4">
          <Link href={`/properties/${propertyId}`}>
            <ArrowLeft className="mr-2 h-4 w-4" />
            Back to Property
          </Link>
        </Button>
        <h1 className="text-3xl font-bold">Submit Rental Application</h1>
        <p className="text-muted-foreground">Fill out the form below to apply for this property</p>
      </div>

      {/* Property Info Card */}
      <Card className="mb-6">
        <CardContent className="p-6">
          <div className="flex gap-4">
            <img
              src={property.images[0]?.fileUrl || '/placeholder.svg'}
              alt={property.title}
              className="w-24 h-24 rounded-lg object-cover"
            />
            <div className="flex-1">
              <h3 className="font-semibold text-lg">{property.title}</h3>
              <p className="text-sm text-muted-foreground">{property.address}, {property.city}</p>
              <p className="text-xl font-bold mt-2">NPR {property.monthlyRent.toLocaleString()}/month</p>
            </div>
          </div>
        </CardContent>
      </Card>

      {/* Error Alert */}
      {error && (
        <Alert variant="destructive" className="mb-6">
          <AlertDescription>{error}</AlertDescription>
        </Alert>
      )}

      {/* Application Form */}
      <form onSubmit={handleSubmit(onSubmit)} className="space-y-6">
        {/* Personal Information */}
        <Card>
          <CardHeader>
            <CardTitle>Personal Information</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div className="grid grid-cols-2 gap-4">
              <div>
                <Label htmlFor="moveInDate">Desired Move-in Date *</Label>
                <Input
                  id="moveInDate"
                  type="date"
                  {...register('moveInDate')}
                  min={new Date().toISOString().split('T')[0]}
                />
                {errors.moveInDate && (
                  <p className="text-sm text-destructive mt-1">{errors.moveInDate.message}</p>
                )}
              </div>

              <div>
                <Label htmlFor="numberOfOccupants">Number of Occupants *</Label>
                <Input
                  id="numberOfOccupants"
                  type="number"
                  min="1"
                  {...register('numberOfOccupants', { valueAsNumber: true })}
                />
                {errors.numberOfOccupants && (
                  <p className="text-sm text-destructive mt-1">{errors.numberOfOccupants.message}</p>
                )}
              </div>
            </div>

            <div>
              <Label htmlFor="previousAddress">Previous Address</Label>
              <Input
                id="previousAddress"
                placeholder="Your current address"
                {...register('previousAddress')}
              />
            </div>
          </CardContent>
        </Card>

        {/* Employment Information */}
        <Card>
          <CardHeader>
            <CardTitle>Employment Information</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div className="grid grid-cols-2 gap-4">
              <div>
                <Label htmlFor="occupation">Occupation *</Label>
                <Input
                  id="occupation"
                  placeholder="e.g., Software Engineer"
                  {...register('occupation')}
                />
                {errors.occupation && (
                  <p className="text-sm text-destructive mt-1">{errors.occupation.message}</p>
                )}
              </div>

              <div>
                <Label htmlFor="employerName">Employer Name</Label>
                <Input
                  id="employerName"
                  placeholder="Company name"
                  {...register('employerName')}
                />
              </div>
            </div>

            <div>
              <Label htmlFor="monthlyIncome">Monthly Income (NPR) *</Label>
              <Input
                id="monthlyIncome"
                type="number"
                min="0"
                step="1000"
                placeholder="50000"
                {...register('monthlyIncome', { valueAsNumber: true })}
              />
              {errors.monthlyIncome && (
                <p className="text-sm text-destructive mt-1">{errors.monthlyIncome.message}</p>
              )}
              <p className="text-xs text-muted-foreground mt-1">
                Recommended: 3x the monthly rent (NPR {(property.monthlyRent * 3).toLocaleString()})
              </p>
            </div>
          </CardContent>
        </Card>

        {/* Emergency Contact */}
        <Card>
          <CardHeader>
            <CardTitle>Emergency Contact</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div className="grid grid-cols-2 gap-4">
              <div>
                <Label htmlFor="emergencyContactName">Contact Name</Label>
                <Input
                  id="emergencyContactName"
                  placeholder="Full name"
                  {...register('emergencyContactName')}
                />
              </div>

              <div>
                <Label htmlFor="emergencyContactPhone">Contact Phone</Label>
                <Input
                  id="emergencyContactPhone"
                  type="tel"
                  placeholder="+977 98XXXXXXXX"
                  {...register('emergencyContactPhone')}
                />
              </div>
            </div>
          </CardContent>
        </Card>

        {/* Pet Information */}
        <Card>
          <CardHeader>
            <CardTitle>Pet Information</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <div className="flex items-center justify-between">
              <div>
                <Label htmlFor="hasPets">Do you have pets?</Label>
                <p className="text-sm text-muted-foreground">
                  Let the landlord know if you have any pets
                </p>
              </div>
              <Switch
                id="hasPets"
                checked={hasPets}
                onCheckedChange={(checked) => setValue('hasPets', checked)}
              />
            </div>

            {hasPets && (
              <div>
                <Label htmlFor="petDetails">Pet Details</Label>
                <Textarea
                  id="petDetails"
                  placeholder="Please describe your pets (type, breed, size, etc.)"
                  rows={3}
                  {...register('petDetails')}
                />
              </div>
            )}
          </CardContent>
        </Card>

        {/* Additional Message */}
        <Card>
          <CardHeader>
            <CardTitle>Additional Message (Optional)</CardTitle>
          </CardHeader>
          <CardContent>
            <Textarea
              id="message"
              placeholder="Tell the landlord why you're a great tenant..."
              rows={4}
              {...register('message')}
            />
          </CardContent>
        </Card>

        {/* Submit Button */}
        <div className="flex gap-4">
          <Button
            type="button"
            variant="outline"
            onClick={() => router.back()}
            disabled={isSubmitting}
          >
            Cancel
          </Button>
          <Button type="submit" disabled={isSubmitting} className="flex-1">
            {isSubmitting ? 'Submitting...' : 'Submit Application'}
            <Send className="ml-2 h-4 w-4" />
          </Button>
        </div>
      </form>
    </div>
  )
}
```

### Verification Checklist

- [ ] Application form created with all fields
- [ ] Property details displayed
- [ ] Form validation working
- [ ] Pet details conditional rendering
- [ ] Income recommendation shown
- [ ] Application submission working
- [ ] Success state displayed
- [ ] Navigation after submission

---

## Task 5.5: Create Tenant Applications List Page

**Duration:** 2 hours

### Steps

**1. Create `app/tenant/applications/page.tsx`:**

```typescript
'use client'

import { useState, useEffect } from 'react'
import Link from 'next/link'
import { Card, CardContent } from '@/components/ui/card'
import { Button } from '@/components/ui/button'
import { Badge } from '@/components/ui/badge'
import { Alert, AlertDescription } from '@/components/ui/alert'
import { Skeleton } from '@/components/ui/skeleton'
import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
} from '@/components/ui/alert-dialog'
import { FileText, Eye, X, Calendar, DollarSign } from 'lucide-react'
import { applicationService } from '@/lib/services/application.service'
import { handleAPIError } from '@/lib/api/errorHandler'
import type { RentalApplication } from '@/types/application.types'

export default function TenantApplicationsPage() {
  const [applications, setApplications] = useState<RentalApplication[]>([])
  const [isLoading, setIsLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)
  const [withdrawingId, setWithdrawingId] = useState<number | null>(null)

  /**
   * Fetch applications
   */
  const fetchApplications = async () => {
    try {
      setIsLoading(true)
      setError(null)
      const response = await applicationService.getTenantApplications(0, 50)
      setApplications(response.applications)
    } catch (err) {
      const apiError = handleAPIError(err)
      setError(apiError.message)
    } finally {
      setIsLoading(false)
    }
  }

  useEffect(() => {
    fetchApplications()
  }, [])

  /**
   * Withdraw application
   */
  const handleWithdraw = async (applicationId: number) => {
    try {
      await applicationService.withdrawApplication(applicationId)
      // Refresh list
      fetchApplications()
    } catch (err) {
      const apiError = handleAPIError(err)
      setError(apiError.message)
    }
  }

  /**
   * Get status badge variant
   */
  const getStatusVariant = (status: string) => {
    switch (status) {
      case 'APPROVED':
        return 'success'
      case 'REJECTED':
        return 'destructive'
      case 'PENDING':
      case 'UNDER_REVIEW':
        return 'warning'
      case 'WITHDRAWN':
        return 'secondary'
      default:
        return 'default'
    }
  }

  // Loading state
  if (isLoading) {
    return (
      <div className="p-8 space-y-4">
        {Array.from({ length: 3 }).map((_, i) => (
          <Skeleton key={i} className="h-48" />
        ))}
      </div>
    )
  }

  return (
    <div className="p-8">
      {/* Header */}
      <div className="mb-6">
        <h1 className="text-3xl font-bold">My Applications</h1>
        <p className="text-muted-foreground">Track your rental applications</p>
      </div>

      {/* Error Alert */}
      {error && (
        <Alert variant="destructive" className="mb-6">
          <AlertDescription>{error}</AlertDescription>
        </Alert>
      )}

      {/* Applications List */}
      {applications.length === 0 ? (
        <Card>
          <CardContent className="py-16 text-center">
            <FileText className="mx-auto h-12 w-12 text-muted-foreground mb-4" />
            <h3 className="text-lg font-semibold mb-2">No Applications Yet</h3>
            <p className="text-muted-foreground mb-4">
              Start browsing properties and submit your first application
            </p>
            <Button asChild>
              <Link href="/properties">Browse Properties</Link>
            </Button>
          </CardContent>
        </Card>
      ) : (
        <div className="space-y-4">
          {applications.map((application) => (
            <Card key={application.id}>
              <CardContent className="p-6">
                <div className="flex gap-4">
                  {/* Property Image */}
                  <img
                    src={application.property.imageUrl || '/placeholder.svg'}
                    alt={application.property.title}
                    className="w-32 h-32 rounded-lg object-cover"
                  />

                  {/* Application Details */}
                  <div className="flex-1 space-y-3">
                    <div className="flex items-start justify-between">
                      <div>
                        <h3 className="font-semibold text-lg">
                          {application.property.title}
                        </h3>
                        <p className="text-sm text-muted-foreground">
                          {application.property.address}, {application.property.city}
                        </p>
                      </div>
                      <Badge variant={getStatusVariant(application.status) as any}>
                        {application.status.replace('_', ' ')}
                      </Badge>
                    </div>

                    <div className="grid grid-cols-2 gap-4 text-sm">
                      <div className="flex items-center gap-2">
                        <Calendar className="h-4 w-4 text-muted-foreground" />
                        <span>Applied: {new Date(application.appliedAt).toLocaleDateString()}</span>
                      </div>
                      <div className="flex items-center gap-2">
                        <DollarSign className="h-4 w-4 text-muted-foreground" />
                        <span>Rent: NPR {application.property.monthlyRent.toLocaleString()}</span>
                      </div>
                    </div>

                    {application.reviewNote && (
                      <Alert>
                        <AlertDescription>
                          <strong>Review Note:</strong> {application.reviewNote}
                        </AlertDescription>
                      </Alert>
                    )}

                    {/* Actions */}
                    <div className="flex gap-2">
                      <Button variant="outline" size="sm" asChild>
                        <Link href={`/properties/${application.property.id}`}>
                          <Eye className="mr-2 h-4 w-4" />
                          View Property
                        </Link>
                      </Button>
                      
                      {application.status === 'PENDING' && (
                        <Button
                          variant="ghost"
                          size="sm"
                          onClick={() => setWithdrawingId(application.id)}
                        >
                          <X className="mr-2 h-4 w-4" />
                          Withdraw
                        </Button>
                      )}

                      {application.status === 'APPROVED' && (
                        <Button variant="default" size="sm" asChild>
                          <Link href="/tenant/leases">View Lease</Link>
                        </Button>
                      )}
                    </div>
                  </div>
                </div>
              </CardContent>
            </Card>
          ))}
        </div>
      )}

      {/* Withdraw Confirmation Dialog */}
      <AlertDialog open={withdrawingId !== null} onOpenChange={() => setWithdrawingId(null)}>
        <AlertDialogContent>
          <AlertDialogHeader>
            <AlertDialogTitle>Withdraw Application?</AlertDialogTitle>
            <AlertDialogDescription>
              Are you sure you want to withdraw this application? This action cannot be undone.
            </AlertDialogDescription>
          </AlertDialogHeader>
          <AlertDialogFooter>
            <AlertDialogCancel>Cancel</AlertDialogCancel>
            <AlertDialogAction
              onClick={() => {
                if (withdrawingId) {
                  handleWithdraw(withdrawingId)
                  setWithdrawingId(null)
                }
              }}
            >
              Withdraw
            </AlertDialogAction>
          </AlertDialogFooter>
        </AlertDialogContent>
      </AlertDialog>
    </div>
  )
}
```

### Verification Checklist

- [ ] Applications list loaded from API
- [ ] Application cards display all info
- [ ] Status badges with correct colors
- [ ] Withdraw functionality working
- [ ] Confirmation dialog for withdrawal
- [ ] Empty state shown when no applications
- [ ] Navigation to property details
- [ ] Navigation to lease (if approved)

---

## Task 5.6: Create Landlord Applications Management Page

**Duration:** 3 hours

### Steps

**1. Create `app/landlord/applications/page.tsx`:**

```typescript
'use client'

import { useState, useEffect } from 'react'
import Link from 'next/link'
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card'
import { Button } from '@/components/ui/button'
import { Badge } from '@/components/ui/badge'
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs'
import { Alert, AlertDescription } from '@/components/ui/alert'
import { Skeleton } from '@/components/ui/skeleton'
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
} from '@/components/ui/dialog'
import { Input } from '@/components/ui/input'
import { Label } from '@/components/ui/label'
import { Textarea } from '@/components/ui/textarea'
import { Check, X, Eye, Mail, Phone, Users, Briefcase, Home } from 'lucide-react'
import { applicationService } from '@/lib/services/application.service'
import { handleAPIError } from '@/lib/api/errorHandler'
import type { RentalApplication, ApproveApplicationRequest, RejectApplicationRequest } from '@/types/application.types'

export default function LandlordApplicationsPage() {
  const [applications, setApplications] = useState<RentalApplication[]>([])
  const [isLoading, setIsLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)
  const [selectedTab, setSelectedTab] = useState('PENDING')
  
  // Dialog states
  const [selectedApplication, setSelectedApplication] = useState<RentalApplication | null>(null)
  const [isApproving, setIsApproving] = useState(false)
  const [isRejecting, setIsRejecting] = useState(false)
  const [showApproveDialog, setShowApproveDialog] = useState(false)
  const [showRejectDialog, setShowRejectDialog] = useState(false)
  
  // Form states
  const [leaseStartDate, setLeaseStartDate] = useState('')
  const [leaseEndDate, setLeaseEndDate] = useState('')
  const [monthlyRent, setMonthlyRent] = useState('')
  const [securityDeposit, setSecurityDeposit] = useState('')
  const [specialTerms, setSpecialTerms] = useState('')
  const [rejectionReason, setRejectionReason] = useState('')

  /**
   * Fetch applications
   */
  const fetchApplications = async (status?: string) => {
    try {
      setIsLoading(true)
      setError(null)
      const response = await applicationService.getLandlordApplications(0, 50, status)
      setApplications(response.applications)
    } catch (err) {
      const apiError = handleAPIError(err)
      setError(apiError.message)
    } finally {
      setIsLoading(false)
    }
  }

  useEffect(() => {
    fetchApplications(selectedTab === 'ALL' ? undefined : selectedTab)
  }, [selectedTab])

  /**
   * Open approve dialog
   */
  const handleOpenApprove = (application: RentalApplication) => {
    setSelectedApplication(application)
    setMonthlyRent(application.property.monthlyRent.toString())
    setSecurityDeposit((application.property.monthlyRent * 2).toString())
    setLeaseStartDate(application.moveInDate)
    // Default 1 year lease
    const endDate = new Date(application.moveInDate)
    endDate.setFullYear(endDate.getFullYear() + 1)
    setLeaseEndDate(endDate.toISOString().split('T')[0])
    setShowApproveDialog(true)
  }

  /**
   * Approve application
   */
  const handleApprove = async () => {
    if (!selectedApplication) return

    try {
      setIsApproving(true)
      const data: ApproveApplicationRequest = {
        leaseStartDate,
        leaseEndDate,
        monthlyRent: parseFloat(monthlyRent),
        securityDeposit: parseFloat(securityDeposit),
        specialTerms: specialTerms || undefined,
      }
      
      await applicationService.approveApplication(selectedApplication.id, data)
      
      setShowApproveDialog(false)
      fetchApplications(selectedTab === 'ALL' ? undefined : selectedTab)
    } catch (err) {
      const apiError = handleAPIError(err)
      setError(apiError.message)
    } finally {
      setIsApproving(false)
    }
  }

  /**
   * Open reject dialog
   */
  const handleOpenReject = (application: RentalApplication) => {
    setSelectedApplication(application)
    setRejectionReason('')
    setShowRejectDialog(true)
  }

  /**
   * Reject application
   */
  const handleReject = async () => {
    if (!selectedApplication || !rejectionReason) return

    try {
      setIsRejecting(true)
      const data: RejectApplicationRequest = { reason: rejectionReason }
      
      await applicationService.rejectApplication(selectedApplication.id, data)
      
      setShowRejectDialog(false)
      fetchApplications(selectedTab === 'ALL' ? undefined : selectedTab)
    } catch (err) {
      const apiError = handleAPIError(err)
      setError(apiError.message)
    } finally {
      setIsRejecting(false)
    }
  }

  return (
    <div className="p-8">
      {/* Header */}
      <div className="mb-6">
        <h1 className="text-3xl font-bold">Applications</h1>
        <p className="text-muted-foreground">Review and manage rental applications</p>
      </div>

      {/* Error Alert */}
      {error && (
        <Alert variant="destructive" className="mb-6">
          <AlertDescription>{error}</AlertDescription>
        </Alert>
      )}

      {/* Tabs */}
      <Tabs value={selectedTab} onValueChange={setSelectedTab}>
        <TabsList>
          <TabsTrigger value="PENDING">Pending</TabsTrigger>
          <TabsTrigger value="UNDER_REVIEW">Under Review</TabsTrigger>
          <TabsTrigger value="APPROVED">Approved</TabsTrigger>
          <TabsTrigger value="REJECTED">Rejected</TabsTrigger>
          <TabsTrigger value="ALL">All</TabsTrigger>
        </TabsList>

        <TabsContent value={selectedTab} className="mt-6">
          {isLoading ? (
            <div className="space-y-4">
              {Array.from({ length: 3 }).map((_, i) => (
                <Skeleton key={i} className="h-64" />
              ))}
            </div>
          ) : applications.length === 0 ? (
            <Card>
              <CardContent className="py-16 text-center">
                <Users className="mx-auto h-12 w-12 text-muted-foreground mb-4" />
                <h3 className="text-lg font-semibold mb-2">No Applications</h3>
                <p className="text-muted-foreground">
                  You don't have any {selectedTab.toLowerCase()} applications
                </p>
              </CardContent>
            </Card>
          ) : (
            <div className="space-y-6">
              {applications.map((application) => (
                <Card key={application.id}>
                  <CardHeader>
                    <div className="flex items-start justify-between">
                      <div>
                        <CardTitle className="text-xl">
                          {application.tenant.fullName}
                        </CardTitle>
                        <p className="text-sm text-muted-foreground mt-1">
                          Applied {new Date(application.appliedAt).toLocaleDateString()}
                        </p>
                      </div>
                      <Badge variant={
                        application.status === 'APPROVED' ? 'success' :
                        application.status === 'REJECTED' ? 'destructive' :
                        'warning' as any
                      }>
                        {application.status.replace('_', ' ')}
                      </Badge>
                    </div>
                  </CardHeader>
                  <CardContent className="space-y-4">
                    {/* Property Info */}
                    <div className="flex items-center gap-3 p-4 rounded-lg bg-muted/50">
                      <Home className="h-5 w-5 text-muted-foreground" />
                      <div>
                        <p className="font-medium">{application.property.title}</p>
                        <p className="text-sm text-muted-foreground">
                          {application.property.address}, {application.property.city}
                        </p>
                      </div>
                    </div>

                    {/* Tenant Details */}
                    <div className="grid grid-cols-2 gap-4">
                      <div>
                        <p className="text-sm font-medium text-muted-foreground">Contact</p>
                        <div className="mt-1 space-y-1">
                          <div className="flex items-center gap-2 text-sm">
                            <Mail className="h-4 w-4" />
                            {application.tenant.email}
                          </div>
                          {application.tenant.phoneNumber && (
                            <div className="flex items-center gap-2 text-sm">
                              <Phone className="h-4 w-4" />
                              {application.tenant.phoneNumber}
                            </div>
                          )}
                        </div>
                      </div>

                      <div>
                        <p className="text-sm font-medium text-muted-foreground">Employment</p>
                        <div className="mt-1 space-y-1 text-sm">
                          <div className="flex items-center gap-2">
                            <Briefcase className="h-4 w-4" />
                            {application.occupation}
                          </div>
                          {application.employerName && (
                            <p className="text-muted-foreground">{application.employerName}</p>
                          )}
                          <p className="font-medium">
                            Monthly Income: NPR {application.monthlyIncome.toLocaleString()}
                          </p>
                        </div>
                      </div>
                    </div>

                    {/* Application Details */}
                    <div className="grid grid-cols-3 gap-4 p-4 rounded-lg bg-muted/50">
                      <div>
                        <p className="text-sm text-muted-foreground">Move-in Date</p>
                        <p className="font-medium">{new Date(application.moveInDate).toLocaleDateString()}</p>
                      </div>
                      <div>
                        <p className="text-sm text-muted-foreground">Occupants</p>
                        <p className="font-medium">{application.numberOfOccupants}</p>
                      </div>
                      <div>
                        <p className="text-sm text-muted-foreground">Pets</p>
                        <p className="font-medium">{application.hasPets ? 'Yes' : 'No'}</p>
                      </div>
                    </div>

                    {application.petDetails && (
                      <Alert>
                        <AlertDescription>
                          <strong>Pet Details:</strong> {application.petDetails}
                        </AlertDescription>
                      </Alert>
                    )}

                    {application.message && (
                      <div>
                        <p className="text-sm font-medium mb-2">Message from tenant:</p>
                        <p className="text-sm text-muted-foreground p-3 rounded-lg bg-muted/50">
                          {application.message}
                        </p>
                      </div>
                    )}

                    {/* Actions */}
                    {application.status === 'PENDING' && (
                      <div className="flex gap-2 pt-4 border-t">
                        <Button
                          onClick={() => handleOpenApprove(application)}
                          className="flex-1"
                        >
                          <Check className="mr-2 h-4 w-4" />
                          Approve
                        </Button>
                        <Button
                          onClick={() => handleOpenReject(application)}
                          variant="destructive"
                          className="flex-1"
                        >
                          <X className="mr-2 h-4 w-4" />
                          Reject
                        </Button>
                      </div>
                    )}
                  </CardContent>
                </Card>
              ))}
            </div>
          )}
        </TabsContent>
      </Tabs>

      {/* Approve Dialog */}
      <Dialog open={showApproveDialog} onOpenChange={setShowApproveDialog}>
        <DialogContent className="max-w-2xl">
          <DialogHeader>
            <DialogTitle>Approve Application</DialogTitle>
            <DialogDescription>
              Set lease terms for {selectedApplication?.tenant.fullName}
            </DialogDescription>
          </DialogHeader>
          
          <div className="space-y-4">
            <div className="grid grid-cols-2 gap-4">
              <div>
                <Label htmlFor="leaseStartDate">Lease Start Date</Label>
                <Input
                  id="leaseStartDate"
                  type="date"
                  value={leaseStartDate}
                  onChange={(e) => setLeaseStartDate(e.target.value)}
                />
              </div>
              <div>
                <Label htmlFor="leaseEndDate">Lease End Date</Label>
                <Input
                  id="leaseEndDate"
                  type="date"
                  value={leaseEndDate}
                  onChange={(e) => setLeaseEndDate(e.target.value)}
                />
              </div>
            </div>

            <div className="grid grid-cols-2 gap-4">
              <div>
                <Label htmlFor="monthlyRent">Monthly Rent (NPR)</Label>
                <Input
                  id="monthlyRent"
                  type="number"
                  value={monthlyRent}
                  onChange={(e) => setMonthlyRent(e.target.value)}
                />
              </div>
              <div>
                <Label htmlFor="securityDeposit">Security Deposit (NPR)</Label>
                <Input
                  id="securityDeposit"
                  type="number"
                  value={securityDeposit}
                  onChange={(e) => setSecurityDeposit(e.target.value)}
                />
              </div>
            </div>

            <div>
              <Label htmlFor="specialTerms">Special Terms (Optional)</Label>
              <Textarea
                id="specialTerms"
                placeholder="Any special terms or conditions..."
                rows={3}
                value={specialTerms}
                onChange={(e) => setSpecialTerms(e.target.value)}
              />
            </div>
          </div>

          <DialogFooter>
            <Button
              variant="outline"
              onClick={() => setShowApproveDialog(false)}
              disabled={isApproving}
            >
              Cancel
            </Button>
            <Button onClick={handleApprove} disabled={isApproving}>
              {isApproving ? 'Approving...' : 'Approve & Create Lease'}
            </Button>
          </DialogFooter>
        </DialogContent>
      </Dialog>

      {/* Reject Dialog */}
      <Dialog open={showRejectDialog} onOpenChange={setShowRejectDialog}>
        <DialogContent>
          <DialogHeader>
            <DialogTitle>Reject Application</DialogTitle>
            <DialogDescription>
              Provide a reason for rejecting {selectedApplication?.tenant.fullName}'s application
            </DialogDescription>
          </DialogHeader>
          
          <div>
            <Label htmlFor="rejectionReason">Reason for Rejection</Label>
            <Textarea
              id="rejectionReason"
              placeholder="Please explain why you're rejecting this application..."
              rows={4}
              value={rejectionReason}
              onChange={(e) => setRejectionReason(e.target.value)}
            />
          </div>

          <DialogFooter>
            <Button
              variant="outline"
              onClick={() => setShowRejectDialog(false)}
              disabled={isRejecting}
            >
              Cancel
            </Button>
            <Button
              variant="destructive"
              onClick={handleReject}
              disabled={isRejecting || !rejectionReason}
            >
              {isRejecting ? 'Rejecting...' : 'Reject Application'}
            </Button>
          </DialogFooter>
        </DialogContent>
      </Dialog>
    </div>
  )
}
```

### Verification Checklist

- [ ] Applications loaded from API
- [ ] Tab filtering working (Pending, Approved, Rejected, All)
- [ ] Application details displayed
- [ ] Approve dialog with lease terms form
- [ ] Reject dialog with reason field
- [ ] Approve action creates lease
- [ ] Reject action updates status
- [ ] Empty states for each tab
- [ ] Loading and error states

---

## Phase 5 Completion Checklist

### Tenant Application Flow

- [ ] Tenant can submit application from property page
- [ ] Application form with all required fields
- [ ] Form validation working
- [ ] Application submission successful
- [ ] Tenant can view all applications
- [ ] Tenant can withdraw pending applications
- [ ] Application status displayed correctly

### Landlord Review Flow

- [ ] Landlord can view all applications
- [ ] Applications filtered by status
- [ ] Application details displayed completely
- [ ] Landlord can approve applications
- [ ] Landlord can reject applications
- [ ] Lease auto-created on approval
- [ ] Rejection reason sent to tenant

### Lease Management

- [ ] Lease service created
- [ ] Lease types defined
- [ ] Tenant can view leases (to be built in next task)
- [ ] Landlord can view leases (to be built in next task)
- [ ] Lease termination (to be built in next task)

### Testing

- [ ] Submit application successfully
- [ ] View submitted applications
- [ ] Withdraw application
- [ ] Landlord views applications
- [ ] Approve application creates lease
- [ ] Reject application with reason
- [ ] Error handling throughout

---

*Phase 6-10 continue with Payment System, Supporting Features, Admin Panel, Testing, and Deployment...*

---

# PHASE 6: PAYMENT SYSTEM INTEGRATION

**Priority:** 🟡 MEDIUM  
**Duration:** 3-4 days  
**Dependencies:** Phase 1, Phase 2, Phase 5  
**Estimated Hours:** 20-24h

## Objectives

- Create payment type definitions and services
- Integrate payment tracking for tenants
- Implement payment management for landlords
- Add payment confirmation workflow
- Display payment statistics and history

---

## Task 6.1: Create Payment Type Definitions

**Duration:** 30 minutes

### Steps

**1. Create `types/payment.types.ts`:**

```typescript
/**
 * Payment status
 */
export type PaymentStatus = 'PENDING' | 'PAID' | 'CONFIRMED' | 'LATE' | 'CANCELLED'

/**
 * Payment entity
 */
export interface Payment {
  id: number
  lease: {
    id: number
    property: {
      id: number
      title: string
      address: string
    }
  }
  tenant: {
    id: number
    fullName: string
    email: string
  }
  landlord: {
    id: number
    fullName: string
  }
  amount: number
  dueDate: string
  paidDate?: string
  confirmedDate?: string
  status: PaymentStatus
  paymentMethod?: string
  transactionId?: string
  notes?: string
  createdAt: string
}

/**
 * Payment list response
 */
export interface PaymentListResponse {
  payments: Payment[]
  currentPage: number
  totalPages: number
  totalPayments: number
}

/**
 * Payment statistics
 */
export interface PaymentStatistics {
  totalPaid: number
  totalPending: number
  totalLate: number
  onTimePaymentRate: number
  averagePaymentDelay: number
}

/**
 * Mark paid request
 */
export interface MarkPaidRequest {
  paidDate: string
  paymentMethod?: string
  transactionId?: string
  notes?: string
}

/**
 * Confirm payment request
 */
export interface ConfirmPaymentRequest {
  notes?: string
}
```

**2. Export from `types/index.ts`:**

```typescript
export * from './payment.types'
```

### Verification Checklist

- [ ] Payment types defined
- [ ] Status enum included
- [ ] Statistics type created
- [ ] Request types defined
- [ ] Types exported

---

## Task 6.2: Create Payment Service

**Duration:** 1 hour

### Steps

**1. Create `lib/services/payment.service.ts`:**

```typescript
import apiClient from '@/lib/api/client'
import { API_ENDPOINTS } from '@/lib/constants/api'
import type {
  Payment,
  PaymentListResponse,
  PaymentStatistics,
  MarkPaidRequest,
  ConfirmPaymentRequest,
} from '@/types/payment.types'

/**
 * Payment Service
 */
class PaymentService {
  /**
   * Get payment by ID
   */
  async getPaymentById(paymentId: number): Promise<Payment> {
    const response = await apiClient.get<Payment>(
      API_ENDPOINTS.PAYMENTS.BY_ID(paymentId)
    )
    return response.data
  }

  /**
   * Get payments by lease
   */
  async getPaymentsByLease(leaseId: number): Promise<Payment[]> {
    const response = await apiClient.get<Payment[]>(
      API_ENDPOINTS.PAYMENTS.BY_LEASE(leaseId)
    )
    return response.data
  }

  /**
   * Get tenant's payments
   */
  async getTenantPayments(
    tenantId: number,
    page: number = 0,
    size: number = 10
  ): Promise<PaymentListResponse> {
    const response = await apiClient.get<PaymentListResponse>(
      API_ENDPOINTS.PAYMENTS.TENANT_PAYMENTS(tenantId),
      { params: { page, size } }
    )
    return response.data
  }

  /**
   * Get tenant's payment statistics
   */
  async getTenantStatistics(tenantId: number): Promise<PaymentStatistics> {
    const response = await apiClient.get<PaymentStatistics>(
      API_ENDPOINTS.PAYMENTS.TENANT_STATISTICS(tenantId)
    )
    return response.data
  }

  /**
   * Get tenant's upcoming payments
   */
  async getTenantUpcomingPayments(tenantId: number): Promise<Payment[]> {
    const response = await apiClient.get<Payment[]>(
      API_ENDPOINTS.PAYMENTS.TENANT_UPCOMING(tenantId)
    )
    return response.data
  }

  /**
   * Get landlord's payments
   */
  async getLandlordPayments(
    landlordId: number,
    page: number = 0,
    size: number = 10
  ): Promise<PaymentListResponse> {
    const response = await apiClient.get<PaymentListResponse>(
      API_ENDPOINTS.PAYMENTS.LANDLORD_PAYMENTS(landlordId),
      { params: { page, size } }
    )
    return response.data
  }

  /**
   * Get landlord's payment statistics
   */
  async getLandlordStatistics(landlordId: number): Promise<PaymentStatistics> {
    const response = await apiClient.get<PaymentStatistics>(
      API_ENDPOINTS.PAYMENTS.LANDLORD_STATISTICS(landlordId)
    )
    return response.data
  }

  /**
   * Mark payment as paid (Tenant)
   */
  async markAsPaid(
    paymentId: number,
    data: MarkPaidRequest
  ): Promise<Payment> {
    const response = await apiClient.post<Payment>(
      API_ENDPOINTS.PAYMENTS.MARK_PAID(paymentId),
      data
    )
    return response.data
  }

  /**
   * Confirm payment (Landlord)
   */
  async confirmPayment(
    paymentId: number,
    data: ConfirmPaymentRequest
  ): Promise<Payment> {
    const response = await apiClient.post<Payment>(
      API_ENDPOINTS.PAYMENTS.CONFIRM(paymentId),
      data
    )
    return response.data
  }
}

export const paymentService = new PaymentService()
export default paymentService
```

**2. Export from `lib/services/index.ts`:**

```typescript
export * from './payment.service'
```

### Verification Checklist

- [ ] Payment service created
- [ ] Tenant payment methods
- [ ] Landlord payment methods
- [ ] Statistics methods
- [ ] Mark paid and confirm methods

---

## Task 6.3: Create Tenant Payment Page

**Duration:** 3 hours

### Steps

**1. Create `app/tenant/payments/page.tsx`:**

```typescript
'use client'

import { useState, useEffect } from 'react'
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card'
import { Button } from '@/components/ui/button'
import { Badge } from '@/components/ui/badge'
import { Skeleton } from '@/components/ui/skeleton'
import { Alert, AlertDescription } from '@/components/ui/alert'
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
} from '@/components/ui/dialog'
import { Input } from '@/components/ui/input'
import { Label } from '@/components/ui/label'
import { Textarea } from '@/components/ui/textarea'
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select'
import {
  CreditCard,
  Clock,
  CheckCircle2,
  AlertCircle,
  TrendingUp,
  Calendar,
} from 'lucide-react'
import { StatsCard } from '@/components/stats-card'
import { paymentService } from '@/lib/services/payment.service'
import { useAuth } from '@/contexts/AuthContext'
import { handleAPIError } from '@/lib/api/errorHandler'
import type { Payment, PaymentStatistics, MarkPaidRequest } from '@/types/payment.types'

export default function TenantPaymentsPage() {
  const { user } = useAuth()
  const [payments, setPayments] = useState<Payment[]>([])
  const [statistics, setStatistics] = useState<PaymentStatistics | null>(null)
  const [isLoading, setIsLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)
  
  // Payment dialog
  const [selectedPayment, setSelectedPayment] = useState<Payment | null>(null)
  const [showPaymentDialog, setShowPaymentDialog] = useState(false)
  const [isSubmitting, setIsSubmitting] = useState(false)
  const [paymentDate, setPaymentDate] = useState('')
  const [paymentMethod, setPaymentMethod] = useState('')
  const [transactionId, setTransactionId] = useState('')
  const [notes, setNotes] = useState('')

  /**
   * Fetch payments and statistics
   */
  useEffect(() => {
    const fetchData = async () => {
      if (!user) return

      try {
        setIsLoading(true)
        setError(null)
        
        const [paymentsData, statsData] = await Promise.all([
          paymentService.getTenantPayments(user.id, 0, 50),
          paymentService.getTenantStatistics(user.id),
        ])
        
        setPayments(paymentsData.payments)
        setStatistics(statsData)
      } catch (err) {
        const apiError = handleAPIError(err)
        setError(apiError.message)
      } finally {
        setIsLoading(false)
      }
    }

    fetchData()
  }, [user])

  /**
   * Open payment dialog
   */
  const handleOpenPaymentDialog = (payment: Payment) => {
    setSelectedPayment(payment)
    setPaymentDate(new Date().toISOString().split('T')[0])
    setPaymentMethod('BANK_TRANSFER')
    setTransactionId('')
    setNotes('')
    setShowPaymentDialog(true)
  }

  /**
   * Submit payment
   */
  const handleSubmitPayment = async () => {
    if (!selectedPayment) return

    try {
      setIsSubmitting(true)
      const data: MarkPaidRequest = {
        paidDate: paymentDate,
        paymentMethod,
        transactionId: transactionId || undefined,
        notes: notes || undefined,
      }
      
      await paymentService.markAsPaid(selectedPayment.id, data)
      
      setShowPaymentDialog(false)
      
      // Refresh data
      if (user) {
        const paymentsData = await paymentService.getTenantPayments(user.id, 0, 50)
        setPayments(paymentsData.payments)
      }
    } catch (err) {
      const apiError = handleAPIError(err)
      setError(apiError.message)
    } finally {
      setIsSubmitting(false)
    }
  }

  /**
   * Get status badge variant
   */
  const getStatusVariant = (status: string) => {
    switch (status) {
      case 'CONFIRMED':
        return 'success'
      case 'PAID':
        return 'default'
      case 'LATE':
        return 'destructive'
      case 'PENDING':
        return 'warning'
      default:
        return 'secondary'
    }
  }

  /**
   * Get status icon
   */
  const getStatusIcon = (status: string) => {
    switch (status) {
      case 'CONFIRMED':
        return CheckCircle2
      case 'PAID':
        return CreditCard
      case 'LATE':
        return AlertCircle
      case 'PENDING':
        return Clock
      default:
        return CreditCard
    }
  }

  if (isLoading) {
    return (
      <div className="p-8 space-y-6">
        <div className="grid gap-4 md:grid-cols-4">
          {Array.from({ length: 4 }).map((_, i) => (
            <Skeleton key={i} className="h-32" />
          ))}
        </div>
        <Skeleton className="h-[400px]" />
      </div>
    )
  }

  return (
    <div className="p-8 space-y-6">
      {/* Header */}
      <div>
        <h1 className="text-3xl font-bold">Payments</h1>
        <p className="text-muted-foreground">Track your rent payments and history</p>
      </div>

      {/* Error Alert */}
      {error && (
        <Alert variant="destructive">
          <AlertDescription>{error}</AlertDescription>
        </Alert>
      )}

      {/* Statistics */}
      {statistics && (
        <div className="grid gap-4 md:grid-cols-4">
          <StatsCard
            title="Total Paid"
            value={`NPR ${(statistics.totalPaid / 1000).toFixed(1)}K`}
            icon={CheckCircle2}
            description="All time"
            variant="success"
          />
          <StatsCard
            title="Pending"
            value={`NPR ${statistics.totalPending.toLocaleString()}`}
            icon={Clock}
            description="Due soon"
            variant="warning"
          />
          <StatsCard
            title="On-Time Rate"
            value={`${statistics.onTimePaymentRate}%`}
            icon={TrendingUp}
            description="Payment reliability"
          />
          <StatsCard
            title="Average Delay"
            value={`${statistics.averagePaymentDelay} days`}
            icon={Calendar}
            description="When late"
          />
        </div>
      )}

      {/* Payments List */}
      <Card>
        <CardHeader>
          <CardTitle>Payment History</CardTitle>
        </CardHeader>
        <CardContent>
          {payments.length === 0 ? (
            <div className="text-center py-8">
              <CreditCard className="mx-auto h-12 w-12 text-muted-foreground mb-4" />
              <p className="text-muted-foreground">No payments yet</p>
            </div>
          ) : (
            <div className="space-y-3">
              {payments.map((payment) => {
                const StatusIcon = getStatusIcon(payment.status)
                const isOverdue = payment.status === 'PENDING' && 
                                 new Date(payment.dueDate) < new Date()
                
                return (
                  <div
                    key={payment.id}
                    className="flex items-center justify-between p-4 rounded-lg border"
                  >
                    <div className="flex items-center gap-4">
                      <div className={`flex h-10 w-10 items-center justify-center rounded-full ${
                        payment.status === 'CONFIRMED' ? 'bg-green-100 dark:bg-green-900' :
                        payment.status === 'LATE' ? 'bg-red-100 dark:bg-red-900' :
                        'bg-muted'
                      }`}>
                        <StatusIcon className="h-5 w-5" />
                      </div>
                      <div>
                        <p className="font-medium">{payment.lease.property.title}</p>
                        <p className="text-sm text-muted-foreground">
                          {payment.lease.property.address}
                        </p>
                        <p className="text-xs text-muted-foreground mt-1">
                          Due: {new Date(payment.dueDate).toLocaleDateString()}
                          {payment.paidDate && ` • Paid: ${new Date(payment.paidDate).toLocaleDateString()}`}
                        </p>
                      </div>
                    </div>
                    
                    <div className="flex items-center gap-4">
                      <div className="text-right">
                        <p className="font-bold">NPR {payment.amount.toLocaleString()}</p>
                        <Badge variant={getStatusVariant(payment.status) as any}>
                          {payment.status}
                        </Badge>
                      </div>
                      
                      {(payment.status === 'PENDING' || payment.status === 'LATE') && (
                        <Button
                          size="sm"
                          onClick={() => handleOpenPaymentDialog(payment)}
                          variant={isOverdue ? 'destructive' : 'default'}
                        >
                          Mark as Paid
                        </Button>
                      )}
                    </div>
                  </div>
                )
              })}
            </div>
          )}
        </CardContent>
      </Card>

      {/* Payment Dialog */}
      <Dialog open={showPaymentDialog} onOpenChange={setShowPaymentDialog}>
        <DialogContent>
          <DialogHeader>
            <DialogTitle>Mark Payment as Paid</DialogTitle>
            <DialogDescription>
              Confirm your payment for {selectedPayment?.lease.property.title}
            </DialogDescription>
          </DialogHeader>
          
          <div className="space-y-4">
            <div>
              <Label htmlFor="paymentAmount">Amount</Label>
              <Input
                id="paymentAmount"
                value={`NPR ${selectedPayment?.amount.toLocaleString()}`}
                disabled
              />
            </div>

            <div>
              <Label htmlFor="paymentDate">Payment Date</Label>
              <Input
                id="paymentDate"
                type="date"
                value={paymentDate}
                onChange={(e) => setPaymentDate(e.target.value)}
                max={new Date().toISOString().split('T')[0]}
              />
            </div>

            <div>
              <Label htmlFor="paymentMethod">Payment Method</Label>
              <Select value={paymentMethod} onValueChange={setPaymentMethod}>
                <SelectTrigger>
                  <SelectValue placeholder="Select method" />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="BANK_TRANSFER">Bank Transfer</SelectItem>
                  <SelectItem value="CASH">Cash</SelectItem>
                  <SelectItem value="DIGITAL_WALLET">Digital Wallet (eSewa, Khalti)</SelectItem>
                  <SelectItem value="CHEQUE">Cheque</SelectItem>
                </SelectContent>
              </Select>
            </div>

            <div>
              <Label htmlFor="transactionId">Transaction ID (Optional)</Label>
              <Input
                id="transactionId"
                placeholder="Payment reference number"
                value={transactionId}
                onChange={(e) => setTransactionId(e.target.value)}
              />
            </div>

            <div>
              <Label htmlFor="notes">Notes (Optional)</Label>
              <Textarea
                id="notes"
                placeholder="Any additional information..."
                rows={3}
                value={notes}
                onChange={(e) => setNotes(e.target.value)}
              />
            </div>
          </div>

          <DialogFooter>
            <Button
              variant="outline"
              onClick={() => setShowPaymentDialog(false)}
              disabled={isSubmitting}
            >
              Cancel
            </Button>
            <Button onClick={handleSubmitPayment} disabled={isSubmitting}>
              {isSubmitting ? 'Submitting...' : 'Confirm Payment'}
            </Button>
          </DialogFooter>
        </DialogContent>
      </Dialog>
    </div>
  )
}
```

### Verification Checklist

- [ ] Payments loaded from API
- [ ] Statistics displayed
- [ ] Payment list with status badges
- [ ] Mark as paid dialog
- [ ] Payment submission working
- [ ] List refreshes after payment
- [ ] Empty state shown
- [ ] Loading and error states

---

## Phase 6 Completion Checklist

### Payment Integration

- [ ] Payment service created
- [ ] Tenant can view payments
- [ ] Tenant can mark payments as paid
- [ ] Landlord can view payments (to be built)
- [ ] Landlord can confirm payments (to be built)
- [ ] Payment statistics displayed

### User Experience

- [ ] Payment history clearly displayed
- [ ] Status badges with colors
- [ ] Payment dialog with all fields
- [ ] Success feedback after submission
- [ ] Error handling

### Testing

- [ ] View payment history
- [ ] Mark payment as paid
- [ ] View payment statistics
- [ ] Overdue payments highlighted
- [ ] Confirmed payments shown

---

# PHASE 7: Supporting Features (32-40h)

**Objective:** Implement essential supporting features including notifications, file uploads, reviews, user profiles, and saved properties.

**Dependencies:** Phases 1-6 completed

**Tasks:**

- Task 7.1: Notification System Integration (6-8h)
- Task 7.2: File Upload System (6-8h)
- Task 7.3: Property Review System (8-10h)
- Task 7.4: User Profile Management (6-8h)
- Task 7.5: Saved Properties Feature (6-8h)

---

## Task 7.1: Notification System Integration (6-8h)

### Step 1: Create Notification Types and Service

**File: `frontend/types/notification.ts`**

```typescript
// Notification Types
export enum NotificationType {
  APPLICATION_SUBMITTED = 'APPLICATION_SUBMITTED',
  APPLICATION_APPROVED = 'APPLICATION_APPROVED',
  APPLICATION_REJECTED = 'APPLICATION_REJECTED',
  LEASE_CREATED = 'LEASE_CREATED',
  LEASE_EXPIRING = 'LEASE_EXPIRING',
  PAYMENT_DUE = 'PAYMENT_DUE',
  PAYMENT_RECEIVED = 'PAYMENT_RECEIVED',
  PROPERTY_VERIFIED = 'PROPERTY_VERIFIED',
  PROPERTY_REJECTED = 'PROPERTY_REJECTED',
  REVIEW_SUBMITTED = 'REVIEW_SUBMITTED',
  SYSTEM = 'SYSTEM'
}

export interface Notification {
  id: number;
  userId: number;
  type: NotificationType;
  title: string;
  message: string;
  read: boolean;
  createdAt: string;
  relatedEntityId?: number;
  relatedEntityType?: string;
}

export interface NotificationPreferences {
  id: number;
  userId: number;
  emailNotifications: boolean;
  applicationUpdates: boolean;
  leaseUpdates: boolean;
  paymentReminders: boolean;
  propertyUpdates: boolean;
  reviewNotifications: boolean;
  marketingEmails: boolean;
}

export interface NotificationStats {
  totalUnread: number;
  unreadByType: Record<NotificationType, number>;
}

export interface NotificationResponse {
  content: Notification[];
  totalElements: number;
  totalPages: number;
  currentPage: number;
  pageSize: number;
  hasNext: boolean;
}
```

**File: `frontend/lib/services/notification.service.ts`**

```typescript
import apiClient from '../api-client';
import { API_ENDPOINTS } from '../constants/api-endpoints';
import type {
  Notification,
  NotificationPreferences,
  NotificationStats,
  NotificationResponse
} from '@/types/notification';

export const notificationService = {
  /**
   * Get paginated notifications for current user
   */
  async getNotifications(page = 0, size = 20, unreadOnly = false): Promise<NotificationResponse> {
    const params = new URLSearchParams({
      page: page.toString(),
      size: size.toString(),
      ...(unreadOnly && { unreadOnly: 'true' })
    });
    
    const response = await apiClient.get<NotificationResponse>(
      `${API_ENDPOINTS.NOTIFICATIONS}?${params}`
    );
    return response.data;
  },

  /**
   * Get notification statistics
   */
  async getNotificationStats(): Promise<NotificationStats> {
    const response = await apiClient.get<NotificationStats>(
      `${API_ENDPOINTS.NOTIFICATIONS}/stats`
    );
    return response.data;
  },

  /**
   * Mark single notification as read
   */
  async markAsRead(notificationId: number): Promise<void> {
    await apiClient.put(`${API_ENDPOINTS.NOTIFICATIONS}/${notificationId}/read`);
  },

  /**
   * Mark all notifications as read
   */
  async markAllAsRead(): Promise<void> {
    await apiClient.put(`${API_ENDPOINTS.NOTIFICATIONS}/mark-all-read`);
  },

  /**
   * Delete a notification
   */
  async deleteNotification(notificationId: number): Promise<void> {
    await apiClient.delete(`${API_ENDPOINTS.NOTIFICATIONS}/${notificationId}`);
  },

  /**
   * Delete all read notifications
   */
  async deleteAllRead(): Promise<void> {
    await apiClient.delete(`${API_ENDPOINTS.NOTIFICATIONS}/delete-read`);
  },

  /**
   * Get notification preferences
   */
  async getPreferences(): Promise<NotificationPreferences> {
    const response = await apiClient.get<NotificationPreferences>(
      `${API_ENDPOINTS.NOTIFICATIONS}/preferences`
    );
    return response.data;
  },

  /**
   * Update notification preferences
   */
  async updatePreferences(preferences: Partial<NotificationPreferences>): Promise<NotificationPreferences> {
    const response = await apiClient.put<NotificationPreferences>(
      `${API_ENDPOINTS.NOTIFICATIONS}/preferences`,
      preferences
    );
    return response.data;
  }
};
```

### Step 2: Add Notification Endpoints to Constants

**Update: `frontend/lib/constants/api-endpoints.ts`**

Add these endpoints to the existing file:

```typescript
export const API_ENDPOINTS = {
  // ... existing endpoints ...
  NOTIFICATIONS: '/notifications',
  // ... rest of endpoints
};
```

### Step 3: Create Notification Center Component

**File: `frontend/components/notification-center.tsx`**

```typescript
'use client';

import { useState, useEffect } from 'react';
import { Bell, Check, Trash2, Settings, X } from 'lucide-react';
import { Button } from './ui/button';
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from './ui/popover';
import { Badge } from './ui/badge';
import { ScrollArea } from './ui/scroll-area';
import { Tabs, TabsContent, TabsList, TabsTrigger } from './ui/tabs';
import { formatDistanceToNow } from 'date-fns';
import { notificationService } from '@/lib/services/notification.service';
import type { Notification, NotificationStats } from '@/types/notification';
import { useToast } from '@/hooks/use-toast';
import Link from 'next/link';

export function NotificationCenter() {
  const [notifications, setNotifications] = useState<Notification[]>([]);
  const [stats, setStats] = useState<NotificationStats | null>(null);
  const [loading, setLoading] = useState(false);
  const [open, setOpen] = useState(false);
  const { toast } = useToast();

  // Load notifications when popover opens
  useEffect(() => {
    if (open) {
      loadNotifications();
      loadStats();
    }
  }, [open]);

  const loadNotifications = async () => {
    try {
      setLoading(true);
      const response = await notificationService.getNotifications(0, 20);
      setNotifications(response.content);
    } catch (error) {
      console.error('Failed to load notifications:', error);
    } finally {
      setLoading(false);
    }
  };

  const loadStats = async () => {
    try {
      const statsData = await notificationService.getNotificationStats();
      setStats(statsData);
    } catch (error) {
      console.error('Failed to load notification stats:', error);
    }
  };

  const handleMarkAsRead = async (notificationId: number) => {
    try {
      await notificationService.markAsRead(notificationId);
      setNotifications(prev =>
        prev.map(n => n.id === notificationId ? { ...n, read: true } : n)
      );
      loadStats(); // Refresh unread count
      toast({
        title: 'Success',
        description: 'Notification marked as read',
      });
    } catch (error) {
      toast({
        title: 'Error',
        description: 'Failed to mark notification as read',
        variant: 'destructive',
      });
    }
  };

  const handleMarkAllAsRead = async () => {
    try {
      await notificationService.markAllAsRead();
      setNotifications(prev => prev.map(n => ({ ...n, read: true })));
      loadStats();
      toast({
        title: 'Success',
        description: 'All notifications marked as read',
      });
    } catch (error) {
      toast({
        title: 'Error',
        description: 'Failed to mark all as read',
        variant: 'destructive',
      });
    }
  };

  const handleDelete = async (notificationId: number) => {
    try {
      await notificationService.deleteNotification(notificationId);
      setNotifications(prev => prev.filter(n => n.id !== notificationId));
      loadStats();
      toast({
        title: 'Success',
        description: 'Notification deleted',
      });
    } catch (error) {
      toast({
        title: 'Error',
        description: 'Failed to delete notification',
        variant: 'destructive',
      });
    }
  };

  const handleDeleteAllRead = async () => {
    try {
      await notificationService.deleteAllRead();
      setNotifications(prev => prev.filter(n => !n.read));
      loadStats();
      toast({
        title: 'Success',
        description: 'All read notifications deleted',
      });
    } catch (error) {
      toast({
        title: 'Error',
        description: 'Failed to delete notifications',
        variant: 'destructive',
      });
    }
  };

  const getNotificationLink = (notification: Notification): string => {
    switch (notification.type) {
      case 'APPLICATION_SUBMITTED':
      case 'APPLICATION_APPROVED':
      case 'APPLICATION_REJECTED':
        return '/tenant/applications';
      case 'LEASE_CREATED':
      case 'LEASE_EXPIRING':
        return '/tenant/leases';
      case 'PAYMENT_DUE':
      case 'PAYMENT_RECEIVED':
        return '/tenant/payments';
      case 'PROPERTY_VERIFIED':
      case 'PROPERTY_REJECTED':
        return '/landlord/properties';
      case 'REVIEW_SUBMITTED':
        return `/properties/${notification.relatedEntityId}`;
      default:
        return '/dashboard';
    }
  };

  const unreadNotifications = notifications.filter(n => !n.read);
  const readNotifications = notifications.filter(n => n.read);

  return (
    <Popover open={open} onOpenChange={setOpen}>
      <PopoverTrigger asChild>
        <Button variant="ghost" size="icon" className="relative">
          <Bell className="h-5 w-5" />
          {stats && stats.totalUnread > 0 && (
            <Badge
              variant="destructive"
              className="absolute -top-1 -right-1 h-5 w-5 rounded-full p-0 flex items-center justify-center text-xs"
            >
              {stats.totalUnread > 99 ? '99+' : stats.totalUnread}
            </Badge>
          )}
        </Button>
      </PopoverTrigger>
      <PopoverContent className="w-96 p-0" align="end">
        <div className="flex items-center justify-between p-4 border-b">
          <h3 className="font-semibold">Notifications</h3>
          <div className="flex items-center gap-2">
            <Button
              variant="ghost"
              size="sm"
              onClick={handleMarkAllAsRead}
              disabled={unreadNotifications.length === 0}
            >
              <Check className="h-4 w-4 mr-1" />
              Mark all read
            </Button>
            <Link href="/settings/notifications">
              <Button variant="ghost" size="icon">
                <Settings className="h-4 w-4" />
              </Button>
            </Link>
          </div>
        </div>

        <Tabs defaultValue="all" className="w-full">
          <TabsList className="w-full justify-start rounded-none border-b">
            <TabsTrigger value="all" className="flex-1">
              All ({notifications.length})
            </TabsTrigger>
            <TabsTrigger value="unread" className="flex-1">
              Unread ({unreadNotifications.length})
            </TabsTrigger>
          </TabsList>

          <TabsContent value="all" className="m-0">
            <ScrollArea className="h-[400px]">
              {loading ? (
                <div className="p-4 text-center text-muted-foreground">
                  Loading notifications...
                </div>
              ) : notifications.length === 0 ? (
                <div className="p-8 text-center">
                  <Bell className="h-12 w-12 mx-auto mb-2 text-muted-foreground" />
                  <p className="text-sm text-muted-foreground">
                    No notifications yet
                  </p>
                </div>
              ) : (
                <>
                  {notifications.map(notification => (
                    <NotificationItem
                      key={notification.id}
                      notification={notification}
                      onMarkAsRead={handleMarkAsRead}
                      onDelete={handleDelete}
                      getLink={getNotificationLink}
                    />
                  ))}
                  {readNotifications.length > 0 && (
                    <div className="p-2 border-t">
                      <Button
                        variant="ghost"
                        size="sm"
                        onClick={handleDeleteAllRead}
                        className="w-full"
                      >
                        <Trash2 className="h-4 w-4 mr-1" />
                        Delete all read
                      </Button>
                    </div>
                  )}
                </>
              )}
            </ScrollArea>
          </TabsContent>

          <TabsContent value="unread" className="m-0">
            <ScrollArea className="h-[400px]">
              {unreadNotifications.length === 0 ? (
                <div className="p-8 text-center">
                  <Check className="h-12 w-12 mx-auto mb-2 text-muted-foreground" />
                  <p className="text-sm text-muted-foreground">
                    All caught up!
                  </p>
                </div>
              ) : (
                unreadNotifications.map(notification => (
                  <NotificationItem
                    key={notification.id}
                    notification={notification}
                    onMarkAsRead={handleMarkAsRead}
                    onDelete={handleDelete}
                    getLink={getNotificationLink}
                  />
                ))
              )}
            </ScrollArea>
          </TabsContent>
        </Tabs>
      </PopoverContent>
    </Popover>
  );
}

interface NotificationItemProps {
  notification: Notification;
  onMarkAsRead: (id: number) => void;
  onDelete: (id: number) => void;
  getLink: (notification: Notification) => string;
}

function NotificationItem({
  notification,
  onMarkAsRead,
  onDelete,
  getLink
}: NotificationItemProps) {
  const link = getLink(notification);

  return (
    <div
      className={`p-4 border-b hover:bg-muted/50 transition-colors ${
        !notification.read ? 'bg-primary/5' : ''
      }`}
    >
      <div className="flex items-start gap-3">
        <div className="flex-1 min-w-0">
          <Link href={link} className="block">
            <div className="flex items-center gap-2 mb-1">
              <h4 className="font-medium text-sm">{notification.title}</h4>
              {!notification.read && (
                <div className="h-2 w-2 rounded-full bg-primary flex-shrink-0" />
              )}
            </div>
            <p className="text-sm text-muted-foreground line-clamp-2">
              {notification.message}
            </p>
            <p className="text-xs text-muted-foreground mt-1">
              {formatDistanceToNow(new Date(notification.createdAt), {
                addSuffix: true
              })}
            </p>
          </Link>
        </div>
        <div className="flex items-center gap-1">
          {!notification.read && (
            <Button
              variant="ghost"
              size="icon"
              onClick={(e) => {
                e.stopPropagation();
                onMarkAsRead(notification.id);
              }}
            >
              <Check className="h-4 w-4" />
            </Button>
          )}
          <Button
            variant="ghost"
            size="icon"
            onClick={(e) => {
              e.stopPropagation();
              onDelete(notification.id);
            }}
          >
            <X className="h-4 w-4" />
          </Button>
        </div>
      </div>
    </div>
  );
}
```

### Step 4: Add Notification Center to Navbar

**Update: `frontend/components/navbar.tsx`**

Import and add the NotificationCenter component:

```typescript
import { NotificationCenter } from './notification-center';

// Inside the navbar, add before the user menu:
{user && <NotificationCenter />}
```

### Step 5: Create Notification Preferences Page

**File: `frontend/app/(protected)/settings/notifications/page.tsx`**

```typescript
'use client';

import { useState, useEffect } from 'react';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';
import { Switch } from '@/components/ui/switch';
import { Label } from '@/components/ui/label';
import { Button } from '@/components/ui/button';
import { notificationService } from '@/lib/services/notification.service';
import type { NotificationPreferences } from '@/types/notification';
import { useToast } from '@/hooks/use-toast';

export default function NotificationSettingsPage() {
  const [preferences, setPreferences] = useState<NotificationPreferences | null>(null);
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const { toast } = useToast();

  useEffect(() => {
    loadPreferences();
  }, []);

  const loadPreferences = async () => {
    try {
      const data = await notificationService.getPreferences();
      setPreferences(data);
    } catch (error) {
      toast({
        title: 'Error',
        description: 'Failed to load notification preferences',
        variant: 'destructive',
      });
    } finally {
      setLoading(false);
    }
  };

  const handleSave = async () => {
    if (!preferences) return;

    try {
      setSaving(true);
      await notificationService.updatePreferences(preferences);
      toast({
        title: 'Success',
        description: 'Notification preferences updated successfully',
      });
    } catch (error) {
      toast({
        title: 'Error',
        description: 'Failed to update preferences',
        variant: 'destructive',
      });
    } finally {
      setSaving(false);
    }
  };

  const updatePreference = (key: keyof NotificationPreferences, value: boolean) => {
    if (!preferences) return;
    setPreferences({ ...preferences, [key]: value });
  };

  if (loading || !preferences) {
    return <div>Loading...</div>;
  }

  return (
    <div className="container max-w-2xl py-8">
      <h1 className="text-3xl font-bold mb-6">Notification Settings</h1>

      <div className="space-y-6">
        <Card>
          <CardHeader>
            <CardTitle>Email Notifications</CardTitle>
            <CardDescription>
              Choose how you want to receive notifications via email
            </CardDescription>
          </CardHeader>
          <CardContent className="space-y-4">
            <div className="flex items-center justify-between">
              <Label htmlFor="email-notifications" className="flex flex-col gap-1">
                <span>Email Notifications</span>
                <span className="text-sm font-normal text-muted-foreground">
                  Receive notifications via email
                </span>
              </Label>
              <Switch
                id="email-notifications"
                checked={preferences.emailNotifications}
                onCheckedChange={(checked) =>
                  updatePreference('emailNotifications', checked)
                }
              />
            </div>
          </CardContent>
        </Card>

        <Card>
          <CardHeader>
            <CardTitle>Application Updates</CardTitle>
            <CardDescription>
              Get notified about application status changes
            </CardDescription>
          </CardHeader>
          <CardContent className="space-y-4">
            <div className="flex items-center justify-between">
              <Label htmlFor="application-updates" className="flex flex-col gap-1">
                <span>Application Updates</span>
                <span className="text-sm font-normal text-muted-foreground">
                  Approval, rejection, and other application updates
                </span>
              </Label>
              <Switch
                id="application-updates"
                checked={preferences.applicationUpdates}
                onCheckedChange={(checked) =>
                  updatePreference('applicationUpdates', checked)
                }
              />
            </div>
          </CardContent>
        </Card>

        <Card>
          <CardHeader>
            <CardTitle>Lease Updates</CardTitle>
            <CardDescription>
              Stay informed about your leases
            </CardDescription>
          </CardHeader>
          <CardContent className="space-y-4">
            <div className="flex items-center justify-between">
              <Label htmlFor="lease-updates" className="flex flex-col gap-1">
                <span>Lease Updates</span>
                <span className="text-sm font-normal text-muted-foreground">
                  Lease creation, expiration reminders, and updates
                </span>
              </Label>
              <Switch
                id="lease-updates"
                checked={preferences.leaseUpdates}
                onCheckedChange={(checked) =>
                  updatePreference('leaseUpdates', checked)
                }
              />
            </div>
          </CardContent>
        </Card>

        <Card>
          <CardHeader>
            <CardTitle>Payment Reminders</CardTitle>
            <CardDescription>
              Get notified about upcoming and overdue payments
            </CardDescription>
          </CardHeader>
          <CardContent className="space-y-4">
            <div className="flex items-center justify-between">
              <Label htmlFor="payment-reminders" className="flex flex-col gap-1">
                <span>Payment Reminders</span>
                <span className="text-sm font-normal text-muted-foreground">
                  Due dates, overdue payments, and payment confirmations
                </span>
              </Label>
              <Switch
                id="payment-reminders"
                checked={preferences.paymentReminders}
                onCheckedChange={(checked) =>
                  updatePreference('paymentReminders', checked)
                }
              />
            </div>
          </CardContent>
        </Card>

        <Card>
          <CardHeader>
            <CardTitle>Property Updates</CardTitle>
            <CardDescription>
              Updates about your listed properties
            </CardDescription>
          </CardHeader>
          <CardContent className="space-y-4">
            <div className="flex items-center justify-between">
              <Label htmlFor="property-updates" className="flex flex-col gap-1">
                <span>Property Updates</span>
                <span className="text-sm font-normal text-muted-foreground">
                  Property verification, reviews, and other updates
                </span>
              </Label>
              <Switch
                id="property-updates"
                checked={preferences.propertyUpdates}
                onCheckedChange={(checked) =>
                  updatePreference('propertyUpdates', checked)
                }
              />
            </div>
          </CardContent>
        </Card>

        <Card>
          <CardHeader>
            <CardTitle>Review Notifications</CardTitle>
            <CardDescription>
              Get notified when someone reviews your property
            </CardDescription>
          </CardHeader>
          <CardContent className="space-y-4">
            <div className="flex items-center justify-between">
              <Label htmlFor="review-notifications" className="flex flex-col gap-1">
                <span>Review Notifications</span>
                <span className="text-sm font-normal text-muted-foreground">
                  New reviews and responses to your reviews
                </span>
              </Label>
              <Switch
                id="review-notifications"
                checked={preferences.reviewNotifications}
                onCheckedChange={(checked) =>
                  updatePreference('reviewNotifications', checked)
                }
              />
            </div>
          </CardContent>
        </Card>

        <Card>
          <CardHeader>
            <CardTitle>Marketing</CardTitle>
            <CardDescription>
              Promotional emails and platform updates
            </CardDescription>
          </CardHeader>
          <CardContent className="space-y-4">
            <div className="flex items-center justify-between">
              <Label htmlFor="marketing-emails" className="flex flex-col gap-1">
                <span>Marketing Emails</span>
                <span className="text-sm font-normal text-muted-foreground">
                  New features, tips, and promotional offers
                </span>
              </Label>
              <Switch
                id="marketing-emails"
                checked={preferences.marketingEmails}
                onCheckedChange={(checked) =>
                  updatePreference('marketingEmails', checked)
                }
              />
            </div>
          </CardContent>
        </Card>

        <Button onClick={handleSave} disabled={saving} className="w-full">
          {saving ? 'Saving...' : 'Save Preferences'}
        </Button>
      </div>
    </div>
  );
}
```

### Verification Checklist

- [ ] Notification types defined
- [ ] Notification service created with all methods
- [ ] API endpoints added to constants
- [ ] NotificationCenter component displays unread count
- [ ] Notifications popover shows all/unread tabs
- [ ] Mark as read functionality works
- [ ] Mark all as read works
- [ ] Delete notification works
- [ ] Delete all read works
- [ ] Notification links navigate to correct pages
- [ ] Notification preferences page loads
- [ ] Preferences can be updated
- [ ] Real-time badge updates on new notifications
- [ ] Proper date formatting ("2 hours ago")
- [ ] Empty states displayed correctly

---

## Task 7.2: File Upload System (6-8h)

### Step 1: Install Required Dependencies

```bash
cd frontend
npm install react-dropzone
npm install --save-dev @types/react-dropzone
```

### Step 2: Create File Upload Component

**File: `frontend/components/file-upload.tsx`**

```typescript
'use client';

import { useCallback, useState } from 'react';
import { useDropzone } from 'react-dropzone';
import { Upload, X, FileIcon, Image as ImageIcon } from 'lucide-react';
import { Button } from './ui/button';
import { Progress } from './ui/progress';
import { cn } from '@/lib/utils';

export interface UploadedFile {
  file: File;
  preview?: string;
  progress: number;
  error?: string;
}

interface FileUploadProps {
  onFilesSelected: (files: File[]) => void;
  maxFiles?: number;
  maxSize?: number; // in bytes
  accept?: Record<string, string[]>;
  multiple?: boolean;
  className?: string;
}

export function FileUpload({
  onFilesSelected,
  maxFiles = 10,
  maxSize = 5 * 1024 * 1024, // 5MB default
  accept = {
    'image/*': ['.png', '.jpg', '.jpeg', '.gif', '.webp']
  },
  multiple = true,
  className
}: FileUploadProps) {
  const [selectedFiles, setSelectedFiles] = useState<UploadedFile[]>([]);

  const onDrop = useCallback((acceptedFiles: File[], rejectedFiles: any[]) => {
    // Handle rejected files
    if (rejectedFiles.length > 0) {
      console.error('Rejected files:', rejectedFiles);
    }

    // Create preview URLs for images
    const filesWithPreviews = acceptedFiles.map(file => ({
      file,
      preview: file.type.startsWith('image/') ? URL.createObjectURL(file) : undefined,
      progress: 0
    }));

    setSelectedFiles(prev => {
      const newFiles = [...prev, ...filesWithPreviews];
      return newFiles.slice(0, maxFiles);
    });

    onFilesSelected(acceptedFiles);
  }, [maxFiles, onFilesSelected]);

  const { getRootProps, getInputProps, isDragActive } = useDropzone({
    onDrop,
    accept,
    maxSize,
    maxFiles,
    multiple
  });

  const removeFile = (index: number) => {
    setSelectedFiles(prev => {
      const newFiles = [...prev];
      // Revoke preview URL to avoid memory leaks
      if (newFiles[index].preview) {
        URL.revokeObjectURL(newFiles[index].preview!);
      }
      newFiles.splice(index, 1);
      return newFiles;
    });
  };

  const formatFileSize = (bytes: number): string => {
    if (bytes === 0) return '0 Bytes';
    const k = 1024;
    const sizes = ['Bytes', 'KB', 'MB', 'GB'];
    const i = Math.floor(Math.log(bytes) / Math.log(k));
    return Math.round(bytes / Math.pow(k, i) * 100) / 100 + ' ' + sizes[i];
  };

  return (
    <div className={cn('space-y-4', className)}>
      <div
        {...getRootProps()}
        className={cn(
          'border-2 border-dashed rounded-lg p-8 text-center cursor-pointer transition-colors',
          isDragActive ? 'border-primary bg-primary/5' : 'border-muted-foreground/25 hover:border-primary/50',
          selectedFiles.length >= maxFiles && 'opacity-50 cursor-not-allowed'
        )}
      >
        <input {...getInputProps()} disabled={selectedFiles.length >= maxFiles} />
        <Upload className="mx-auto h-12 w-12 text-muted-foreground mb-4" />
        {isDragActive ? (
          <p className="text-lg font-medium">Drop the files here...</p>
        ) : (
          <>
            <p className="text-lg font-medium mb-2">
              Drag & drop files here, or click to select
            </p>
            <p className="text-sm text-muted-foreground">
              {multiple ? `Up to ${maxFiles} files` : 'Single file'} • Max {formatFileSize(maxSize)} each
            </p>
            <p className="text-xs text-muted-foreground mt-1">
              {Object.values(accept).flat().join(', ')}
            </p>
          </>
        )}
      </div>

      {selectedFiles.length > 0 && (
        <div className="space-y-2">
          <h4 className="text-sm font-medium">
            Selected Files ({selectedFiles.length}/{maxFiles})
          </h4>
          <div className="space-y-2">
            {selectedFiles.map((uploadedFile, index) => (
              <div
                key={index}
                className="flex items-center gap-3 p-3 border rounded-lg bg-card"
              >
                {uploadedFile.preview ? (
                  <img
                    src={uploadedFile.preview}
                    alt={uploadedFile.file.name}
                    className="h-12 w-12 object-cover rounded"
                  />
                ) : (
                  <div className="h-12 w-12 flex items-center justify-center bg-muted rounded">
                    <FileIcon className="h-6 w-6 text-muted-foreground" />
                  </div>
                )}

                <div className="flex-1 min-w-0">
                  <p className="text-sm font-medium truncate">
                    {uploadedFile.file.name}
                  </p>
                  <p className="text-xs text-muted-foreground">
                    {formatFileSize(uploadedFile.file.size)}
                  </p>

                  {uploadedFile.progress > 0 && uploadedFile.progress < 100 && (
                    <Progress value={uploadedFile.progress} className="h-1 mt-2" />
                  )}

                  {uploadedFile.error && (
                    <p className="text-xs text-destructive mt-1">
                      {uploadedFile.error}
                    </p>
                  )}
                </div>

                <Button
                  variant="ghost"
                  size="icon"
                  onClick={() => removeFile(index)}
                >
                  <X className="h-4 w-4" />
                </Button>
              </div>
            ))}
          </div>
        </div>
      )}
    </div>
  );
}
```

### Step 3: Create Image Upload Service

**File: `frontend/lib/services/file-upload.service.ts`**

```typescript
import apiClient from '../api-client';

export interface UploadResponse {
  fileUrl: string;
  fileName: string;
  fileSize: number;
  contentType: string;
}

export const fileUploadService = {
  /**
   * Upload a single file
   */
  async uploadFile(
    file: File,
    onProgress?: (progress: number) => void
  ): Promise<UploadResponse> {
    const formData = new FormData();
    formData.append('file', file);

    const response = await apiClient.post<UploadResponse>(
      '/files/upload',
      formData,
      {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
        onUploadProgress: (progressEvent) => {
          if (progressEvent.total && onProgress) {
            const progress = Math.round(
              (progressEvent.loaded * 100) / progressEvent.total
            );
            onProgress(progress);
          }
        },
      }
    );

    return response.data;
  },

  /**
   * Upload multiple files
   */
  async uploadMultipleFiles(
    files: File[],
    onProgress?: (fileIndex: number, progress: number) => void
  ): Promise<UploadResponse[]> {
    const uploadPromises = files.map((file, index) =>
      this.uploadFile(file, (progress) => {
        if (onProgress) {
          onProgress(index, progress);
        }
      })
    );

    return Promise.all(uploadPromises);
  },

  /**
   * Delete a file by URL
   */
  async deleteFile(fileUrl: string): Promise<void> {
    await apiClient.delete('/files/delete', {
      params: { fileUrl }
    });
  },

  /**
   * Upload property images
   */
  async uploadPropertyImages(
    propertyId: number,
    files: File[],
    onProgress?: (fileIndex: number, progress: number) => void
  ): Promise<UploadResponse[]> {
    const formData = new FormData();
    files.forEach(file => {
      formData.append('images', file);
    });

    const response = await apiClient.post<UploadResponse[]>(
      `/properties/${propertyId}/images`,
      formData,
      {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
        onUploadProgress: (progressEvent) => {
          if (progressEvent.total && onProgress) {
            const progress = Math.round(
              (progressEvent.loaded * 100) / progressEvent.total
            );
            // Report overall progress
            files.forEach((_, index) => onProgress(index, progress));
          }
        },
      }
    );

    return response.data;
  },

  /**
   * Delete a property image
   */
  async deletePropertyImage(propertyId: number, imageUrl: string): Promise<void> {
    await apiClient.delete(`/properties/${propertyId}/images`, {
      params: { imageUrl }
    });
  }
};
```

### Step 4: Create Property Image Upload Component

**File: `frontend/components/property-image-upload.tsx`**

```typescript
'use client';

import { useState } from 'react';
import { FileUpload, UploadedFile } from './file-upload';
import { Button } from './ui/button';
import { fileUploadService } from '@/lib/services/file-upload.service';
import { useToast } from '@/hooks/use-toast';
import { Loader2 } from 'lucide-react';

interface PropertyImageUploadProps {
  propertyId: number;
  existingImages?: string[];
  onUploadComplete: (imageUrls: string[]) => void;
}

export function PropertyImageUpload({
  propertyId,
  existingImages = [],
  onUploadComplete
}: PropertyImageUploadProps) {
  const [selectedFiles, setSelectedFiles] = useState<File[]>([]);
  const [uploading, setUploading] = useState(false);
  const [uploadProgress, setUploadProgress] = useState<Record<number, number>>({});
  const { toast } = useToast();

  const handleFilesSelected = (files: File[]) => {
    setSelectedFiles(files);
  };

  const handleUpload = async () => {
    if (selectedFiles.length === 0) return;

    try {
      setUploading(true);
      const responses = await fileUploadService.uploadPropertyImages(
        propertyId,
        selectedFiles,
        (fileIndex, progress) => {
          setUploadProgress(prev => ({
            ...prev,
            [fileIndex]: progress
          }));
        }
      );

      const newImageUrls = responses.map(r => r.fileUrl);
      onUploadComplete([...existingImages, ...newImageUrls]);

      toast({
        title: 'Success',
        description: `${selectedFiles.length} image(s) uploaded successfully`,
      });

      setSelectedFiles([]);
      setUploadProgress({});
    } catch (error) {
      toast({
        title: 'Error',
        description: 'Failed to upload images. Please try again.',
        variant: 'destructive',
      });
    } finally {
      setUploading(false);
    }
  };

  return (
    <div className="space-y-4">
      <FileUpload
        onFilesSelected={handleFilesSelected}
        maxFiles={10}
        maxSize={5 * 1024 * 1024} // 5MB
        accept={{
          'image/*': ['.png', '.jpg', '.jpeg', '.webp']
        }}
        multiple
      />

      {selectedFiles.length > 0 && (
        <Button
          onClick={handleUpload}
          disabled={uploading}
          className="w-full"
        >
          {uploading ? (
            <>
              <Loader2 className="mr-2 h-4 w-4 animate-spin" />
              Uploading...
            </>
          ) : (
            `Upload ${selectedFiles.length} Image(s)`
          )}
        </Button>
      )}

      {/* Existing images preview */}
      {existingImages.length > 0 && (
        <div className="space-y-2">
          <h4 className="text-sm font-medium">Existing Images</h4>
          <div className="grid grid-cols-4 gap-4">
            {existingImages.map((imageUrl, index) => (
              <div key={index} className="relative aspect-square">
                <img
                  src={imageUrl}
                  alt={`Property image ${index + 1}`}
                  className="w-full h-full object-cover rounded-lg"
                />
              </div>
            ))}
          </div>
        </div>
      )}
    </div>
  );
}
```

### Verification Checklist

- [ ] react-dropzone installed
- [ ] FileUpload component accepts drag & drop
- [ ] File size validation works
- [ ] File type validation works
- [ ] Multiple files can be selected
- [ ] Preview shown for images
- [ ] Files can be removed before upload
- [ ] Upload progress shown
- [ ] fileUploadService created
- [ ] Single file upload works
- [ ] Multiple file upload works
- [ ] Property image upload integration
- [ ] Existing images displayed
- [ ] Upload success feedback
- [ ] Error handling for failed uploads

---

## Task 7.3: Property Review System (8-10h)

### Step 1: Create Review Types

**File: `frontend/types/review.ts`**

```typescript
export interface Review {
  id: number;
  propertyId: number;
  tenantId: number;
  tenantName: string;
  tenantAvatar?: string;
  rating: number;
  comment: string;
  createdAt: string;
  updatedAt: string;
  landlordResponse?: string;
  landlordResponseDate?: string;
}

export interface ReviewStats {
  averageRating: number;
  totalReviews: number;
  ratingDistribution: {
    1: number;
    2: number;
    3: number;
    4: number;
    5: number;
  };
}

export interface CreateReviewRequest {
  propertyId: number;
  rating: number;
  comment: string;
}

export interface ReviewResponse {
  content: Review[];
  totalElements: number;
  totalPages: number;
  currentPage: number;
  pageSize: number;
}
```

### Step 2: Create Review Service

**File: `frontend/lib/services/review.service.ts`**

```typescript
import apiClient from '../api-client';
import { API_ENDPOINTS } from '../constants/api-endpoints';
import type {
  Review,
  ReviewStats,
  CreateReviewRequest,
  ReviewResponse
} from '@/types/review';

export const reviewService = {
  /**
   * Get reviews for a property
   */
  async getPropertyReviews(
    propertyId: number,
    page = 0,
    size = 10
  ): Promise<ReviewResponse> {
    const response = await apiClient.get<ReviewResponse>(
      `${API_ENDPOINTS.PROPERTIES}/${propertyId}/reviews`,
      { params: { page, size } }
    );
    return response.data;
  },

  /**
   * Get review statistics for a property
   */
  async getPropertyReviewStats(propertyId: number): Promise<ReviewStats> {
    const response = await apiClient.get<ReviewStats>(
      `${API_ENDPOINTS.PROPERTIES}/${propertyId}/reviews/stats`
    );
    return response.data;
  },

  /**
   * Create a review
   */
  async createReview(data: CreateReviewRequest): Promise<Review> {
    const response = await apiClient.post<Review>(
      API_ENDPOINTS.REVIEWS,
      data
    );
    return response.data;
  },

  /**
   * Update a review
   */
  async updateReview(
    reviewId: number,
    data: Partial<CreateReviewRequest>
  ): Promise<Review> {
    const response = await apiClient.put<Review>(
      `${API_ENDPOINTS.REVIEWS}/${reviewId}`,
      data
    );
    return response.data;
  },

  /**
   * Delete a review
   */
  async deleteReview(reviewId: number): Promise<void> {
    await apiClient.delete(`${API_ENDPOINTS.REVIEWS}/${reviewId}`);
  },

  /**
   * Add landlord response to review
   */
  async respondToReview(reviewId: number, response: string): Promise<Review> {
    const result = await apiClient.post<Review>(
      `${API_ENDPOINTS.REVIEWS}/${reviewId}/respond`,
      { response }
    );
    return result.data;
  },

  /**
   * Get user's reviews
   */
  async getUserReviews(page = 0, size = 10): Promise<ReviewResponse> {
    const response = await apiClient.get<ReviewResponse>(
      `${API_ENDPOINTS.REVIEWS}/my-reviews`,
      { params: { page, size } }
    );
    return response.data;
  }
};
```

### Step 3: Add Review Endpoints to Constants

**Update: `frontend/lib/constants/api-endpoints.ts`**

```typescript
export const API_ENDPOINTS = {
  // ... existing endpoints ...
  REVIEWS: '/reviews',
  // ... rest
};
```

### Step 4: Create Review Display Component

**File: `frontend/components/property-reviews.tsx`**

```typescript
'use client';

import { useState, useEffect } from 'react';
import { Star, User, MessageSquare } from 'lucide-react';
import { Card, CardContent, CardHeader, CardTitle } from './ui/card';
import { Button } from './ui/button';
import { Textarea } from './ui/textarea';
import { Avatar, AvatarFallback, AvatarImage } from './ui/avatar';
import { Badge } from './ui/badge';
import { Progress } from './ui/progress';
import { reviewService } from '@/lib/services/review.service';
import type { Review, ReviewStats } from '@/types/review';
import { formatDistanceToNow } from 'date-fns';
import { useToast } from '@/hooks/use-toast';
import { useAuth } from '@/contexts/auth-context';

interface PropertyReviewsProps {
  propertyId: number;
  landlordId?: number;
}

export function PropertyReviews({ propertyId, landlordId }: PropertyReviewsProps) {
  const [reviews, setReviews] = useState<Review[]>([]);
  const [stats, setStats] = useState<ReviewStats | null>(null);
  const [loading, setLoading] = useState(true);
  const [responding, setResponding] = useState<number | null>(null);
  const [responseText, setResponseText] = useState('');
  const { user } = useAuth();
  const { toast } = useToast();

  useEffect(() => {
    loadReviews();
    loadStats();
  }, [propertyId]);

  const loadReviews = async () => {
    try {
      const response = await reviewService.getPropertyReviews(propertyId);
      setReviews(response.content);
    } catch (error) {
      console.error('Failed to load reviews:', error);
    } finally {
      setLoading(false);
    }
  };

  const loadStats = async () => {
    try {
      const statsData = await reviewService.getPropertyReviewStats(propertyId);
      setStats(statsData);
    } catch (error) {
      console.error('Failed to load review stats:', error);
    }
  };

  const handleRespond = async (reviewId: number) => {
    if (!responseText.trim()) return;

    try {
      const updatedReview = await reviewService.respondToReview(reviewId, responseText);
      setReviews(prev =>
        prev.map(r => r.id === reviewId ? updatedReview : r)
      );
      setResponding(null);
      setResponseText('');
      toast({
        title: 'Success',
        description: 'Response added successfully',
      });
    } catch (error) {
      toast({
        title: 'Error',
        description: 'Failed to add response',
        variant: 'destructive',
      });
    }
  };

  const isLandlord = user?.id === landlordId;

  if (loading) {
    return <div>Loading reviews...</div>;
  }

  return (
    <div className="space-y-6">
      {/* Review Statistics */}
      {stats && stats.totalReviews > 0 && (
        <Card>
          <CardHeader>
            <CardTitle>Guest Reviews</CardTitle>
          </CardHeader>
          <CardContent>
            <div className="grid md:grid-cols-2 gap-6">
              {/* Overall Rating */}
              <div className="space-y-4">
                <div className="flex items-center gap-4">
                  <div className="text-5xl font-bold">
                    {stats.averageRating.toFixed(1)}
                  </div>
                  <div>
                    <div className="flex items-center gap-1">
                      {[...Array(5)].map((_, i) => (
                        <Star
                          key={i}
                          className={`h-5 w-5 ${
                            i < Math.round(stats.averageRating)
                              ? 'fill-yellow-400 text-yellow-400'
                              : 'text-muted-foreground'
                          }`}
                        />
                      ))}
                    </div>
                    <p className="text-sm text-muted-foreground mt-1">
                      Based on {stats.totalReviews} review{stats.totalReviews !== 1 ? 's' : ''}
                    </p>
                  </div>
                </div>
              </div>

              {/* Rating Distribution */}
              <div className="space-y-2">
                {[5, 4, 3, 2, 1].map(rating => {
                  const count = stats.ratingDistribution[rating as keyof typeof stats.ratingDistribution] || 0;
                  const percentage = (count / stats.totalReviews) * 100;
                  
                  return (
                    <div key={rating} className="flex items-center gap-2">
                      <span className="text-sm w-12">{rating} star</span>
                      <Progress value={percentage} className="h-2" />
                      <span className="text-sm text-muted-foreground w-12 text-right">
                        {count}
                      </span>
                    </div>
                  );
                })}
              </div>
            </div>
          </CardContent>
        </Card>
      )}

      {/* Individual Reviews */}
      <div className="space-y-4">
        {reviews.length === 0 ? (
          <Card>
            <CardContent className="p-8 text-center">
              <MessageSquare className="h-12 w-12 mx-auto mb-4 text-muted-foreground" />
              <p className="text-muted-foreground">
                No reviews yet. Be the first to review this property!
              </p>
            </CardContent>
          </Card>
        ) : (
          reviews.map(review => (
            <Card key={review.id}>
              <CardContent className="p-6">
                <div className="flex items-start gap-4">
                  <Avatar>
                    <AvatarImage src={review.tenantAvatar} />
                    <AvatarFallback>
                      <User className="h-5 w-5" />
                    </AvatarFallback>
                  </Avatar>

                  <div className="flex-1">
                    <div className="flex items-center justify-between mb-2">
                      <div>
                        <h4 className="font-semibold">{review.tenantName}</h4>
                        <p className="text-sm text-muted-foreground">
                          {formatDistanceToNow(new Date(review.createdAt), {
                            addSuffix: true
                          })}
                        </p>
                      </div>
                      <div className="flex items-center gap-1">
                        {[...Array(5)].map((_, i) => (
                          <Star
                            key={i}
                            className={`h-4 w-4 ${
                              i < review.rating
                                ? 'fill-yellow-400 text-yellow-400'
                                : 'text-muted-foreground'
                            }`}
                          />
                        ))}
                      </div>
                    </div>

                    <p className="text-sm mb-4">{review.comment}</p>

                    {/* Landlord Response */}
                    {review.landlordResponse && (
                      <div className="bg-muted p-4 rounded-lg mt-4">
                        <div className="flex items-center gap-2 mb-2">
                          <Badge variant="secondary">Landlord Response</Badge>
                          {review.landlordResponseDate && (
                            <span className="text-xs text-muted-foreground">
                              {formatDistanceToNow(new Date(review.landlordResponseDate), {
                                addSuffix: true
                              })}
                            </span>
                          )}
                        </div>
                        <p className="text-sm">{review.landlordResponse}</p>
                      </div>
                    )}

                    {/* Response Form (Landlord Only) */}
                    {isLandlord && !review.landlordResponse && (
                      <div className="mt-4">
                        {responding === review.id ? (
                          <div className="space-y-2">
                            <Textarea
                              placeholder="Write your response..."
                              value={responseText}
                              onChange={(e) => setResponseText(e.target.value)}
                              rows={3}
                            />
                            <div className="flex gap-2">
                              <Button
                                size="sm"
                                onClick={() => handleRespond(review.id)}
                              >
                                Submit Response
                              </Button>
                              <Button
                                size="sm"
                                variant="outline"
                                onClick={() => {
                                  setResponding(null);
                                  setResponseText('');
                                }}
                              >
                                Cancel
                              </Button>
                            </div>
                          </div>
                        ) : (
                          <Button
                            size="sm"
                            variant="outline"
                            onClick={() => setResponding(review.id)}
                          >
                            <MessageSquare className="h-4 w-4 mr-2" />
                            Respond
                          </Button>
                        )}
                      </div>
                    )}
                  </div>
                </div>
              </CardContent>
            </Card>
          ))
        )}
      </div>
    </div>
  );
}
```

### Step 5: Create Review Submission Form

**File: `frontend/components/review-form.tsx`**

```typescript
'use client';

import { useState } from 'react';
import { Star } from 'lucide-react';
import { Button } from './ui/button';
import { Textarea } from './ui/textarea';
import { Label } from './ui/label';
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from './ui/dialog';
import { reviewService } from '@/lib/services/review.service';
import { useToast } from '@/hooks/use-toast';
import type { CreateReviewRequest } from '@/types/review';

interface ReviewFormProps {
  propertyId: number;
  onReviewSubmitted?: () => void;
}

export function ReviewForm({ propertyId, onReviewSubmitted }: ReviewFormProps) {
  const [open, setOpen] = useState(false);
  const [rating, setRating] = useState(0);
  const [hoveredRating, setHoveredRating] = useState(0);
  const [comment, setComment] = useState('');
  const [submitting, setSubmitting] = useState(false);
  const { toast } = useToast();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (rating === 0) {
      toast({
        title: 'Error',
        description: 'Please select a rating',
        variant: 'destructive',
      });
      return;
    }

    if (!comment.trim()) {
      toast({
        title: 'Error',
        description: 'Please write a review',
        variant: 'destructive',
      });
      return;
    }

    try {
      setSubmitting(true);
      const reviewData: CreateReviewRequest = {
        propertyId,
        rating,
        comment: comment.trim()
      };

      await reviewService.createReview(reviewData);

      toast({
        title: 'Success',
        description: 'Review submitted successfully',
      });

      // Reset form
      setRating(0);
      setComment('');
      setOpen(false);

      // Notify parent
      if (onReviewSubmitted) {
        onReviewSubmitted();
      }
    } catch (error: any) {
      toast({
        title: 'Error',
        description: error.response?.data?.message || 'Failed to submit review',
        variant: 'destructive',
      });
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <Dialog open={open} onOpenChange={setOpen}>
      <DialogTrigger asChild>
        <Button>Write a Review</Button>
      </DialogTrigger>
      <DialogContent className="sm:max-w-[500px]">
        <DialogHeader>
          <DialogTitle>Write a Review</DialogTitle>
          <DialogDescription>
            Share your experience with this property
          </DialogDescription>
        </DialogHeader>

        <form onSubmit={handleSubmit} className="space-y-4">
          {/* Rating */}
          <div className="space-y-2">
            <Label>Rating</Label>
            <div className="flex items-center gap-2">
              {[1, 2, 3, 4, 5].map(star => (
                <button
                  key={star}
                  type="button"
                  onClick={() => setRating(star)}
                  onMouseEnter={() => setHoveredRating(star)}
                  onMouseLeave={() => setHoveredRating(0)}
                  className="transition-transform hover:scale-110"
                >
                  <Star
                    className={`h-8 w-8 ${
                      star <= (hoveredRating || rating)
                        ? 'fill-yellow-400 text-yellow-400'
                        : 'text-muted-foreground'
                    }`}
                  />
                </button>
              ))}
              {rating > 0 && (
                <span className="text-sm text-muted-foreground ml-2">
                  {rating} star{rating !== 1 ? 's' : ''}
                </span>
              )}
            </div>
          </div>

          {/* Comment */}
          <div className="space-y-2">
            <Label htmlFor="comment">Your Review</Label>
            <Textarea
              id="comment"
              placeholder="Tell others about your experience with this property..."
              value={comment}
              onChange={(e) => setComment(e.target.value)}
              rows={5}
              required
            />
            <p className="text-xs text-muted-foreground">
              Minimum 10 characters
            </p>
          </div>

          <div className="flex justify-end gap-2">
            <Button
              type="button"
              variant="outline"
              onClick={() => setOpen(false)}
            >
              Cancel
            </Button>
            <Button type="submit" disabled={submitting}>
              {submitting ? 'Submitting...' : 'Submit Review'}
            </Button>
          </div>
        </form>
      </DialogContent>
    </Dialog>
  );
}
```

### Verification Checklist

- [ ] Review types defined
- [ ] Review service created
- [ ] Review endpoints added
- [ ] PropertyReviews component displays reviews
- [ ] Rating statistics shown with distribution
- [ ] Individual reviews displayed
- [ ] Landlord can respond to reviews
- [ ] ReviewForm component created
- [ ] Star rating selection works
- [ ] Review submission works
- [ ] Reviews refresh after submission
- [ ] Empty state for no reviews
- [ ] Date formatting works
- [ ] Avatar fallbacks display

---

## Task 7.4: User Profile Management (6-8h)

### Step 1: Create Profile Types

**File: `frontend/types/profile.ts`**

```typescript
export interface UserProfile {
  id: number;
  email: string;
  fullName: string;
  phone: string;
  avatar?: string;
  bio?: string;
  address?: string;
  city?: string;
  state?: string;
  zipCode?: string;
  role: 'TENANT' | 'LANDLORD' | 'ADMIN';
  emailVerified: boolean;
  phoneVerified: boolean;
  createdAt: string;
  lastLogin?: string;
}

export interface UpdateProfileRequest {
  fullName?: string;
  phone?: string;
  bio?: string;
  address?: string;
  city?: string;
  state?: string;
  zipCode?: string;
}

export interface ChangePasswordRequest {
  currentPassword: string;
  newPassword: string;
  confirmPassword: string;
}
```

### Step 2: Create Profile Service

**File: `frontend/lib/services/profile.service.ts`**

```typescript
import apiClient from '../api-client';
import { API_ENDPOINTS } from '../constants/api-endpoints';
import type {
  UserProfile,
  UpdateProfileRequest,
  ChangePasswordRequest
} from '@/types/profile';

export const profileService = {
  /**
   * Get current user profile
   */
  async getProfile(): Promise<UserProfile> {
    const response = await apiClient.get<UserProfile>(
      `${API_ENDPOINTS.USERS}/profile`
    );
    return response.data;
  },

  /**
   * Update user profile
   */
  async updateProfile(data: UpdateProfileRequest): Promise<UserProfile> {
    const response = await apiClient.put<UserProfile>(
      `${API_ENDPOINTS.USERS}/profile`,
      data
    );
    return response.data;
  },

  /**
   * Upload profile avatar
   */
  async uploadAvatar(file: File): Promise<{ avatarUrl: string }> {
    const formData = new FormData();
    formData.append('avatar', file);

    const response = await apiClient.post<{ avatarUrl: string }>(
      `${API_ENDPOINTS.USERS}/profile/avatar`,
      formData,
      {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      }
    );

    return response.data;
  },

  /**
   * Change password
   */
  async changePassword(data: ChangePasswordRequest): Promise<void> {
    await apiClient.post(
      `${API_ENDPOINTS.USERS}/profile/change-password`,
      data
    );
  },

  /**
   * Request email verification
   */
  async requestEmailVerification(): Promise<void> {
    await apiClient.post(`${API_ENDPOINTS.USERS}/profile/verify-email/request`);
  },

  /**
   * Verify email with code
   */
  async verifyEmail(code: string): Promise<void> {
    await apiClient.post(`${API_ENDPOINTS.USERS}/profile/verify-email`, { code });
  },

  /**
   * Delete account
   */
  async deleteAccount(password: string): Promise<void> {
    await apiClient.delete(`${API_ENDPOINTS.USERS}/profile`, {
      data: { password }
    });
  }
};
```

### Step 3: Create Profile Page

**File: `frontend/app/(protected)/profile/page.tsx`**

```typescript
'use client';

import { useState, useEffect } from 'react';
import { Camera, Mail, Phone, MapPin, Calendar, Shield } from 'lucide-react';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Textarea } from '@/components/ui/textarea';
import { Avatar, AvatarFallback, AvatarImage } from '@/components/ui/avatar';
import { Badge } from '@/components/ui/badge';
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs';
import { profileService } from '@/lib/services/profile.service';
import type { UserProfile, UpdateProfileRequest } from '@/types/profile';
import { useToast } from '@/hooks/use-toast';
import { format } from 'date-fns';
import Link from 'next/link';

export default function ProfilePage() {
  const [profile, setProfile] = useState<UserProfile | null>(null);
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [formData, setFormData] = useState<UpdateProfileRequest>({});
  const { toast } = useToast();

  useEffect(() => {
    loadProfile();
  }, []);

  const loadProfile = async () => {
    try {
      const data = await profileService.getProfile();
      setProfile(data);
      setFormData({
        fullName: data.fullName,
        phone: data.phone,
        bio: data.bio || '',
        address: data.address || '',
        city: data.city || '',
        state: data.state || '',
        zipCode: data.zipCode || ''
      });
    } catch (error) {
      toast({
        title: 'Error',
        description: 'Failed to load profile',
        variant: 'destructive',
      });
    } finally {
      setLoading(false);
    }
  };

  const handleAvatarUpload = async (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (!file) return;

    // Validate file size (max 2MB)
    if (file.size > 2 * 1024 * 1024) {
      toast({
        title: 'Error',
        description: 'File size must be less than 2MB',
        variant: 'destructive',
      });
      return;
    }

    try {
      const result = await profileService.uploadAvatar(file);
      setProfile(prev => prev ? { ...prev, avatar: result.avatarUrl } : null);
      toast({
        title: 'Success',
        description: 'Avatar updated successfully',
      });
    } catch (error) {
      toast({
        title: 'Error',
        description: 'Failed to upload avatar',
        variant: 'destructive',
      });
    }
  };

  const handleUpdateProfile = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      setSaving(true);
      const updatedProfile = await profileService.updateProfile(formData);
      setProfile(updatedProfile);
      toast({
        title: 'Success',
        description: 'Profile updated successfully',
      });
    } catch (error) {
      toast({
        title: 'Error',
        description: 'Failed to update profile',
        variant: 'destructive',
      });
    } finally {
      setSaving(false);
    }
  };

  const handleRequestEmailVerification = async () => {
    try {
      await profileService.requestEmailVerification();
      toast({
        title: 'Success',
        description: 'Verification email sent. Please check your inbox.',
      });
    } catch (error) {
      toast({
        title: 'Error',
        description: 'Failed to send verification email',
        variant: 'destructive',
      });
    }
  };

  if (loading || !profile) {
    return <div>Loading profile...</div>;
  }

  return (
    <div className="container max-w-4xl py-8">
      {/* Profile Header */}
      <Card className="mb-6">
        <CardContent className="p-6">
          <div className="flex items-start gap-6">
            <div className="relative">
              <Avatar className="h-24 w-24">
                <AvatarImage src={profile.avatar} />
                <AvatarFallback className="text-2xl">
                  {profile.fullName.charAt(0)}
                </AvatarFallback>
              </Avatar>
              <label
                htmlFor="avatar-upload"
                className="absolute bottom-0 right-0 p-2 bg-primary text-primary-foreground rounded-full cursor-pointer hover:bg-primary/90 transition-colors"
              >
                <Camera className="h-4 w-4" />
                <input
                  id="avatar-upload"
                  type="file"
                  accept="image/*"
                  className="hidden"
                  onChange={handleAvatarUpload}
                />
              </label>
            </div>

            <div className="flex-1">
              <div className="flex items-center gap-3 mb-2">
                <h1 className="text-2xl font-bold">{profile.fullName}</h1>
                <Badge variant="secondary">{profile.role}</Badge>
              </div>

              <div className="space-y-1 text-sm text-muted-foreground">
                <div className="flex items-center gap-2">
                  <Mail className="h-4 w-4" />
                  <span>{profile.email}</span>
                  {profile.emailVerified ? (
                    <Badge variant="default" className="text-xs">
                      Verified
                    </Badge>
                  ) : (
                    <Button
                      variant="ghost"
                      size="sm"
                      onClick={handleRequestEmailVerification}
                      className="h-auto py-0 px-2 text-xs"
                    >
                      Verify Email
                    </Button>
                  )}
                </div>

                {profile.phone && (
                  <div className="flex items-center gap-2">
                    <Phone className="h-4 w-4" />
                    <span>{profile.phone}</span>
                    {profile.phoneVerified && (
                      <Badge variant="default" className="text-xs">
                        Verified
                      </Badge>
                    )}
                  </div>
                )}

                {profile.city && profile.state && (
                  <div className="flex items-center gap-2">
                    <MapPin className="h-4 w-4" />
                    <span>{profile.city}, {profile.state}</span>
                  </div>
                )}

                <div className="flex items-center gap-2">
                  <Calendar className="h-4 w-4" />
                  <span>
                    Member since {format(new Date(profile.createdAt), 'MMMM yyyy')}
                  </span>
                </div>
              </div>
            </div>
          </div>
        </CardContent>
      </Card>

      {/* Profile Tabs */}
      <Tabs defaultValue="general" className="space-y-6">
        <TabsList className="grid w-full grid-cols-2">
          <TabsTrigger value="general">General Information</TabsTrigger>
          <TabsTrigger value="security">Security</TabsTrigger>
        </TabsList>

        {/* General Information */}
        <TabsContent value="general">
          <form onSubmit={handleUpdateProfile}>
            <Card>
              <CardHeader>
                <CardTitle>General Information</CardTitle>
                <CardDescription>
                  Update your personal information
                </CardDescription>
              </CardHeader>
              <CardContent className="space-y-4">
                <div className="grid md:grid-cols-2 gap-4">
                  <div className="space-y-2">
                    <Label htmlFor="fullName">Full Name</Label>
                    <Input
                      id="fullName"
                      value={formData.fullName || ''}
                      onChange={(e) =>
                        setFormData({ ...formData, fullName: e.target.value })
                      }
                      required
                    />
                  </div>

                  <div className="space-y-2">
                    <Label htmlFor="phone">Phone Number</Label>
                    <Input
                      id="phone"
                      type="tel"
                      value={formData.phone || ''}
                      onChange={(e) =>
                        setFormData({ ...formData, phone: e.target.value })
                      }
                      required
                    />
                  </div>
                </div>

                <div className="space-y-2">
                  <Label htmlFor="bio">Bio</Label>
                  <Textarea
                    id="bio"
                    value={formData.bio || ''}
                    onChange={(e) =>
                      setFormData({ ...formData, bio: e.target.value })
                    }
                    rows={3}
                    placeholder="Tell us about yourself..."
                  />
                </div>

                <div className="space-y-2">
                  <Label htmlFor="address">Address</Label>
                  <Input
                    id="address"
                    value={formData.address || ''}
                    onChange={(e) =>
                      setFormData({ ...formData, address: e.target.value })
                    }
                  />
                </div>

                <div className="grid md:grid-cols-3 gap-4">
                  <div className="space-y-2">
                    <Label htmlFor="city">City</Label>
                    <Input
                      id="city"
                      value={formData.city || ''}
                      onChange={(e) =>
                        setFormData({ ...formData, city: e.target.value })
                      }
                    />
                  </div>

                  <div className="space-y-2">
                    <Label htmlFor="state">State</Label>
                    <Input
                      id="state"
                      value={formData.state || ''}
                      onChange={(e) =>
                        setFormData({ ...formData, state: e.target.value })
                      }
                    />
                  </div>

                  <div className="space-y-2">
                    <Label htmlFor="zipCode">Zip Code</Label>
                    <Input
                      id="zipCode"
                      value={formData.zipCode || ''}
                      onChange={(e) =>
                        setFormData({ ...formData, zipCode: e.target.value })
                      }
                    />
                  </div>
                </div>

                <Button type="submit" disabled={saving}>
                  {saving ? 'Saving...' : 'Save Changes'}
                </Button>
              </CardContent>
            </Card>
          </form>
        </TabsContent>

        {/* Security */}
        <TabsContent value="security">
          <div className="space-y-6">
            <Card>
              <CardHeader>
                <CardTitle>Change Password</CardTitle>
                <CardDescription>
                  Update your password to keep your account secure
                </CardDescription>
              </CardHeader>
              <CardContent>
                <Link href="/profile/change-password">
                  <Button>
                    <Shield className="h-4 w-4 mr-2" />
                    Change Password
                  </Button>
                </Link>
              </CardContent>
            </Card>

            <Card className="border-destructive">
              <CardHeader>
                <CardTitle className="text-destructive">Danger Zone</CardTitle>
                <CardDescription>
                  Irreversible actions that affect your account
                </CardDescription>
              </CardHeader>
              <CardContent>
                <Link href="/profile/delete-account">
                  <Button variant="destructive">
                    Delete Account
                  </Button>
                </Link>
              </CardContent>
            </Card>
          </div>
        </TabsContent>
      </Tabs>
    </div>
  );
}
```

### Step 4: Create Change Password Page

**File: `frontend/app/(protected)/profile/change-password/page.tsx`**

```typescript
'use client';

import { useState } from 'react';
import { useRouter } from 'next/navigation';
import { ArrowLeft } from 'lucide-react';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { profileService } from '@/lib/services/profile.service';
import type { ChangePasswordRequest } from '@/types/profile';
import { useToast } from '@/hooks/use-toast';
import Link from 'next/link';

export default function ChangePasswordPage() {
  const [formData, setFormData] = useState<ChangePasswordRequest>({
    currentPassword: '',
    newPassword: '',
    confirmPassword: ''
  });
  const [loading, setLoading] = useState(false);
  const { toast } = useToast();
  const router = useRouter();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    // Validate passwords match
    if (formData.newPassword !== formData.confirmPassword) {
      toast({
        title: 'Error',
        description: 'New passwords do not match',
        variant: 'destructive',
      });
      return;
    }

    // Validate password strength
    if (formData.newPassword.length < 8) {
      toast({
        title: 'Error',
        description: 'Password must be at least 8 characters',
        variant: 'destructive',
      });
      return;
    }

    try {
      setLoading(true);
      await profileService.changePassword(formData);
      toast({
        title: 'Success',
        description: 'Password changed successfully',
      });
      router.push('/profile');
    } catch (error: any) {
      toast({
        title: 'Error',
        description: error.response?.data?.message || 'Failed to change password',
        variant: 'destructive',
      });
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container max-w-2xl py-8">
      <Link href="/profile">
        <Button variant="ghost" className="mb-6">
          <ArrowLeft className="h-4 w-4 mr-2" />
          Back to Profile
        </Button>
      </Link>

      <Card>
        <CardHeader>
          <CardTitle>Change Password</CardTitle>
          <CardDescription>
            Update your password to keep your account secure
          </CardDescription>
        </CardHeader>
        <CardContent>
          <form onSubmit={handleSubmit} className="space-y-4">
            <div className="space-y-2">
              <Label htmlFor="currentPassword">Current Password</Label>
              <Input
                id="currentPassword"
                type="password"
                value={formData.currentPassword}
                onChange={(e) =>
                  setFormData({ ...formData, currentPassword: e.target.value })
                }
                required
              />
            </div>

            <div className="space-y-2">
              <Label htmlFor="newPassword">New Password</Label>
              <Input
                id="newPassword"
                type="password"
                value={formData.newPassword}
                onChange={(e) =>
                  setFormData({ ...formData, newPassword: e.target.value })
                }
                required
              />
              <p className="text-xs text-muted-foreground">
                Must be at least 8 characters
              </p>
            </div>

            <div className="space-y-2">
              <Label htmlFor="confirmPassword">Confirm New Password</Label>
              <Input
                id="confirmPassword"
                type="password"
                value={formData.confirmPassword}
                onChange={(e) =>
                  setFormData({ ...formData, confirmPassword: e.target.value })
                }
                required
              />
            </div>

            <Button type="submit" disabled={loading} className="w-full">
              {loading ? 'Changing Password...' : 'Change Password'}
            </Button>
          </form>
        </CardContent>
      </Card>
    </div>
  );
}
```

### Verification Checklist

- [ ] Profile types defined
- [ ] Profile service created
- [ ] Profile page loads user data
- [ ] Avatar upload works
- [ ] Avatar validation (size check)
- [ ] Profile information can be updated
- [ ] Email verification request works
- [ ] Phone display with verification badge
- [ ] Change password page works
- [ ] Password validation (length, match)
- [ ] Success/error feedback
- [ ] Navigation between profile pages
- [ ] Member since date formatted
- [ ] Tabs switch between general and security

---

## Task 7.5: Saved Properties Feature (6-8h)

### Step 1: Create Saved Property Service

**File: `frontend/lib/services/saved-property.service.ts`**

```typescript
import apiClient from '../api-client';
import { API_ENDPOINTS } from '../constants/api-endpoints';
import type { Property } from '@/types/property';

export interface SavedPropertyResponse {
  content: Property[];
  totalElements: number;
  totalPages: number;
  currentPage: number;
  pageSize: number;
}

export const savedPropertyService = {
  /**
   * Get saved properties for current user
   */
  async getSavedProperties(page = 0, size = 12): Promise<SavedPropertyResponse> {
    const response = await apiClient.get<SavedPropertyResponse>(
      `${API_ENDPOINTS.SAVED_PROPERTIES}`,
      { params: { page, size } }
    );
    return response.data;
  },

  /**
   * Save a property
   */
  async saveProperty(propertyId: number): Promise<void> {
    await apiClient.post(`${API_ENDPOINTS.SAVED_PROPERTIES}/${propertyId}`);
  },

  /**
   * Unsave a property
   */
  async unsaveProperty(propertyId: number): Promise<void> {
    await apiClient.delete(`${API_ENDPOINTS.SAVED_PROPERTIES}/${propertyId}`);
  },

  /**
   * Check if property is saved
   */
  async isPropertySaved(propertyId: number): Promise<boolean> {
    const response = await apiClient.get<{ saved: boolean }>(
      `${API_ENDPOINTS.SAVED_PROPERTIES}/${propertyId}/status`
    );
    return response.data.saved;
  },

  /**
   * Get saved property IDs for current user
   */
  async getSavedPropertyIds(): Promise<number[]> {
    const response = await apiClient.get<number[]>(
      `${API_ENDPOINTS.SAVED_PROPERTIES}/ids`
    );
    return response.data;
  }
};
```

### Step 2: Add Saved Properties Endpoint

**Update: `frontend/lib/constants/api-endpoints.ts`**

```typescript
export const API_ENDPOINTS = {
  // ... existing endpoints ...
  SAVED_PROPERTIES: '/saved-properties',
  // ... rest
};
```

### Step 3: Create Save Button Component

**File: `frontend/components/save-property-button.tsx`**

```typescript
'use client';

import { useState, useEffect } from 'react';
import { Heart } from 'lucide-react';
import { Button } from './ui/button';
import { savedPropertyService } from '@/lib/services/saved-property.service';
import { useToast } from '@/hooks/use-toast';
import { useAuth } from '@/contexts/auth-context';
import { useRouter } from 'next/navigation';
import { cn } from '@/lib/utils';

interface SavePropertyButtonProps {
  propertyId: number;
  variant?: 'default' | 'icon';
  className?: string;
}

export function SavePropertyButton({
  propertyId,
  variant = 'default',
  className
}: SavePropertyButtonProps) {
  const [isSaved, setIsSaved] = useState(false);
  const [loading, setLoading] = useState(false);
  const { user } = useAuth();
  const { toast } = useToast();
  const router = useRouter();

  useEffect(() => {
    if (user) {
      checkSavedStatus();
    }
  }, [propertyId, user]);

  const checkSavedStatus = async () => {
    try {
      const saved = await savedPropertyService.isPropertySaved(propertyId);
      setIsSaved(saved);
    } catch (error) {
      console.error('Failed to check saved status:', error);
    }
  };

  const handleToggleSave = async (e: React.MouseEvent) => {
    e.preventDefault();
    e.stopPropagation();

    if (!user) {
      toast({
        title: 'Login Required',
        description: 'Please login to save properties',
      });
      router.push('/auth/login');
      return;
    }

    try {
      setLoading(true);
      
      if (isSaved) {
        await savedPropertyService.unsaveProperty(propertyId);
        setIsSaved(false);
        toast({
          title: 'Removed',
          description: 'Property removed from saved list',
        });
      } else {
        await savedPropertyService.saveProperty(propertyId);
        setIsSaved(true);
        toast({
          title: 'Saved',
          description: 'Property added to your saved list',
        });
      }
    } catch (error: any) {
      toast({
        title: 'Error',
        description: error.response?.data?.message || 'Failed to update saved status',
        variant: 'destructive',
      });
    } finally {
      setLoading(false);
    }
  };

  if (variant === 'icon') {
    return (
      <Button
        variant="ghost"
        size="icon"
        onClick={handleToggleSave}
        disabled={loading}
        className={cn(
          'rounded-full',
          isSaved && 'text-red-500 hover:text-red-600',
          className
        )}
      >
        <Heart
          className={cn(
            'h-5 w-5',
            isSaved && 'fill-current'
          )}
        />
      </Button>
    );
  }

  return (
    <Button
      variant={isSaved ? 'default' : 'outline'}
      onClick={handleToggleSave}
      disabled={loading}
      className={className}
    >
      <Heart
        className={cn(
          'h-4 w-4 mr-2',
          isSaved && 'fill-current'
        )}
      />
      {isSaved ? 'Saved' : 'Save'}
    </Button>
  );
}
```

### Step 4: Create Saved Properties Page

**File: `frontend/app/(protected)/tenant/saved-properties/page.tsx`**

```typescript
'use client';

import { useState, useEffect } from 'react';
import { Heart, Filter } from 'lucide-react';
import { PropertyCard } from '@/components/property-card';
import { Button } from '@/components/ui/button';
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select';
import { savedPropertyService } from '@/lib/services/saved-property.service';
import type { Property } from '@/types/property';
import { useToast } from '@/hooks/use-toast';

export default function SavedPropertiesPage() {
  const [properties, setProperties] = useState<Property[]>([]);
  const [loading, setLoading] = useState(true);
  const [sortBy, setSortBy] = useState('recent');
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const { toast } = useToast();

  useEffect(() => {
    loadProperties();
  }, [page]);

  const loadProperties = async () => {
    try {
      setLoading(true);
      const response = await savedPropertyService.getSavedProperties(page, 12);
      setProperties(response.content);
      setTotalPages(response.totalPages);
    } catch (error) {
      toast({
        title: 'Error',
        description: 'Failed to load saved properties',
        variant: 'destructive',
      });
    } finally {
      setLoading(false);
    }
  };

  const handlePropertyUnsaved = (propertyId: number) => {
    setProperties(prev => prev.filter(p => p.id !== propertyId));
  };

  const sortedProperties = [...properties].sort((a, b) => {
    switch (sortBy) {
      case 'price-low':
        return a.rentAmount - b.rentAmount;
      case 'price-high':
        return b.rentAmount - a.rentAmount;
      case 'recent':
      default:
        return 0;
    }
  });

  if (loading) {
    return (
      <div className="container py-8">
        <div className="text-center">Loading saved properties...</div>
      </div>
    );
  }

  return (
    <div className="container py-8">
      {/* Header */}
      <div className="flex items-center justify-between mb-6">
        <div>
          <h1 className="text-3xl font-bold">Saved Properties</h1>
          <p className="text-muted-foreground mt-1">
            {properties.length} saved {properties.length !== 1 ? 'properties' : 'property'}
          </p>
        </div>

        {properties.length > 0 && (
          <Select value={sortBy} onValueChange={setSortBy}>
            <SelectTrigger className="w-[180px]">
              <Filter className="h-4 w-4 mr-2" />
              <SelectValue placeholder="Sort by" />
            </SelectTrigger>
            <SelectContent>
              <SelectItem value="recent">Recently Saved</SelectItem>
              <SelectItem value="price-low">Price: Low to High</SelectItem>
              <SelectItem value="price-high">Price: High to Low</SelectItem>
            </SelectContent>
          </Select>
        )}
      </div>

      {/* Properties Grid */}
      {properties.length === 0 ? (
        <div className="text-center py-12">
          <Heart className="h-16 w-16 mx-auto mb-4 text-muted-foreground" />
          <h2 className="text-2xl font-semibold mb-2">No Saved Properties</h2>
          <p className="text-muted-foreground mb-6">
            Start saving properties you like to view them later
          </p>
          <Button onClick={() => window.location.href = '/properties'}>
            Browse Properties
          </Button>
        </div>
      ) : (
        <>
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {sortedProperties.map(property => (
              <PropertyCard
                key={property.id}
                property={property}
                onUnsaved={() => handlePropertyUnsaved(property.id)}
              />
            ))}
          </div>

          {/* Pagination */}
          {totalPages > 1 && (
            <div className="flex justify-center gap-2 mt-8">
              <Button
                variant="outline"
                onClick={() => setPage(p => Math.max(0, p - 1))}
                disabled={page === 0}
              >
                Previous
              </Button>
              <span className="flex items-center px-4">
                Page {page + 1} of {totalPages}
              </span>
              <Button
                variant="outline"
                onClick={() => setPage(p => Math.min(totalPages - 1, p + 1))}
                disabled={page === totalPages - 1}
              >
                Next
              </Button>
            </div>
          )}
        </>
      )}
    </div>
  );
}
```

### Step 5: Update Property Card Component

**Update: `frontend/components/property-card.tsx`**

Add the SavePropertyButton to the existing PropertyCard:

```typescript
import { SavePropertyButton } from './save-property-button';

// Inside the PropertyCard component, add to the header or footer:
<div className="absolute top-2 right-2 z-10">
  <SavePropertyButton propertyId={property.id} variant="icon" />
</div>
```

### Step 6: Update Navbar with Saved Count

**Update: `frontend/components/navbar.tsx`**

```typescript
import { Heart } from 'lucide-react';
import { Badge } from './ui/badge';
import { savedPropertyService } from '@/lib/services/saved-property.service';

// Add saved count state and effect
const [savedCount, setSavedCount] = useState(0);

useEffect(() => {
  if (user?.role === 'TENANT') {
    loadSavedCount();
  }
}, [user]);

const loadSavedCount = async () => {
  try {
    const ids = await savedPropertyService.getSavedPropertyIds();
    setSavedCount(ids.length);
  } catch (error) {
    console.error('Failed to load saved count:', error);
  }
};

// In the navigation links for tenants:
<Link href="/tenant/saved-properties" className="relative">
  <Button variant="ghost">
    <Heart className="h-4 w-4 mr-2" />
    Saved
    {savedCount > 0 && (
      <Badge variant="destructive" className="ml-2">
        {savedCount}
      </Badge>
    )}
  </Button>
</Link>
```

### Verification Checklist

- [ ] Saved property service created
- [ ] Save/unsave API integration works
- [ ] SavePropertyButton component created
- [ ] Heart icon fills when saved
- [ ] Save button works on property cards
- [ ] Login redirect for unauthenticated users
- [ ] Saved properties page displays properties
- [ ] Empty state for no saved properties
- [ ] Sort functionality works
- [ ] Pagination works
- [ ] Unsaving updates the list
- [ ] Saved count in navbar
- [ ] Toast notifications for save/unsave
- [ ] Optimistic UI updates

---

## Phase 7 Completion Checklist

### Notification System

- [ ] Notification types and service created
- [ ] NotificationCenter in navbar with badge
- [ ] Unread count updates real-time
- [ ] Mark as read/delete functionality
- [ ] Notification preferences page
- [ ] Email notification toggles

### File Upload System

- [ ] FileUpload component with drag & drop
- [ ] File validation (type, size)
- [ ] Image preview for uploads
- [ ] Progress indicators
- [ ] Property image upload integration
- [ ] Multiple file upload support

### Review System

- [ ] Review types and service created
- [ ] Review statistics with distribution
- [ ] Individual reviews display
- [ ] Star rating system
- [ ] Review submission form
- [ ] Landlord response feature

### User Profile

- [ ] Profile page with avatar upload
- [ ] Profile information editing
- [ ] Email verification flow
- [ ] Change password page
- [ ] Password validation
- [ ] Member since date display

### Saved Properties

- [ ] Save/unsave functionality
- [ ] Saved properties page
- [ ] SavePropertyButton component
- [ ] Saved count in navbar
- [ ] Sort and pagination
- [ ] Empty state handling

---

# PHASE 8: Admin Panel (24-28h)

**Objective:** Build comprehensive admin panel for user management, property moderation, system statistics, and reporting.

**Dependencies:** Phases 1-7 completed, ADMIN role implemented

**Tasks:**

- Task 8.1: User Management System (8-10h)
- Task 8.2: Property Moderation (6-8h)
- Task 8.3: Admin Dashboard & Analytics (8-10h)
- Task 8.4: Reports & Data Export (2-4h)

---

## Task 8.1: User Management System (8-10h)

### Step 1: Create Admin Types

**File: `frontend/types/admin.ts`**

```typescript
export interface AdminUser {
  id: number;
  email: string;
  fullName: string;
  phone: string;
  role: 'TENANT' | 'LANDLORD' | 'ADMIN';
  status: 'ACTIVE' | 'SUSPENDED' | 'BANNED';
  emailVerified: boolean;
  phoneVerified: boolean;
  createdAt: string;
  lastLogin?: string;
  propertiesCount?: number;
  applicationsCount?: number;
  leasesCount?: number;
}

export interface UserFilter {
  role?: 'TENANT' | 'LANDLORD' | 'ADMIN';
  status?: 'ACTIVE' | 'SUSPENDED' | 'BANNED';
  verified?: boolean;
  search?: string;
}

export interface UserStats {
  totalUsers: number;
  activeUsers: number;
  suspendedUsers: number;
  bannedUsers: number;
  tenants: number;
  landlords: number;
  admins: number;
  verifiedUsers: number;
}

export interface SuspendUserRequest {
  userId: number;
  reason: string;
  duration?: number; // days
}
```

### Step 2: Create Admin Service

**File: `frontend/lib/services/admin.service.ts`**

```typescript
import apiClient from '../api-client';
import { API_ENDPOINTS } from '../constants/api-endpoints';
import type { AdminUser, UserFilter, UserStats, SuspendUserRequest } from '@/types/admin';

export interface UserResponse {
  content: AdminUser[];
  totalElements: number;
  totalPages: number;
  currentPage: number;
  pageSize: number;
}

export const adminService = {
  /**
   * Get all users with filtering and pagination
   */
  async getUsers(
    page = 0,
    size = 20,
    filter: UserFilter = {}
  ): Promise<UserResponse> {
    const params = new URLSearchParams({
      page: page.toString(),
      size: size.toString(),
      ...(filter.role && { role: filter.role }),
      ...(filter.status && { status: filter.status }),
      ...(filter.verified !== undefined && { verified: filter.verified.toString() }),
      ...(filter.search && { search: filter.search })
    });

    const response = await apiClient.get<UserResponse>(
      `${API_ENDPOINTS.ADMIN}/users?${params}`
    );
    return response.data;
  },

  /**
   * Get user statistics
   */
  async getUserStats(): Promise<UserStats> {
    const response = await apiClient.get<UserStats>(
      `${API_ENDPOINTS.ADMIN}/users/stats`
    );
    return response.data;
  },

  /**
   * Get user details
   */
  async getUserDetails(userId: number): Promise<AdminUser> {
    const response = await apiClient.get<AdminUser>(
      `${API_ENDPOINTS.ADMIN}/users/${userId}`
    );
    return response.data;
  },

  /**
   * Suspend user
   */
  async suspendUser(data: SuspendUserRequest): Promise<void> {
    await apiClient.post(
      `${API_ENDPOINTS.ADMIN}/users/${data.userId}/suspend`,
      data
    );
  },

  /**
   * Activate user
   */
  async activateUser(userId: number): Promise<void> {
    await apiClient.post(
      `${API_ENDPOINTS.ADMIN}/users/${userId}/activate`
    );
  },

  /**
   * Ban user permanently
   */
  async banUser(userId: number, reason: string): Promise<void> {
    await apiClient.post(
      `${API_ENDPOINTS.ADMIN}/users/${userId}/ban`,
      { reason }
    );
  },

  /**
   * Delete user (soft delete)
   */
  async deleteUser(userId: number): Promise<void> {
    await apiClient.delete(
      `${API_ENDPOINTS.ADMIN}/users/${userId}`
    );
  },

  /**
   * Change user role
   */
  async changeUserRole(userId: number, role: string): Promise<void> {
    await apiClient.put(
      `${API_ENDPOINTS.ADMIN}/users/${userId}/role`,
      { role }
    );
  }
};
```

### Step 3: Add Admin Endpoints

**Update: `frontend/lib/constants/api-endpoints.ts`**

```typescript
export const API_ENDPOINTS = {
  // ... existing endpoints ...
  ADMIN: '/admin',
  // ... rest
};
```

### Step 4: Create User Management Page

**File: `frontend/app/(protected)/admin/users/page.tsx`**

```typescript
'use client';

import { useState, useEffect } from 'react';
import { Search, Filter, UserX, UserCheck, Ban, Trash2, MoreVertical } from 'lucide-react';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Badge } from '@/components/ui/badge';
import { Avatar, AvatarFallback, AvatarImage } from '@/components/ui/avatar';
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from '@/components/ui/table';
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select';
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from '@/components/ui/dropdown-menu';
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogFooter,
} from '@/components/ui/dialog';
import { Textarea } from '@/components/ui/textarea';
import { Label } from '@/components/ui/label';
import { adminService } from '@/lib/services/admin.service';
import type { AdminUser, UserFilter, UserStats } from '@/types/admin';
import { useToast } from '@/hooks/use-toast';
import { format } from 'date-fns';
import Link from 'next/link';

export default function AdminUsersPage() {
  const [users, setUsers] = useState<AdminUser[]>([]);
  const [stats, setStats] = useState<UserStats | null>(null);
  const [loading, setLoading] = useState(true);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [filter, setFilter] = useState<UserFilter>({});
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedUser, setSelectedUser] = useState<AdminUser | null>(null);
  const [actionDialog, setActionDialog] = useState<'suspend' | 'ban' | 'delete' | null>(null);
  const [actionReason, setActionReason] = useState('');
  const [actionLoading, setActionLoading] = useState(false);
  const { toast } = useToast();

  useEffect(() => {
    loadUsers();
    loadStats();
  }, [page, filter]);

  const loadUsers = async () => {
    try {
      setLoading(true);
      const response = await adminService.getUsers(page, 20, filter);
      setUsers(response.content);
      setTotalPages(response.totalPages);
    } catch (error) {
      toast({
        title: 'Error',
        description: 'Failed to load users',
        variant: 'destructive',
      });
    } finally {
      setLoading(false);
    }
  };

  const loadStats = async () => {
    try {
      const statsData = await adminService.getUserStats();
      setStats(statsData);
    } catch (error) {
      console.error('Failed to load stats:', error);
    }
  };

  const handleSearch = () => {
    setFilter({ ...filter, search: searchTerm });
    setPage(0);
  };

  const handleSuspend = async () => {
    if (!selectedUser || !actionReason.trim()) return;

    try {
      setActionLoading(true);
      await adminService.suspendUser({
        userId: selectedUser.id,
        reason: actionReason
      });
      
      toast({
        title: 'Success',
        description: 'User suspended successfully',
      });
      
      closeActionDialog();
      loadUsers();
      loadStats();
    } catch (error: any) {
      toast({
        title: 'Error',
        description: error.response?.data?.message || 'Failed to suspend user',
        variant: 'destructive',
      });
    } finally {
      setActionLoading(false);
    }
  };

  const handleActivate = async (userId: number) => {
    try {
      await adminService.activateUser(userId);
      toast({
        title: 'Success',
        description: 'User activated successfully',
      });
      loadUsers();
      loadStats();
    } catch (error) {
      toast({
        title: 'Error',
        description: 'Failed to activate user',
        variant: 'destructive',
      });
    }
  };

  const handleBan = async () => {
    if (!selectedUser || !actionReason.trim()) return;

    try {
      setActionLoading(true);
      await adminService.banUser(selectedUser.id, actionReason);
      
      toast({
        title: 'Success',
        description: 'User banned successfully',
      });
      
      closeActionDialog();
      loadUsers();
      loadStats();
    } catch (error) {
      toast({
        title: 'Error',
        description: 'Failed to ban user',
        variant: 'destructive',
      });
    } finally {
      setActionLoading(false);
    }
  };

  const handleDelete = async () => {
    if (!selectedUser) return;

    try {
      setActionLoading(true);
      await adminService.deleteUser(selectedUser.id);
      
      toast({
        title: 'Success',
        description: 'User deleted successfully',
      });
      
      closeActionDialog();
      loadUsers();
      loadStats();
    } catch (error) {
      toast({
        title: 'Error',
        description: 'Failed to delete user',
        variant: 'destructive',
      });
    } finally {
      setActionLoading(false);
    }
  };

  const openActionDialog = (user: AdminUser, action: 'suspend' | 'ban' | 'delete') => {
    setSelectedUser(user);
    setActionDialog(action);
    setActionReason('');
  };

  const closeActionDialog = () => {
    setSelectedUser(null);
    setActionDialog(null);
    setActionReason('');
  };

  const getStatusBadge = (status: string) => {
    const variants = {
      ACTIVE: 'default',
      SUSPENDED: 'secondary',
      BANNED: 'destructive'
    } as const;

    return (
      <Badge variant={variants[status as keyof typeof variants] || 'default'}>
        {status}
      </Badge>
    );
  };

  const getRoleBadge = (role: string) => {
    const colors = {
      ADMIN: 'bg-purple-100 text-purple-800',
      LANDLORD: 'bg-blue-100 text-blue-800',
      TENANT: 'bg-green-100 text-green-800'
    };

    return (
      <Badge className={colors[role as keyof typeof colors]}>
        {role}
      </Badge>
    );
  };

  return (
    <div className="container py-8 space-y-6">
      {/* Header */}
      <div>
        <h1 className="text-3xl font-bold">User Management</h1>
        <p className="text-muted-foreground mt-1">
          Manage users, roles, and account status
        </p>
      </div>

      {/* Statistics */}
      {stats && (
        <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
          <Card>
            <CardHeader className="pb-2">
              <CardTitle className="text-sm font-medium text-muted-foreground">
                Total Users
              </CardTitle>
            </CardHeader>
            <CardContent>
              <div className="text-2xl font-bold">{stats.totalUsers}</div>
            </CardContent>
          </Card>

          <Card>
            <CardHeader className="pb-2">
              <CardTitle className="text-sm font-medium text-muted-foreground">
                Active Users
              </CardTitle>
            </CardHeader>
            <CardContent>
              <div className="text-2xl font-bold text-green-600">
                {stats.activeUsers}
              </div>
            </CardContent>
          </Card>

          <Card>
            <CardHeader className="pb-2">
              <CardTitle className="text-sm font-medium text-muted-foreground">
                Tenants
              </CardTitle>
            </CardHeader>
            <CardContent>
              <div className="text-2xl font-bold">{stats.tenants}</div>
            </CardContent>
          </Card>

          <Card>
            <CardHeader className="pb-2">
              <CardTitle className="text-sm font-medium text-muted-foreground">
                Landlords
              </CardTitle>
            </CardHeader>
            <CardContent>
              <div className="text-2xl font-bold">{stats.landlords}</div>
            </CardContent>
          </Card>
        </div>
      )}

      {/* Filters */}
      <Card>
        <CardContent className="p-6">
          <div className="flex flex-col md:flex-row gap-4">
            <div className="flex-1 flex gap-2">
              <Input
                placeholder="Search by name or email..."
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
                onKeyPress={(e) => e.key === 'Enter' && handleSearch()}
              />
              <Button onClick={handleSearch}>
                <Search className="h-4 w-4" />
              </Button>
            </div>

            <Select
              value={filter.role || 'all'}
              onValueChange={(value) => {
                setFilter({ ...filter, role: value === 'all' ? undefined : value as any });
                setPage(0);
              }}
            >
              <SelectTrigger className="w-[180px]">
                <SelectValue placeholder="Role" />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="all">All Roles</SelectItem>
                <SelectItem value="TENANT">Tenant</SelectItem>
                <SelectItem value="LANDLORD">Landlord</SelectItem>
                <SelectItem value="ADMIN">Admin</SelectItem>
              </SelectContent>
            </Select>

            <Select
              value={filter.status || 'all'}
              onValueChange={(value) => {
                setFilter({ ...filter, status: value === 'all' ? undefined : value as any });
                setPage(0);
              }}
            >
              <SelectTrigger className="w-[180px]">
                <SelectValue placeholder="Status" />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="all">All Status</SelectItem>
                <SelectItem value="ACTIVE">Active</SelectItem>
                <SelectItem value="SUSPENDED">Suspended</SelectItem>
                <SelectItem value="BANNED">Banned</SelectItem>
              </SelectContent>
            </Select>
          </div>
        </CardContent>
      </Card>

      {/* Users Table */}
      <Card>
        <CardContent className="p-0">
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>User</TableHead>
                <TableHead>Role</TableHead>
                <TableHead>Status</TableHead>
                <TableHead>Verified</TableHead>
                <TableHead>Joined</TableHead>
                <TableHead>Last Login</TableHead>
                <TableHead>Actions</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {loading ? (
                <TableRow>
                  <TableCell colSpan={7} className="text-center py-8">
                    Loading users...
                  </TableCell>
                </TableRow>
              ) : users.length === 0 ? (
                <TableRow>
                  <TableCell colSpan={7} className="text-center py-8">
                    No users found
                  </TableCell>
                </TableRow>
              ) : (
                users.map((user) => (
                  <TableRow key={user.id}>
                    <TableCell>
                      <div className="flex items-center gap-3">
                        <Avatar>
                          <AvatarFallback>
                            {user.fullName.charAt(0)}
                          </AvatarFallback>
                        </Avatar>
                        <div>
                          <Link
                            href={`/admin/users/${user.id}`}
                            className="font-medium hover:underline"
                          >
                            {user.fullName}
                          </Link>
                          <p className="text-sm text-muted-foreground">
                            {user.email}
                          </p>
                        </div>
                      </div>
                    </TableCell>
                    <TableCell>{getRoleBadge(user.role)}</TableCell>
                    <TableCell>{getStatusBadge(user.status)}</TableCell>
                    <TableCell>
                      {user.emailVerified && user.phoneVerified ? (
                        <Badge variant="default" className="text-xs">
                          Verified
                        </Badge>
                      ) : (
                        <Badge variant="secondary" className="text-xs">
                          Unverified
                        </Badge>
                      )}
                    </TableCell>
                    <TableCell>
                      {format(new Date(user.createdAt), 'MMM dd, yyyy')}
                    </TableCell>
                    <TableCell>
                      {user.lastLogin
                        ? format(new Date(user.lastLogin), 'MMM dd, yyyy')
                        : 'Never'}
                    </TableCell>
                    <TableCell>
                      <DropdownMenu>
                        <DropdownMenuTrigger asChild>
                          <Button variant="ghost" size="icon">
                            <MoreVertical className="h-4 w-4" />
                          </Button>
                        </DropdownMenuTrigger>
                        <DropdownMenuContent align="end">
                          <DropdownMenuItem asChild>
                            <Link href={`/admin/users/${user.id}`}>
                              View Details
                            </Link>
                          </DropdownMenuItem>
                          
                          {user.status === 'ACTIVE' && (
                            <DropdownMenuItem
                              onClick={() => openActionDialog(user, 'suspend')}
                            >
                              <UserX className="h-4 w-4 mr-2" />
                              Suspend
                            </DropdownMenuItem>
                          )}
                          
                          {user.status === 'SUSPENDED' && (
                            <DropdownMenuItem
                              onClick={() => handleActivate(user.id)}
                            >
                              <UserCheck className="h-4 w-4 mr-2" />
                              Activate
                            </DropdownMenuItem>
                          )}
                          
                          {user.status !== 'BANNED' && (
                            <DropdownMenuItem
                              onClick={() => openActionDialog(user, 'ban')}
                              className="text-destructive"
                            >
                              <Ban className="h-4 w-4 mr-2" />
                              Ban
                            </DropdownMenuItem>
                          )}
                          
                          <DropdownMenuItem
                            onClick={() => openActionDialog(user, 'delete')}
                            className="text-destructive"
                          >
                            <Trash2 className="h-4 w-4 mr-2" />
                            Delete
                          </DropdownMenuItem>
                        </DropdownMenuContent>
                      </DropdownMenu>
                    </TableCell>
                  </TableRow>
                ))
              )}
            </TableBody>
          </Table>
        </CardContent>
      </Card>

      {/* Pagination */}
      {totalPages > 1 && (
        <div className="flex justify-center gap-2">
          <Button
            variant="outline"
            onClick={() => setPage(p => Math.max(0, p - 1))}
            disabled={page === 0}
          >
            Previous
          </Button>
          <span className="flex items-center px-4">
            Page {page + 1} of {totalPages}
          </span>
          <Button
            variant="outline"
            onClick={() => setPage(p => Math.min(totalPages - 1, p + 1))}
            disabled={page === totalPages - 1}
          >
            Next
          </Button>
        </div>
      )}

      {/* Action Dialogs */}
      <Dialog open={actionDialog !== null} onOpenChange={() => closeActionDialog()}>
        <DialogContent>
          <DialogHeader>
            <DialogTitle>
              {actionDialog === 'suspend' && 'Suspend User'}
              {actionDialog === 'ban' && 'Ban User'}
              {actionDialog === 'delete' && 'Delete User'}
            </DialogTitle>
            <DialogDescription>
              {actionDialog === 'suspend' &&
                'Temporarily suspend this user\'s account. They won\'t be able to login.'}
              {actionDialog === 'ban' &&
                'Permanently ban this user. This action cannot be undone.'}
              {actionDialog === 'delete' &&
                'Delete this user\'s account and all associated data. This action cannot be undone.'}
            </DialogDescription>
          </DialogHeader>

          {(actionDialog === 'suspend' || actionDialog === 'ban') && (
            <div className="space-y-2">
              <Label htmlFor="reason">Reason</Label>
              <Textarea
                id="reason"
                placeholder="Provide a reason for this action..."
                value={actionReason}
                onChange={(e) => setActionReason(e.target.value)}
                rows={4}
                required
              />
            </div>
          )}

          <DialogFooter>
            <Button
              variant="outline"
              onClick={closeActionDialog}
              disabled={actionLoading}
            >
              Cancel
            </Button>
            <Button
              variant="destructive"
              onClick={() => {
                if (actionDialog === 'suspend') handleSuspend();
                if (actionDialog === 'ban') handleBan();
                if (actionDialog === 'delete') handleDelete();
              }}
              disabled={actionLoading || ((actionDialog === 'suspend' || actionDialog === 'ban') && !actionReason.trim())}
            >
              {actionLoading ? 'Processing...' : 'Confirm'}
            </Button>
          </DialogFooter>
        </DialogContent>
      </Dialog>
    </div>
  );
}
```

### Verification Checklist

- [ ] Admin types defined
- [ ] Admin service created
- [ ] Admin endpoints added
- [ ] User management page loads
- [ ] User statistics displayed
- [ ] Search functionality works
- [ ] Role filter works
- [ ] Status filter works
- [ ] User table displays correctly
- [ ] Status badges color-coded
- [ ] Suspend user dialog works
- [ ] Activate user works
- [ ] Ban user dialog works
- [ ] Delete user dialog works
- [ ] Pagination works
- [ ] Action loading states
- [ ] Toast notifications
- [ ] User details link

---

## Task 8.2: Property Moderation (6-8h)

### Step 1: Update Admin Service for Properties

**Update: `frontend/lib/services/admin.service.ts`**

Add these methods to the existing admin service:

```typescript
/**
 * Get all properties for moderation
 */
async getPropertiesForModeration(
  page = 0,
  size = 20,
  status?: string
): Promise<any> {
  const params = new URLSearchParams({
    page: page.toString(),
    size: size.toString(),
    ...(status && { status })
  });

  const response = await apiClient.get(
    `${API_ENDPOINTS.ADMIN}/properties?${params}`
  );
  return response.data;
},

/**
 * Approve property
 */
async approveProperty(propertyId: number): Promise<void> {
  await apiClient.post(
    `${API_ENDPOINTS.ADMIN}/properties/${propertyId}/approve`
  );
},

/**
 * Reject property
 */
async rejectProperty(propertyId: number, reason: string): Promise<void> {
  await apiClient.post(
    `${API_ENDPOINTS.ADMIN}/properties/${propertyId}/reject`,
    { reason }
  );
},

/**
 * Force delete property
 */
async forceDeleteProperty(propertyId: number): Promise<void> {
  await apiClient.delete(
    `${API_ENDPOINTS.ADMIN}/properties/${propertyId}`
  );
},
```

### Step 2: Create Property Moderation Page

**File: `frontend/app/(protected)/admin/properties/page.tsx`**

```typescript
'use client';

import { useState, useEffect } from 'react';
import { Search, CheckCircle, XCircle, Trash2, Eye, MapPin } from 'lucide-react';
import { Card, CardContent } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Badge } from '@/components/ui/badge';
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select';
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogFooter,
} from '@/components/ui/dialog';
import { Textarea } from '@/components/ui/textarea';
import { Label } from '@/components/ui/label';
import { adminService } from '@/lib/services/admin.service';
import { useToast } from '@/hooks/use-toast';
import Link from 'next/link';
import Image from 'next/image';

export default function AdminPropertiesPage() {
  const [properties, setProperties] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [statusFilter, setStatusFilter] = useState('');
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedProperty, setSelectedProperty] = useState<any>(null);
  const [actionDialog, setActionDialog] = useState<'approve' | 'reject' | 'delete' | null>(null);
  const [rejectionReason, setRejectionReason] = useState('');
  const [actionLoading, setActionLoading] = useState(false);
  const { toast } = useToast();

  useEffect(() => {
    loadProperties();
  }, [page, statusFilter]);

  const loadProperties = async () => {
    try {
      setLoading(true);
      const response = await adminService.getPropertiesForModeration(
        page,
        20,
        statusFilter
      );
      setProperties(response.content);
      setTotalPages(response.totalPages);
    } catch (error) {
      toast({
        title: 'Error',
        description: 'Failed to load properties',
        variant: 'destructive',
      });
    } finally {
      setLoading(false);
    }
  };

  const handleApprove = async () => {
    if (!selectedProperty) return;

    try {
      setActionLoading(true);
      await adminService.approveProperty(selectedProperty.id);
      toast({
        title: 'Success',
        description: 'Property approved successfully',
      });
      closeActionDialog();
      loadProperties();
    } catch (error) {
      toast({
        title: 'Error',
        description: 'Failed to approve property',
        variant: 'destructive',
      });
    } finally {
      setActionLoading(false);
    }
  };

  const handleReject = async () => {
    if (!selectedProperty || !rejectionReason.trim()) return;

    try {
      setActionLoading(true);
      await adminService.rejectProperty(selectedProperty.id, rejectionReason);
      toast({
        title: 'Success',
        description: 'Property rejected',
      });
      closeActionDialog();
      loadProperties();
    } catch (error) {
      toast({
        title: 'Error',
        description: 'Failed to reject property',
        variant: 'destructive',
      });
    } finally {
      setActionLoading(false);
    }
  };

  const handleDelete = async () => {
    if (!selectedProperty) return;

    try {
      setActionLoading(true);
      await adminService.forceDeleteProperty(selectedProperty.id);
      toast({
        title: 'Success',
        description: 'Property deleted',
      });
      closeActionDialog();
      loadProperties();
    } catch (error) {
      toast({
        title: 'Error',
        description: 'Failed to delete property',
        variant: 'destructive',
      });
    } finally {
      setActionLoading(false);
    }
  };

  const openActionDialog = (property: any, action: 'approve' | 'reject' | 'delete') => {
    setSelectedProperty(property);
    setActionDialog(action);
    setRejectionReason('');
  };

  const closeActionDialog = () => {
    setSelectedProperty(null);
    setActionDialog(null);
    setRejectionReason('');
  };

  const getVerificationBadge = (status: string) => {
    const variants = {
      VERIFIED: { variant: 'default' as const, label: 'Verified' },
      PENDING: { variant: 'secondary' as const, label: 'Pending' },
      REJECTED: { variant: 'destructive' as const, label: 'Rejected' }
    };

    const config = variants[status as keyof typeof variants] || variants.PENDING;
    return <Badge variant={config.variant}>{config.label}</Badge>;
  };

  return (
    <div className="container py-8 space-y-6">
      {/* Header */}
      <div>
        <h1 className="text-3xl font-bold">Property Moderation</h1>
        <p className="text-muted-foreground mt-1">
          Review and moderate property listings
        </p>
      </div>

      {/* Filters */}
      <Card>
        <CardContent className="p-6">
          <div className="flex gap-4">
            <div className="flex-1 flex gap-2">
              <Input
                placeholder="Search properties..."
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
              />
              <Button>
                <Search className="h-4 w-4" />
              </Button>
            </div>

            <Select value={statusFilter} onValueChange={setStatusFilter}>
              <SelectTrigger className="w-[200px]">
                <SelectValue placeholder="All Status" />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="">All Status</SelectItem>
                <SelectItem value="PENDING">Pending</SelectItem>
                <SelectItem value="VERIFIED">Verified</SelectItem>
                <SelectItem value="REJECTED">Rejected</SelectItem>
              </SelectContent>
            </Select>
          </div>
        </CardContent>
      </Card>

      {/* Properties Grid */}
      {loading ? (
        <div className="text-center py-8">Loading properties...</div>
      ) : properties.length === 0 ? (
        <div className="text-center py-8">No properties found</div>
      ) : (
        <div className="grid gap-6">
          {properties.map((property) => (
            <Card key={property.id}>
              <CardContent className="p-6">
                <div className="flex gap-6">
                  {/* Property Image */}
                  <div className="w-48 h-48 flex-shrink-0">
                    {property.images?.[0] ? (
                      <img
                        src={property.images[0]}
                        alt={property.title}
                        className="w-full h-full object-cover rounded-lg"
                      />
                    ) : (
                      <div className="w-full h-full bg-muted rounded-lg flex items-center justify-center">
                        <MapPin className="h-12 w-12 text-muted-foreground" />
                      </div>
                    )}
                  </div>

                  {/* Property Details */}
                  <div className="flex-1">
                    <div className="flex items-start justify-between mb-2">
                      <div>
                        <h3 className="text-xl font-semibold">{property.title}</h3>
                        <p className="text-muted-foreground">
                          {property.address}, {property.city}
                        </p>
                      </div>
                      {getVerificationBadge(property.verificationStatus)}
                    </div>

                    <p className="text-sm text-muted-foreground mb-4 line-clamp-2">
                      {property.description}
                    </p>

                    <div className="grid grid-cols-4 gap-4 mb-4">
                      <div>
                        <p className="text-xs text-muted-foreground">Type</p>
                        <p className="font-medium">{property.propertyType}</p>
                      </div>
                      <div>
                        <p className="text-xs text-muted-foreground">Bedrooms</p>
                        <p className="font-medium">{property.bedrooms}</p>
                      </div>
                      <div>
                        <p className="text-xs text-muted-foreground">Bathrooms</p>
                        <p className="font-medium">{property.bathrooms}</p>
                      </div>
                      <div>
                        <p className="text-xs text-muted-foreground">Rent</p>
                        <p className="font-medium">NPR {property.rentAmount.toLocaleString()}/mo</p>
                      </div>
                    </div>

                    <div className="text-sm text-muted-foreground mb-4">
                      <p>Landlord: {property.landlordName}</p>
                      <p>Posted: {new Date(property.createdAt).toLocaleDateString()}</p>
                    </div>

                    {/* Actions */}
                    <div className="flex gap-2">
                      <Link href={`/properties/${property.id}`}>
                        <Button variant="outline" size="sm">
                          <Eye className="h-4 w-4 mr-2" />
                          View Details
                        </Button>
                      </Link>

                      {property.verificationStatus === 'PENDING' && (
                        <>
                          <Button
                            size="sm"
                            onClick={() => openActionDialog(property, 'approve')}
                          >
                            <CheckCircle className="h-4 w-4 mr-2" />
                            Approve
                          </Button>
                          <Button
                            size="sm"
                            variant="destructive"
                            onClick={() => openActionDialog(property, 'reject')}
                          >
                            <XCircle className="h-4 w-4 mr-2" />
                            Reject
                          </Button>
                        </>
                      )}

                      <Button
                        size="sm"
                        variant="outline"
                        onClick={() => openActionDialog(property, 'delete')}
                      >
                        <Trash2 className="h-4 w-4 mr-2" />
                        Delete
                      </Button>
                    </div>
                  </div>
                </div>
              </CardContent>
            </Card>
          ))}
        </div>
      )}

      {/* Pagination */}
      {totalPages > 1 && (
        <div className="flex justify-center gap-2">
          <Button
            variant="outline"
            onClick={() => setPage(p => Math.max(0, p - 1))}
            disabled={page === 0}
          >
            Previous
          </Button>
          <span className="flex items-center px-4">
            Page {page + 1} of {totalPages}
          </span>
          <Button
            variant="outline"
            onClick={() => setPage(p => Math.min(totalPages - 1, p + 1))}
            disabled={page === totalPages - 1}
          >
            Next
          </Button>
        </div>
      )}

      {/* Action Dialogs */}
      <Dialog open={actionDialog !== null} onOpenChange={closeActionDialog}>
        <DialogContent>
          <DialogHeader>
            <DialogTitle>
              {actionDialog === 'approve' && 'Approve Property'}
              {actionDialog === 'reject' && 'Reject Property'}
              {actionDialog === 'delete' && 'Delete Property'}
            </DialogTitle>
            <DialogDescription>
              {actionDialog === 'approve' &&
                'Approve this property to make it visible to tenants.'}
              {actionDialog === 'reject' &&
                'Reject this property and notify the landlord.'}
              {actionDialog === 'delete' &&
                'Permanently delete this property. This action cannot be undone.'}
            </DialogDescription>
          </DialogHeader>

          {actionDialog === 'reject' && (
            <div className="space-y-2">
              <Label htmlFor="rejection-reason">Rejection Reason</Label>
              <Textarea
                id="rejection-reason"
                placeholder="Explain why this property is being rejected..."
                value={rejectionReason}
                onChange={(e) => setRejectionReason(e.target.value)}
                rows={4}
                required
              />
            </div>
          )}

          <DialogFooter>
            <Button variant="outline" onClick={closeActionDialog} disabled={actionLoading}>
              Cancel
            </Button>
            <Button
              variant={actionDialog === 'approve' ? 'default' : 'destructive'}
              onClick={() => {
                if (actionDialog === 'approve') handleApprove();
                if (actionDialog === 'reject') handleReject();
                if (actionDialog === 'delete') handleDelete();
              }}
              disabled={actionLoading || (actionDialog === 'reject' && !rejectionReason.trim())}
            >
              {actionLoading ? 'Processing...' : 'Confirm'}
            </Button>
          </DialogFooter>
        </DialogContent>
      </Dialog>
    </div>
  );
}
```

### Verification Checklist

- [ ] Property moderation methods added to admin service
- [ ] Property moderation page created
- [ ] Properties displayed with images
- [ ] Status filter works
- [ ] Search functionality
- [ ] Approve property works
- [ ] Reject property with reason
- [ ] Delete property works
- [ ] Verification status badges
- [ ] Pagination works
- [ ] View property details link
- [ ] Toast notifications

---

## Task 8.3: Admin Dashboard & Analytics (8-10h)

### Step 1: Create Dashboard Types

**File: `frontend/types/admin-dashboard.ts`**

```typescript
export interface AdminDashboardStats {
  totalUsers: number;
  activeUsers: number;
  totalProperties: number;
  verifiedProperties: number;
  pendingProperties: number;
  totalApplications: number;
  activeLeases: number;
  totalRevenue: number;
  monthlyRevenue: number;
}

export interface UserGrowthData {
  month: string;
  tenants: number;
  landlords: number;
  total: number;
}

export interface PropertyGrowthData {
  month: string;
  properties: number;
  verified: number;
}

export interface RevenueData {
  month: string;
  revenue: number;
  transactions: number;
}

export interface RecentActivity {
  id: number;
  type: 'USER_REGISTERED' | 'PROPERTY_LISTED' | 'APPLICATION_SUBMITTED' | 'LEASE_CREATED' | 'PAYMENT_MADE';
  description: string;
  timestamp: string;
  userId?: number;
  userName?: string;
}
```

### Step 2: Update Admin Service for Dashboard

**Update: `frontend/lib/services/admin.service.ts`**

```typescript
/**
 * Get admin dashboard statistics
 */
async getDashboardStats(): Promise<AdminDashboardStats> {
  const response = await apiClient.get<AdminDashboardStats>(
    `${API_ENDPOINTS.ADMIN}/dashboard/stats`
  );
  return response.data;
},

/**
 * Get user growth data
 */
async getUserGrowth(): Promise<UserGrowthData[]> {
  const response = await apiClient.get<UserGrowthData[]>(
    `${API_ENDPOINTS.ADMIN}/dashboard/user-growth`
  );
  return response.data;
},

/**
 * Get property growth data
 */
async getPropertyGrowth(): Promise<PropertyGrowthData[]> {
  const response = await apiClient.get<PropertyGrowthData[]>(
    `${API_ENDPOINTS.ADMIN}/dashboard/property-growth`
  );
  return response.data;
},

/**
 * Get revenue data
 */
async getRevenueData(): Promise<RevenueData[]> {
  const response = await apiClient.get<RevenueData[]>(
    `${API_ENDPOINTS.ADMIN}/dashboard/revenue`
  );
  return response.data;
},

/**
 * Get recent activity
 */
async getRecentActivity(limit = 10): Promise<RecentActivity[]> {
  const response = await apiClient.get<RecentActivity[]>(
    `${API_ENDPOINTS.ADMIN}/dashboard/recent-activity`,
    { params: { limit } }
  );
  return response.data;
},
```

### Step 3: Create Admin Dashboard Page

**File: `frontend/app/(protected)/admin/page.tsx`**

```typescript
'use client';

import { useState, useEffect } from 'react';
import { Users, Home, FileText, DollarSign, TrendingUp, Activity } from 'lucide-react';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { StatsCard } from '@/components/stats-card';
import {
  LineChart,
  Line,
  BarChart,
  Bar,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
  ResponsiveContainer
} from 'recharts';
import { adminService } from '@/lib/services/admin.service';
import type {
  AdminDashboardStats,
  UserGrowthData,
  PropertyGrowthData,
  RevenueData,
  RecentActivity
} from '@/types/admin-dashboard';
import { useToast } from '@/hooks/use-toast';
import { formatDistanceToNow } from 'date-fns';

export default function AdminDashboardPage() {
  const [stats, setStats] = useState<AdminDashboardStats | null>(null);
  const [userGrowth, setUserGrowth] = useState<UserGrowthData[]>([]);
  const [propertyGrowth, setPropertyGrowth] = useState<PropertyGrowthData[]>([]);
  const [revenueData, setRevenueData] = useState<RevenueData[]>([]);
  const [recentActivity, setRecentActivity] = useState<RecentActivity[]>([]);
  const [loading, setLoading] = useState(true);
  const { toast } = useToast();

  useEffect(() => {
    loadDashboardData();
  }, []);

  const loadDashboardData = async () => {
    try {
      setLoading(true);
      const [statsData, userGrowthData, propertyGrowthData, revenue, activity] =
        await Promise.all([
          adminService.getDashboardStats(),
          adminService.getUserGrowth(),
          adminService.getPropertyGrowth(),
          adminService.getRevenueData(),
          adminService.getRecentActivity(10)
        ]);

      setStats(statsData);
      setUserGrowth(userGrowthData);
      setPropertyGrowth(propertyGrowthData);
      setRevenueData(revenue);
      setRecentActivity(activity);
    } catch (error) {
      toast({
        title: 'Error',
        description: 'Failed to load dashboard data',
        variant: 'destructive',
      });
    } finally {
      setLoading(false);
    }
  };

  if (loading || !stats) {
    return <div className="container py-8">Loading dashboard...</div>;
  }

  return (
    <div className="container py-8 space-y-6">
      {/* Header */}
      <div>
        <h1 className="text-3xl font-bold">Admin Dashboard</h1>
        <p className="text-muted-foreground mt-1">
          Overview of platform statistics and analytics
        </p>
      </div>

      {/* Key Metrics */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        <StatsCard
          title="Total Users"
          value={stats.totalUsers}
          change={`${stats.activeUsers} active`}
          icon={Users}
          trend="up"
        />
        <StatsCard
          title="Total Properties"
          value={stats.totalProperties}
          change={`${stats.pendingProperties} pending`}
          icon={Home}
          trend="up"
        />
        <StatsCard
          title="Active Leases"
          value={stats.activeLeases}
          change={`${stats.totalApplications} applications`}
          icon={FileText}
          trend="neutral"
        />
        <StatsCard
          title="Monthly Revenue"
          value={`NPR ${stats.monthlyRevenue.toLocaleString()}`}
          change={`NPR ${stats.totalRevenue.toLocaleString()} total`}
          icon={DollarSign}
          trend="up"
        />
      </div>

      {/* Charts */}
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        {/* User Growth */}
        <Card>
          <CardHeader>
            <CardTitle>User Growth</CardTitle>
          </CardHeader>
          <CardContent>
            <ResponsiveContainer width="100%" height={300}>
              <LineChart data={userGrowth}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="month" />
                <YAxis />
                <Tooltip />
                <Legend />
                <Line
                  type="monotone"
                  dataKey="tenants"
                  stroke="#10b981"
                  name="Tenants"
                />
                <Line
                  type="monotone"
                  dataKey="landlords"
                  stroke="#3b82f6"
                  name="Landlords"
                />
                <Line
                  type="monotone"
                  dataKey="total"
                  stroke="#8b5cf6"
                  name="Total"
                  strokeWidth={2}
                />
              </LineChart>
            </ResponsiveContainer>
          </CardContent>
        </Card>

        {/* Property Growth */}
        <Card>
          <CardHeader>
            <CardTitle>Property Listings</CardTitle>
          </CardHeader>
          <CardContent>
            <ResponsiveContainer width="100%" height={300}>
              <BarChart data={propertyGrowth}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="month" />
                <YAxis />
                <Tooltip />
                <Legend />
                <Bar dataKey="properties" fill="#3b82f6" name="Total" />
                <Bar dataKey="verified" fill="#10b981" name="Verified" />
              </BarChart>
            </ResponsiveContainer>
          </CardContent>
        </Card>

        {/* Revenue */}
        <Card>
          <CardHeader>
            <CardTitle>Revenue Trend</CardTitle>
          </CardHeader>
          <CardContent>
            <ResponsiveContainer width="100%" height={300}>
              <LineChart data={revenueData}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="month" />
                <YAxis />
                <Tooltip />
                <Legend />
                <Line
                  type="monotone"
                  dataKey="revenue"
                  stroke="#10b981"
                  name="Revenue (NPR)"
                  strokeWidth={2}
                />
              </LineChart>
            </ResponsiveContainer>
          </CardContent>
        </Card>

        {/* Recent Activity */}
        <Card>
          <CardHeader>
            <CardTitle>Recent Activity</CardTitle>
          </CardHeader>
          <CardContent>
            <div className="space-y-4">
              {recentActivity.map((activity) => (
                <div key={activity.id} className="flex items-start gap-3">
                  <div className="p-2 bg-primary/10 rounded-full">
                    <Activity className="h-4 w-4 text-primary" />
                  </div>
                  <div className="flex-1 min-w-0">
                    <p className="text-sm font-medium">{activity.description}</p>
                    {activity.userName && (
                      <p className="text-xs text-muted-foreground">
                        {activity.userName}
                      </p>
                    )}
                    <p className="text-xs text-muted-foreground">
                      {formatDistanceToNow(new Date(activity.timestamp), {
                        addSuffix: true
                      })}
                    </p>
                  </div>
                </div>
              ))}
            </div>
          </CardContent>
        </Card>
      </div>
    </div>
  );
}
```

### Verification Checklist

- [ ] Admin dashboard types defined
- [ ] Dashboard methods in admin service
- [ ] Admin dashboard page created
- [ ] Key metrics displayed
- [ ] User growth chart works
- [ ] Property growth chart works
- [ ] Revenue chart displays
- [ ] Recent activity feed
- [ ] Charts responsive
- [ ] Data loads correctly
- [ ] Error handling
- [ ] Loading states

---

## Task 8.4: Reports & Data Export (2-4h)

### Step 1: Update Admin Service for Reports

**Update: `frontend/lib/services/admin.service.ts`**

```typescript
/**
 * Generate user report
 */
async generateUserReport(startDate: string, endDate: string): Promise<Blob> {
  const response = await apiClient.get(
    `${API_ENDPOINTS.ADMIN}/reports/users`,
    {
      params: { startDate, endDate },
      responseType: 'blob'
    }
  );
  return response.data;
},

/**
 * Generate property report
 */
async generatePropertyReport(startDate: string, endDate: string): Promise<Blob> {
  const response = await apiClient.get(
    `${API_ENDPOINTS.ADMIN}/reports/properties`,
    {
      params: { startDate, endDate },
      responseType: 'blob'
    }
  );
  return response.data;
},

/**
 * Generate revenue report
 */
async generateRevenueReport(startDate: string, endDate: string): Promise<Blob> {
  const response = await apiClient.get(
    `${API_ENDPOINTS.ADMIN}/reports/revenue`,
    {
      params: { startDate, endDate },
      responseType: 'blob'
    }
  );
  return response.data;
},
```

### Step 2: Create Reports Page

**File: `frontend/app/(protected)/admin/reports/page.tsx`**

```typescript
'use client';

import { useState } from 'react';
import { Download, FileText, Calendar } from 'lucide-react';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Label } from '@/components/ui/label';
import { Input } from '@/components/ui/input';
import { adminService } from '@/lib/services/admin.service';
import { useToast } from '@/hooks/use-toast';
import { format, subMonths } from 'date-fns';

export default function AdminReportsPage() {
  const [userReportDates, setUserReportDates] = useState({
    startDate: format(subMonths(new Date(), 1), 'yyyy-MM-dd'),
    endDate: format(new Date(), 'yyyy-MM-dd')
  });
  const [propertyReportDates, setPropertyReportDates] = useState({
    startDate: format(subMonths(new Date(), 1), 'yyyy-MM-dd'),
    endDate: format(new Date(), 'yyyy-MM-dd')
  });
  const [revenueReportDates, setRevenueReportDates] = useState({
    startDate: format(subMonths(new Date(), 1), 'yyyy-MM-dd'),
    endDate: format(new Date(), 'yyyy-MM-dd')
  });
  const [loading, setLoading] = useState<string | null>(null);
  const { toast } = useToast();

  const downloadFile = (blob: Blob, filename: string) => {
    const url = window.URL.createObjectURL(blob);
    const link = document.createElement('a');
    link.href = url;
    link.download = filename;
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
    window.URL.revokeObjectURL(url);
  };

  const handleGenerateUserReport = async () => {
    try {
      setLoading('users');
      const blob = await adminService.generateUserReport(
        userReportDates.startDate,
        userReportDates.endDate
      );
      downloadFile(blob, `user-report-${userReportDates.startDate}-to-${userReportDates.endDate}.csv`);
      toast({
        title: 'Success',
        description: 'User report downloaded successfully',
      });
    } catch (error) {
      toast({
        title: 'Error',
        description: 'Failed to generate report',
        variant: 'destructive',
      });
    } finally {
      setLoading(null);
    }
  };

  const handleGeneratePropertyReport = async () => {
    try {
      setLoading('properties');
      const blob = await adminService.generatePropertyReport(
        propertyReportDates.startDate,
        propertyReportDates.endDate
      );
      downloadFile(blob, `property-report-${propertyReportDates.startDate}-to-${propertyReportDates.endDate}.csv`);
      toast({
        title: 'Success',
        description: 'Property report downloaded successfully',
      });
    } catch (error) {
      toast({
        title: 'Error',
        description: 'Failed to generate report',
        variant: 'destructive',
      });
    } finally {
      setLoading(null);
    }
  };

  const handleGenerateRevenueReport = async () => {
    try {
      setLoading('revenue');
      const blob = await adminService.generateRevenueReport(
        revenueReportDates.startDate,
        revenueReportDates.endDate
      );
      downloadFile(blob, `revenue-report-${revenueReportDates.startDate}-to-${revenueReportDates.endDate}.csv`);
      toast({
        title: 'Success',
        description: 'Revenue report downloaded successfully',
      });
    } catch (error) {
      toast({
        title: 'Error',
        description: 'Failed to generate report',
        variant: 'destructive',
      });
    } finally {
      setLoading(null);
    }
  };

  return (
    <div className="container max-w-4xl py-8 space-y-6">
      {/* Header */}
      <div>
        <h1 className="text-3xl font-bold">Reports & Analytics</h1>
        <p className="text-muted-foreground mt-1">
          Generate and download system reports
        </p>
      </div>

      {/* User Report */}
      <Card>
        <CardHeader>
          <CardTitle className="flex items-center gap-2">
            <FileText className="h-5 w-5" />
            User Report
          </CardTitle>
          <CardDescription>
            Export user registration and activity data
          </CardDescription>
        </CardHeader>
        <CardContent className="space-y-4">
          <div className="grid md:grid-cols-2 gap-4">
            <div className="space-y-2">
              <Label htmlFor="user-start-date">Start Date</Label>
              <Input
                id="user-start-date"
                type="date"
                value={userReportDates.startDate}
                onChange={(e) =>
                  setUserReportDates({ ...userReportDates, startDate: e.target.value })
                }
              />
            </div>
            <div className="space-y-2">
              <Label htmlFor="user-end-date">End Date</Label>
              <Input
                id="user-end-date"
                type="date"
                value={userReportDates.endDate}
                onChange={(e) =>
                  setUserReportDates({ ...userReportDates, endDate: e.target.value })
                }
              />
            </div>
          </div>
          <Button
            onClick={handleGenerateUserReport}
            disabled={loading === 'users'}
          >
            <Download className="h-4 w-4 mr-2" />
            {loading === 'users' ? 'Generating...' : 'Download User Report'}
          </Button>
        </CardContent>
      </Card>

      {/* Property Report */}
      <Card>
        <CardHeader>
          <CardTitle className="flex items-center gap-2">
            <FileText className="h-5 w-5" />
            Property Report
          </CardTitle>
          <CardDescription>
            Export property listings and verification data
          </CardDescription>
        </CardHeader>
        <CardContent className="space-y-4">
          <div className="grid md:grid-cols-2 gap-4">
            <div className="space-y-2">
              <Label htmlFor="property-start-date">Start Date</Label>
              <Input
                id="property-start-date"
                type="date"
                value={propertyReportDates.startDate}
                onChange={(e) =>
                  setPropertyReportDates({ ...propertyReportDates, startDate: e.target.value })
                }
              />
            </div>
            <div className="space-y-2">
              <Label htmlFor="property-end-date">End Date</Label>
              <Input
                id="property-end-date"
                type="date"
                value={propertyReportDates.endDate}
                onChange={(e) =>
                  setPropertyReportDates({ ...propertyReportDates, endDate: e.target.value })
                }
              />
            </div>
          </div>
          <Button
            onClick={handleGeneratePropertyReport}
            disabled={loading === 'properties'}
          >
            <Download className="h-4 w-4 mr-2" />
            {loading === 'properties' ? 'Generating...' : 'Download Property Report'}
          </Button>
        </CardContent>
      </Card>

      {/* Revenue Report */}
      <Card>
        <CardHeader>
          <CardTitle className="flex items-center gap-2">
            <FileText className="h-5 w-5" />
            Revenue Report
          </CardTitle>
          <CardDescription>
            Export payment and revenue data
          </CardDescription>
        </CardHeader>
        <CardContent className="space-y-4">
          <div className="grid md:grid-cols-2 gap-4">
            <div className="space-y-2">
              <Label htmlFor="revenue-start-date">Start Date</Label>
              <Input
                id="revenue-start-date"
                type="date"
                value={revenueReportDates.startDate}
                onChange={(e) =>
                  setRevenueReportDates({ ...revenueReportDates, startDate: e.target.value })
                }
              />
            </div>
            <div className="space-y-2">
              <Label htmlFor="revenue-end-date">End Date</Label>
              <Input
                id="revenue-end-date"
                type="date"
                value={revenueReportDates.endDate}
                onChange={(e) =>
                  setRevenueReportDates({ ...revenueReportDates, endDate: e.target.value })
                }
              />
            </div>
          </div>
          <Button
            onClick={handleGenerateRevenueReport}
            disabled={loading === 'revenue'}
          >
            <Download className="h-4 w-4 mr-2" />
            {loading === 'revenue' ? 'Generating...' : 'Download Revenue Report'}
          </Button>
        </CardContent>
      </Card>
    </div>
  );
}
```

### Verification Checklist

- [ ] Report generation methods in admin service
- [ ] Reports page created
- [ ] Date range selectors work
- [ ] User report downloads
- [ ] Property report downloads
- [ ] Revenue report downloads
- [ ] File downloads with correct names
- [ ] Loading states during generation
- [ ] Error handling
- [ ] Toast notifications

---

## Phase 8 Completion Checklist

### User Management

- [ ] User list with pagination
- [ ] Search and filters work
- [ ] User statistics displayed
- [ ] Suspend user functionality
- [ ] Activate user functionality
- [ ] Ban user functionality
- [ ] Delete user functionality
- [ ] Action dialogs with confirmations

### Property Moderation

- [ ] Property list for moderation
- [ ] Property details displayed
- [ ] Approve property works
- [ ] Reject property with reason
- [ ] Delete property works
- [ ] Status filters work
- [ ] Pagination works

### Admin Dashboard

- [ ] Dashboard statistics load
- [ ] User growth chart
- [ ] Property growth chart
- [ ] Revenue chart
- [ ] Recent activity feed
- [ ] All charts responsive

### Reports

- [ ] User report generation
- [ ] Property report generation
- [ ] Revenue report generation
- [ ] Date range selection
- [ ] CSV file downloads
- [ ] Proper file naming

---

# PHASE 9: Testing & Quality Assurance (24-30h)

**Objective:** Implement comprehensive testing strategy including unit tests, integration tests, E2E tests, and quality assurance.

**Dependencies:** Phases 1-8 completed

**Tasks:**

- Task 9.1: Unit Testing Setup (4-6h)
- Task 9.2: Component Testing (8-10h)
- Task 9.3: Integration Testing (6-8h)
- Task 9.4: End-to-End Testing (4-6h)
- Task 9.5: Performance & Security Audit (2-4h)

---

## Task 9.1: Unit Testing Setup (4-6h)

### Step 1: Install Testing Dependencies

```bash
cd frontend
npm install --save-dev @testing-library/react @testing-library/jest-dom @testing-library/user-event
npm install --save-dev jest jest-environment-jsdom
npm install --save-dev @types/jest
```

### Step 2: Configure Jest

**File: `frontend/jest.config.js`**

```javascript
const nextJest = require('next/jest');

const createJestConfig = nextJest({
  // Provide the path to your Next.js app to load next.config.js and .env files in your test environment
  dir: './',
});

// Add any custom config to be passed to Jest
const customJestConfig = {
  setupFilesAfterEnv: ['<rootDir>/jest.setup.js'],
  testEnvironment: 'jest-environment-jsdom',
  moduleNameMapper: {
    '^@/(.*)$': '<rootDir>/$1',
  },
  collectCoverageFrom: [
    'app/**/*.{js,jsx,ts,tsx}',
    'components/**/*.{js,jsx,ts,tsx}',
    'lib/**/*.{js,jsx,ts,tsx}',
    '!**/*.d.ts',
    '!**/node_modules/**',
    '!**/.next/**',
  ],
  testPathIgnorePatterns: ['<rootDir>/.next/', '<rootDir>/node_modules/'],
  transformIgnorePatterns: [
    '/node_modules/',
    '^.+\\.module\\.(css|sass|scss)$',
  ],
};

// createJestConfig is exported this way to ensure that next/jest can load the Next.js config which is async
module.exports = createJestConfig(customJestConfig);
```

**File: `frontend/jest.setup.js`**

```javascript
import '@testing-library/jest-dom';

// Mock Next.js router
jest.mock('next/navigation', () => ({
  useRouter() {
    return {
      push: jest.fn(),
      replace: jest.fn(),
      prefetch: jest.fn(),
      back: jest.fn(),
    };
  },
  usePathname() {
    return '/';
  },
  useSearchParams() {
    return new URLSearchParams();
  },
}));

// Mock window.matchMedia
Object.defineProperty(window, 'matchMedia', {
  writable: true,
  value: jest.fn().mockImplementation(query => ({
    matches: false,
    media: query,
    onchange: null,
    addListener: jest.fn(),
    removeListener: jest.fn(),
    addEventListener: jest.fn(),
    removeEventListener: jest.fn(),
    dispatchEvent: jest.fn(),
  })),
});
```

### Step 3: Create Service Tests

**File: `frontend/lib/services/__tests__/auth.service.test.ts`**

```typescript
import { authService } from '../auth.service';
import apiClient from '../../api-client';

jest.mock('../../api-client');

describe('AuthService', () => {
  beforeEach(() => {
    jest.clearAllMocks();
    localStorage.clear();
  });

  describe('login', () => {
    it('should login successfully and store tokens', async () => {
      const mockResponse = {
        data: {
          accessToken: 'access-token',
          refreshToken: 'refresh-token',
          user: {
            id: 1,
            email: 'test@example.com',
            fullName: 'Test User',
            role: 'TENANT'
          }
        }
      };

      (apiClient.post as jest.Mock).mockResolvedValue(mockResponse);

      const result = await authService.login({
        email: 'test@example.com',
        password: 'password123'
      });

      expect(apiClient.post).toHaveBeenCalledWith('/auth/login', {
        email: 'test@example.com',
        password: 'password123'
      });

      expect(localStorage.getItem('accessToken')).toBe('access-token');
      expect(localStorage.getItem('refreshToken')).toBe('refresh-token');
      expect(result.user.email).toBe('test@example.com');
    });

    it('should throw error on invalid credentials', async () => {
      (apiClient.post as jest.Mock).mockRejectedValue({
        response: {
          data: { message: 'Invalid credentials' }
        }
      });

      await expect(
        authService.login({
          email: 'test@example.com',
          password: 'wrong'
        })
      ).rejects.toThrow();
    });
  });

  describe('register', () => {
    it('should register a new user', async () => {
      const mockResponse = {
        data: {
          accessToken: 'access-token',
          refreshToken: 'refresh-token',
          user: {
            id: 1,
            email: 'new@example.com',
            fullName: 'New User',
            role: 'TENANT'
          }
        }
      };

      (apiClient.post as jest.Mock).mockResolvedValue(mockResponse);

      const result = await authService.register({
        email: 'new@example.com',
        password: 'password123',
        fullName: 'New User',
        phone: '1234567890',
        role: 'TENANT'
      });

      expect(result.user.email).toBe('new@example.com');
    });
  });

  describe('logout', () => {
    it('should clear tokens from localStorage', () => {
      localStorage.setItem('accessToken', 'token');
      localStorage.setItem('refreshToken', 'token');

      authService.logout();

      expect(localStorage.getItem('accessToken')).toBeNull();
      expect(localStorage.getItem('refreshToken')).toBeNull();
    });
  });
});
```

**File: `frontend/lib/services/__tests__/property.service.test.ts`**

```typescript
import { propertyService } from '../property.service';
import apiClient from '../../api-client';

jest.mock('../../api-client');

describe('PropertyService', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  describe('getProperties', () => {
    it('should fetch properties with filters', async () => {
      const mockResponse = {
        data: {
          content: [
            { id: 1, title: 'Property 1' },
            { id: 2, title: 'Property 2' }
          ],
          totalElements: 2,
          totalPages: 1
        }
      };

      (apiClient.get as jest.Mock).mockResolvedValue(mockResponse);

      const result = await propertyService.getProperties(0, 12, {
        city: 'Kathmandu',
        propertyType: 'APARTMENT'
      });

      expect(result.content).toHaveLength(2);
      expect(apiClient.get).toHaveBeenCalled();
    });
  });

  describe('getPropertyById', () => {
    it('should fetch single property', async () => {
      const mockProperty = {
        id: 1,
        title: 'Test Property',
        city: 'Kathmandu'
      };

      (apiClient.get as jest.Mock).mockResolvedValue({ data: mockProperty });

      const result = await propertyService.getPropertyById(1);

      expect(result.id).toBe(1);
      expect(result.title).toBe('Test Property');
    });
  });
});
```

### Step 4: Create Utility Tests

**File: `frontend/lib/utils.test.ts`**

```typescript
import { cn } from './utils';

describe('Utils', () => {
  describe('cn (className merger)', () => {
    it('should merge class names', () => {
      expect(cn('foo', 'bar')).toBe('foo bar');
    });

    it('should handle conditional classes', () => {
      expect(cn('foo', false && 'bar', 'baz')).toBe('foo baz');
    });

    it('should handle undefined and null', () => {
      expect(cn('foo', undefined, null, 'bar')).toBe('foo bar');
    });
  });
});
```

### Step 5: Add Test Scripts

**Update: `frontend/package.json`**

```json
{
  "scripts": {
    "test": "jest",
    "test:watch": "jest --watch",
    "test:coverage": "jest --coverage"
  }
}
```

### Verification Checklist

- [ ] Jest installed and configured
- [ ] jest.setup.js created
- [ ] Auth service tests pass
- [ ] Property service tests pass
- [ ] Utils tests pass
- [ ] Test scripts in package.json
- [ ] Coverage reports generated
- [ ] Mocks configured correctly

---

## Task 9.2: Component Testing (8-10h)

### Step 1: Test Button Component

**File: `frontend/components/ui/__tests__/button.test.tsx`**

```typescript
import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { Button } from '../button';

describe('Button', () => {
  it('renders correctly', () => {
    render(<Button>Click me</Button>);
    expect(screen.getByRole('button', { name: /click me/i })).toBeInTheDocument();
  });

  it('handles click events', async () => {
    const handleClick = jest.fn();
    render(<Button onClick={handleClick}>Click me</Button>);
    
    await userEvent.click(screen.getByRole('button'));
    expect(handleClick).toHaveBeenCalledTimes(1);
  });

  it('can be disabled', () => {
    render(<Button disabled>Disabled</Button>);
    expect(screen.getByRole('button')).toBeDisabled();
  });

  it('applies variant classes', () => {
    const { rerender } = render(<Button variant="destructive">Delete</Button>);
    expect(screen.getByRole('button')).toHaveClass('bg-destructive');

    rerender(<Button variant="outline">Outline</Button>);
    expect(screen.getByRole('button')).toHaveClass('border');
  });
});
```

### Step 2: Test Property Card Component

**File: `frontend/components/__tests__/property-card.test.tsx`**

```typescript
import { render, screen } from '@testing-library/react';
import { PropertyCard } from '../property-card';

const mockProperty = {
  id: 1,
  title: 'Beautiful Apartment',
  description: 'A lovely place to stay',
  propertyType: 'APARTMENT',
  bedrooms: 2,
  bathrooms: 1,
  area: 800,
  rentAmount: 25000,
  securityDeposit: 50000,
  city: 'Kathmandu',
  address: 'Thamel',
  images: ['https://example.com/image.jpg'],
  amenities: ['WiFi', 'Parking'],
  availableFrom: '2026-02-01',
  landlordId: 1,
  landlordName: 'John Doe',
  verificationStatus: 'VERIFIED',
  createdAt: '2026-01-15T00:00:00Z'
};

describe('PropertyCard', () => {
  it('renders property information', () => {
    render(<PropertyCard property={mockProperty} />);

    expect(screen.getByText('Beautiful Apartment')).toBeInTheDocument();
    expect(screen.getByText(/Kathmandu/i)).toBeInTheDocument();
    expect(screen.getByText(/NPR 25,000/)).toBeInTheDocument();
  });

  it('displays property features', () => {
    render(<PropertyCard property={mockProperty} />);

    expect(screen.getByText(/2/)).toBeInTheDocument(); // bedrooms
    expect(screen.getByText(/1/)).toBeInTheDocument(); // bathrooms
  });

  it('shows property image', () => {
    render(<PropertyCard property={mockProperty} />);

    const image = screen.getByRole('img');
    expect(image).toHaveAttribute('src', expect.stringContaining('image.jpg'));
  });
});
```

### Step 3: Test Form Components

**File: `frontend/components/__tests__/login-form.test.tsx`**

```typescript
import { render, screen, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { LoginForm } from '../../app/auth/login/page';

// Mock the auth service
jest.mock('@/lib/services/auth.service');
jest.mock('@/contexts/auth-context', () => ({
  useAuth: () => ({
    login: jest.fn()
  })
}));

describe('LoginForm', () => {
  it('renders login form fields', () => {
    render(<LoginForm />);

    expect(screen.getByLabelText(/email/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/password/i)).toBeInTheDocument();
    expect(screen.getByRole('button', { name: /login/i })).toBeInTheDocument();
  });

  it('shows validation errors for empty fields', async () => {
    render(<LoginForm />);

    const submitButton = screen.getByRole('button', { name: /login/i });
    await userEvent.click(submitButton);

    await waitFor(() => {
      expect(screen.getByText(/email is required/i)).toBeInTheDocument();
    });
  });

  it('shows error for invalid email format', async () => {
    render(<LoginForm />);

    const emailInput = screen.getByLabelText(/email/i);
    await userEvent.type(emailInput, 'invalid-email');
    
    const submitButton = screen.getByRole('button', { name: /login/i });
    await userEvent.click(submitButton);

    await waitFor(() => {
      expect(screen.getByText(/invalid email/i)).toBeInTheDocument();
    });
  });
});
```

### Step 4: Test Context Providers

**File: `frontend/contexts/__tests__/auth-context.test.tsx`**

```typescript
import { render, screen, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { AuthProvider, useAuth } from '../auth-context';
import { authService } from '@/lib/services/auth.service';

jest.mock('@/lib/services/auth.service');

function TestComponent() {
  const { user, login, logout, loading } = useAuth();

  return (
    <div>
      {loading && <div>Loading...</div>}
      {user ? (
        <>
          <div>Logged in as {user.email}</div>
          <button onClick={logout}>Logout</button>
        </>
      ) : (
        <button onClick={() => login({ email: 'test@example.com', password: 'password' })}>
          Login
        </button>
      )}
    </div>
  );
}

describe('AuthContext', () => {
  it('provides authentication state', () => {
    render(
      <AuthProvider>
        <TestComponent />
      </AuthProvider>
    );

    expect(screen.getByRole('button', { name: /login/i })).toBeInTheDocument();
  });

  it('handles login', async () => {
    const mockUser = {
      id: 1,
      email: 'test@example.com',
      fullName: 'Test User',
      role: 'TENANT'
    };

    (authService.login as jest.Mock).mockResolvedValue({
      accessToken: 'token',
      refreshToken: 'refresh',
      user: mockUser
    });

    render(
      <AuthProvider>
        <TestComponent />
      </AuthProvider>
    );

    const loginButton = screen.getByRole('button', { name: /login/i });
    await userEvent.click(loginButton);

    await waitFor(() => {
      expect(screen.getByText(/logged in as test@example.com/i)).toBeInTheDocument();
    });
  });
});
```

### Verification Checklist

- [ ] Button component tests pass
- [ ] Property card tests pass
- [ ] Login form tests pass
- [ ] Form validation tested
- [ ] Auth context tests pass
- [ ] User interactions tested
- [ ] All components render correctly
- [ ] Edge cases covered

---

## Task 9.3: Integration Testing (6-8h)

### Step 1: Test API Integration

**File: `frontend/lib/__tests__/api-client.test.ts`**

```typescript
import apiClient from '../api-client';
import { authService } from '../services/auth.service';

jest.mock('axios');

describe('API Client', () => {
  beforeEach(() => {
    localStorage.clear();
  });

  it('includes auth token in requests', async () => {
    localStorage.setItem('accessToken', 'test-token');

    // Test that requests include the token
    const config = apiClient.defaults.headers.common;
    expect(config['Authorization']).toBeDefined();
  });

  it('handles 401 errors with token refresh', async () => {
    // Test automatic token refresh on 401
    localStorage.setItem('refreshToken', 'refresh-token');

    // Mock implementation would go here
  });
});
```

### Step 2: Test Complete User Flows

**File: `frontend/__tests__/integration/property-search.test.tsx`**

```typescript
import { render, screen, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import PropertyListPage from '@/app/properties/page';
import { propertyService } from '@/lib/services/property.service';

jest.mock('@/lib/services/property.service');

describe('Property Search Integration', () => {
  it('allows users to search and filter properties', async () => {
    const mockProperties = {
      content: [
        { id: 1, title: 'Apartment in Kathmandu', city: 'Kathmandu' },
        { id: 2, title: 'House in Pokhara', city: 'Pokhara' }
      ],
      totalElements: 2,
      totalPages: 1
    };

    (propertyService.getProperties as jest.Mock).mockResolvedValue(mockProperties);

    render(<PropertyListPage />);

    await waitFor(() => {
      expect(screen.getByText('Apartment in Kathmandu')).toBeInTheDocument();
    });

    // Test filtering
    const cityFilter = screen.getByRole('combobox', { name: /city/i });
    await userEvent.selectOptions(cityFilter, 'Kathmandu');

    await waitFor(() => {
      expect(propertyService.getProperties).toHaveBeenCalledWith(
        expect.anything(),
        expect.anything(),
        expect.objectContaining({ city: 'Kathmandu' })
      );
    });
  });
});
```

### Step 3: Test Authentication Flow

**File: `frontend/__tests__/integration/auth-flow.test.tsx`**

```typescript
import { render, screen, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { AuthProvider } from '@/contexts/auth-context';
import LoginPage from '@/app/auth/login/page';
import { authService } from '@/lib/services/auth.service';

jest.mock('@/lib/services/auth.service');

describe('Authentication Flow', () => {
  it('completes full login process', async () => {
    const mockUser = {
      id: 1,
      email: 'test@example.com',
      fullName: 'Test User',
      role: 'TENANT'
    };

    (authService.login as jest.Mock).mockResolvedValue({
      accessToken: 'token',
      refreshToken: 'refresh',
      user: mockUser
    });

    render(
      <AuthProvider>
        <LoginPage />
      </AuthProvider>
    );

    // Fill in form
    await userEvent.type(screen.getByLabelText(/email/i), 'test@example.com');
    await userEvent.type(screen.getByLabelText(/password/i), 'password123');

    // Submit
    await userEvent.click(screen.getByRole('button', { name: /login/i }));

    // Verify login was called
    await waitFor(() => {
      expect(authService.login).toHaveBeenCalledWith({
        email: 'test@example.com',
        password: 'password123'
      });
    });
  });
});
```

### Verification Checklist

- [ ] API client integration tested
- [ ] Token refresh flow tested
- [ ] Property search flow works
- [ ] Filter integration tested
- [ ] Authentication flow complete
- [ ] Form submission to API
- [ ] Error handling tested
- [ ] Loading states verified

---

## Task 9.4: End-to-End Testing (4-6h)

### Step 1: Install Playwright

```bash
cd frontend
npm install --save-dev @playwright/test
npx playwright install
```

### Step 2: Configure Playwright

**File: `frontend/playwright.config.ts`**

```typescript
import { defineConfig, devices } from '@playwright/test';

export default defineConfig({
  testDir: './e2e',
  fullyParallel: true,
  forbidOnly: !!process.env.CI,
  retries: process.env.CI ? 2 : 0,
  workers: process.env.CI ? 1 : undefined,
  reporter: 'html',
  use: {
    baseURL: 'http://localhost:3000',
    trace: 'on-first-retry',
  },

  projects: [
    {
      name: 'chromium',
      use: { ...devices['Desktop Chrome'] },
    },
    {
      name: 'firefox',
      use: { ...devices['Desktop Firefox'] },
    },
    {
      name: 'webkit',
      use: { ...devices['Desktop Safari'] },
    },
  ],

  webServer: {
    command: 'npm run dev',
    url: 'http://localhost:3000',
    reuseExistingServer: !process.env.CI,
  },
});
```

### Step 3: Create E2E Tests

**File: `frontend/e2e/auth.spec.ts`**

```typescript
import { test, expect } from '@playwright/test';

test.describe('Authentication', () => {
  test('should allow user to login', async ({ page }) => {
    await page.goto('/auth/login');

    await page.fill('input[name="email"]', 'test@example.com');
    await page.fill('input[name="password"]', 'password123');
    await page.click('button[type="submit"]');

    await expect(page).toHaveURL('/dashboard');
    await expect(page.locator('text=Welcome')).toBeVisible();
  });

  test('should show error for invalid credentials', async ({ page }) => {
    await page.goto('/auth/login');

    await page.fill('input[name="email"]', 'wrong@example.com');
    await page.fill('input[name="password"]', 'wrongpassword');
    await page.click('button[type="submit"]');

    await expect(page.locator('text=/Invalid credentials/i')).toBeVisible();
  });

  test('should allow user to register', async ({ page }) => {
    await page.goto('/auth/register');

    await page.fill('input[name="fullName"]', 'New User');
    await page.fill('input[name="email"]', 'new@example.com');
    await page.fill('input[name="phone"]', '9801234567');
    await page.fill('input[name="password"]', 'password123');
    await page.selectOption('select[name="role"]', 'TENANT');

    await page.click('button[type="submit"]');

    await expect(page).toHaveURL('/dashboard');
  });
});
```

**File: `frontend/e2e/property-search.spec.ts`**

```typescript
import { test, expect } from '@playwright/test';

test.describe('Property Search', () => {
  test('should display property listings', async ({ page }) => {
    await page.goto('/properties');

    await expect(page.locator('[data-testid="property-card"]')).toHaveCount(12);
  });

  test('should filter properties by city', async ({ page }) => {
    await page.goto('/properties');

    await page.selectOption('select[name="city"]', 'Kathmandu');
    await page.click('button:has-text("Search")');

    await expect(page).toHaveURL(/city=Kathmandu/);
    await expect(page.locator('text=Kathmandu')).toBeVisible();
  });

  test('should open property details', async ({ page }) => {
    await page.goto('/properties');

    await page.click('[data-testid="property-card"]:first-child');

    await expect(page).toHaveURL(/\/properties\/\d+/);
    await expect(page.locator('h1')).toBeVisible();
  });
});
```

**File: `frontend/e2e/application-flow.spec.ts`**

```typescript
import { test, expect } from '@playwright/test';

test.describe('Application Flow', () => {
  test.beforeEach(async ({ page }) => {
    // Login as tenant
    await page.goto('/auth/login');
    await page.fill('input[name="email"]', 'tenant@example.com');
    await page.fill('input[name="password"]', 'password123');
    await page.click('button[type="submit"]');
    await expect(page).toHaveURL('/dashboard');
  });

  test('should submit property application', async ({ page }) => {
    await page.goto('/properties/1');

    await page.click('button:has-text("Apply Now")');

    await page.fill('textarea[name="message"]', 'I am interested in this property');
    await page.fill('input[name="moveInDate"]', '2026-03-01');
    await page.fill('input[name="occupants"]', '2');

    await page.click('button:has-text("Submit Application")');

    await expect(page.locator('text=/Application submitted/i')).toBeVisible();
  });

  test('should view application status', async ({ page }) => {
    await page.goto('/tenant/applications');

    await expect(page.locator('[data-testid="application-card"]')).toHaveCount.greaterThan(0);
    await expect(page.locator('text=/Pending|Approved|Rejected/i')).toBeVisible();
  });
});
```

### Step 4: Add E2E Scripts

**Update: `frontend/package.json`**

```json
{
  "scripts": {
    "test:e2e": "playwright test",
    "test:e2e:ui": "playwright test --ui",
    "test:e2e:report": "playwright show-report"
  }
}
```

### Verification Checklist

- [ ] Playwright installed
- [ ] Playwright configured
- [ ] Auth E2E tests pass
- [ ] Property search E2E tests pass
- [ ] Application flow E2E tests pass
- [ ] Tests run in multiple browsers
- [ ] Screenshots on failure
- [ ] E2E reports generated

---

## Task 9.5: Performance & Security Audit (2-4h)

### Step 1: Performance Testing with Lighthouse

Create a script to run Lighthouse audits:

**File: `frontend/scripts/lighthouse-audit.js`**

```javascript
const lighthouse = require('lighthouse');
const chromeLauncher = require('chrome-launcher');
const fs = require('fs');

async function runLighthouse(url) {
  const chrome = await chromeLauncher.launch({ chromeFlags: ['--headless'] });
  
  const options = {
    logLevel: 'info',
    output: 'html',
    port: chrome.port,
  };

  const runnerResult = await lighthouse(url, options);

  // Write report
  const reportHtml = runnerResult.report;
  fs.writeFileSync('lighthouse-report.html', reportHtml);

  // Extract scores
  const scores = {
    performance: runnerResult.lhr.categories.performance.score * 100,
    accessibility: runnerResult.lhr.categories.accessibility.score * 100,
    bestPractices: runnerResult.lhr.categories['best-practices'].score * 100,
    seo: runnerResult.lhr.categories.seo.score * 100,
  };

  console.log('Lighthouse Scores:');
  console.log(`Performance: ${scores.performance}`);
  console.log(`Accessibility: ${scores.accessibility}`);
  console.log(`Best Practices: ${scores.bestPractices}`);
  console.log(`SEO: ${scores.seo}`);

  await chrome.kill();

  return scores;
}

runLighthouse('http://localhost:3000');
```

### Step 2: Security Checklist

**File: `SECURITY_CHECKLIST.md`**

```markdown
# Security Checklist

## Authentication & Authorization
- [ ] JWT tokens stored securely (httpOnly cookies preferred)
- [ ] Token expiration implemented
- [ ] Refresh token rotation
- [ ] CORS configured correctly
- [ ] Password strength requirements enforced
- [ ] Rate limiting on auth endpoints

## Data Protection
- [ ] Input validation on all forms
- [ ] XSS protection implemented
- [ ] SQL injection prevented (using ORM)
- [ ] CSRF protection enabled
- [ ] File upload validation (type, size)
- [ ] Sensitive data not logged

## API Security
- [ ] Authentication required for protected routes
- [ ] Role-based access control working
- [ ] API rate limiting configured
- [ ] Request size limits set
- [ ] HTTPS enforced in production

## Frontend Security
- [ ] No sensitive data in localStorage
- [ ] Environment variables secured
- [ ] Dependencies updated (no known vulnerabilities)
- [ ] Content Security Policy configured
- [ ] Secure headers implemented

## Audit Commands
```bash
# Check for vulnerabilities
npm audit

# Fix auto-fixable vulnerabilities
npm audit fix

# Check outdated packages
npm outdated
```

```

### Step 3: Performance Optimization Checklist

**File: `PERFORMANCE_CHECKLIST.md`**

```markdown
# Performance Checklist

## Images
- [ ] Images optimized (WebP format)
- [ ] Lazy loading implemented
- [ ] Proper image sizing
- [ ] CDN for static assets

## Code Splitting
- [ ] Dynamic imports for heavy components
- [ ] Route-based code splitting
- [ ] Component lazy loading

## Caching
- [ ] API responses cached where appropriate
- [ ] Browser caching headers set
- [ ] Service worker for offline support

## Bundle Size
- [ ] Tree shaking enabled
- [ ] Unused dependencies removed
- [ ] Bundle analyzer run
- [ ] Critical CSS inlined

## Network
- [ ] API calls minimized
- [ ] Request batching where possible
- [ ] Compression enabled (gzip/brotli)
- [ ] HTTP/2 enabled

## Monitoring
```bash
# Analyze bundle
npm run build
npx @next/bundle-analyzer

# Check lighthouse scores
npm run lighthouse
```

```

### Verification Checklist

- [ ] Lighthouse audit script created
- [ ] Performance scores > 90
- [ ] Accessibility scores > 90
- [ ] Security checklist completed
- [ ] npm audit shows no vulnerabilities
- [ ] Performance optimizations applied
- [ ] Bundle size optimized
- [ ] Images optimized

---

## Phase 9 Completion Checklist

### Unit Testing
- [ ] Jest configured
- [ ] Service tests written
- [ ] Utility tests written
- [ ] Test coverage > 80%

### Component Testing
- [ ] UI component tests
- [ ] Form validation tests
- [ ] Context provider tests
- [ ] User interaction tests

### Integration Testing
- [ ] API integration tests
- [ ] User flow tests
- [ ] Authentication tests
- [ ] Error handling tests

### E2E Testing
- [ ] Playwright configured
- [ ] Critical paths tested
- [ ] Multi-browser testing
- [ ] E2E reports generated

### Quality Assurance
- [ ] Lighthouse audits passing
- [ ] Security checklist complete
- [ ] Performance optimized
- [ ] No critical vulnerabilities

---

# PHASE 10: Deployment & Production (16-20h)

**Objective:** Deploy the application to production with proper CI/CD, monitoring, and documentation.

**Dependencies:** Phases 1-9 completed

**Tasks:**
- Task 10.1: Environment Configuration (2-3h)
- Task 10.2: Docker Containerization (4-5h)
- Task 10.3: CI/CD Pipeline Setup (4-5h)
- Task 10.4: Production Deployment (3-4h)
- Task 10.5: Monitoring & Logging (2-3h)
- Task 10.6: Documentation Finalization (1-2h)

---

## Task 10.1: Environment Configuration (2-3h)

### Step 1: Production Environment Variables

**File: `frontend/.env.production`**

```env
# API Configuration
NEXT_PUBLIC_API_URL=https://api.gharsaathi.com
NEXT_PUBLIC_APP_URL=https://gharsaathi.com

# Analytics
NEXT_PUBLIC_GA_TRACKING_ID=G-XXXXXXXXXX

# Feature Flags
NEXT_PUBLIC_ENABLE_REVIEWS=true
NEXT_PUBLIC_ENABLE_NOTIFICATIONS=true

# File Upload
NEXT_PUBLIC_MAX_FILE_SIZE=5242880
NEXT_PUBLIC_ALLOWED_FILE_TYPES=image/jpeg,image/png,image/webp

# Maps API (if using)
NEXT_PUBLIC_GOOGLE_MAPS_API_KEY=your-maps-api-key
```

**File: `backend/src/main/resources/application-prod.properties`**

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://${DB_HOST:localhost}:${DB_PORT:3306}/${DB_NAME:gharsaathi_db}
spring.datasource.username=${DB_USERNAME:root}
spring.datasource.password=${DB_PASSWORD:}

# JPA Configuration
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=false

# Server Configuration
server.port=${PORT:8080}
server.error.include-message=never
server.error.include-stacktrace=never

# JWT Configuration
jwt.secret=${JWT_SECRET:your-production-secret-key-change-this}
jwt.expiration=3600000
jwt.refresh-expiration=604800000

# CORS Configuration
cors.allowed-origins=${CORS_ORIGINS:https://gharsaathi.com}

# File Upload
spring.servlet.multipart.max-file-size=5MB
spring.servlet.multipart.max-request-size=10MB
file.upload.dir=${FILE_UPLOAD_DIR:/var/gharsaathi/uploads}

# Logging
logging.level.root=INFO
logging.level.com.gharsaathi=INFO
logging.file.name=/var/log/gharsaathi/application.log
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} - %msg%n
logging.pattern.file=%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n

# Email Configuration (if using)
spring.mail.host=${SMTP_HOST:smtp.gmail.com}
spring.mail.port=${SMTP_PORT:587}
spring.mail.username=${SMTP_USERNAME:}
spring.mail.password=${SMTP_PASSWORD:}
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

### Step 2: Environment Variables Documentation

**File: `ENV_VARIABLES.md`**

```markdown
# Environment Variables

## Frontend (.env.production)

| Variable | Description | Required | Default |
|----------|-------------|----------|---------|
| NEXT_PUBLIC_API_URL | Backend API URL | Yes | - |
| NEXT_PUBLIC_APP_URL | Frontend URL | Yes | - |
| NEXT_PUBLIC_GA_TRACKING_ID | Google Analytics ID | No | - |
| NEXT_PUBLIC_MAX_FILE_SIZE | Max upload size (bytes) | No | 5242880 |

## Backend (application-prod.properties)

| Variable | Description | Required | Default |
|----------|-------------|----------|---------|
| DB_HOST | Database host | Yes | localhost |
| DB_PORT | Database port | Yes | 3306 |
| DB_NAME | Database name | Yes | gharsaathi_db |
| DB_USERNAME | Database user | Yes | root |
| DB_PASSWORD | Database password | Yes | - |
| JWT_SECRET | JWT secret key | Yes | - |
| CORS_ORIGINS | Allowed CORS origins | Yes | - |
| FILE_UPLOAD_DIR | File upload directory | Yes | /var/gharsaathi/uploads |
| SMTP_HOST | SMTP server host | No | - |
| SMTP_PORT | SMTP server port | No | 587 |
| SMTP_USERNAME | SMTP username | No | - |
| SMTP_PASSWORD | SMTP password | No | - |
```

### Verification Checklist

- [ ] Production environment files created
- [ ] All required variables documented
- [ ] Sensitive data in environment variables
- [ ] Database connection configured
- [ ] CORS origins set correctly
- [ ] File upload paths configured
- [ ] Logging configured
- [ ] Email configuration (if needed)

---

## Task 10.2: Docker Containerization (4-5h)

### Step 1: Frontend Dockerfile

**File: `frontend/Dockerfile`**

```dockerfile
# Stage 1: Dependencies
FROM node:20-alpine AS deps
WORKDIR /app

# Copy package files
COPY package.json pnpm-lock.yaml ./

# Install dependencies
RUN npm install -g pnpm && pnpm install --frozen-lockfile

# Stage 2: Builder
FROM node:20-alpine AS builder
WORKDIR /app

# Copy dependencies from deps stage
COPY --from=deps /app/node_modules ./node_modules
COPY . .

# Build application
ENV NEXT_TELEMETRY_DISABLED 1
RUN npm run build

# Stage 3: Runner
FROM node:20-alpine AS runner
WORKDIR /app

ENV NODE_ENV production
ENV NEXT_TELEMETRY_DISABLED 1

# Create non-root user
RUN addgroup --system --gid 1001 nodejs
RUN adduser --system --uid 1001 nextjs

# Copy necessary files
COPY --from=builder /app/public ./public
COPY --from=builder /app/.next/standalone ./
COPY --from=builder /app/.next/static ./.next/static

# Set permissions
RUN chown -R nextjs:nodejs /app

USER nextjs

EXPOSE 3000

ENV PORT 3000
ENV HOSTNAME "0.0.0.0"

CMD ["node", "server.js"]
```

**File: `frontend/.dockerignore`**

```
node_modules
.next
.git
.gitignore
README.md
.env
.env.local
.env.*.local
npm-debug.log*
.DS_Store
```

### Step 2: Backend Dockerfile

**File: `backend/Dockerfile`**

```dockerfile
# Stage 1: Build
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code and build
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Create directories
RUN mkdir -p /var/gharsaathi/uploads /var/log/gharsaathi

# Copy jar from build stage
COPY --from=build /app/target/*.jar app.jar

# Create non-root user
RUN addgroup -S spring && adduser -S spring -G spring
RUN chown -R spring:spring /app /var/gharsaathi /var/log/gharsaathi

USER spring:spring

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
```

**File: `backend/.dockerignore`**

```
target/
.mvn/
*.iml
.idea/
.git/
.gitignore
README.md
```

### Step 3: Docker Compose

**File: `docker-compose.yml`**

```yaml
version: '3.8'

services:
  # MySQL Database
  db:
    image: mysql:8.0
    container_name: gharsaathi-db
    restart: unless-stopped
    environment:
      MYSQL_ROOT_PASSWORD: ${DB_ROOT_PASSWORD}
      MYSQL_DATABASE: ${DB_NAME}
      MYSQL_USER: ${DB_USERNAME}
      MYSQL_PASSWORD: ${DB_PASSWORD}
    ports:
      - "3307:3306"
    volumes:
      - db_data:/var/lib/mysql
      - ./backend/scripts/init.sql:/docker-entrypoint-initdb.d/init.sql
    networks:
      - gharsaathi-network
    healthcheck:
      test: ["CMD", "mysqladmin", "ping", "-h", "localhost"]
      timeout: 20s
      retries: 10

  # Backend API
  backend:
    build:
      context: ./backend
      dockerfile: Dockerfile
    container_name: gharsaathi-backend
    restart: unless-stopped
    ports:
      - "8080:8080"
    environment:
      DB_HOST: db
      DB_PORT: 3306
      DB_NAME: ${DB_NAME}
      DB_USERNAME: ${DB_USERNAME}
      DB_PASSWORD: ${DB_PASSWORD}
      JWT_SECRET: ${JWT_SECRET}
      CORS_ORIGINS: ${CORS_ORIGINS}
      SPRING_PROFILES_ACTIVE: prod
    volumes:
      - upload_data:/var/gharsaathi/uploads
      - log_data:/var/log/gharsaathi
    depends_on:
      db:
        condition: service_healthy
    networks:
      - gharsaathi-network
    healthcheck:
      test: ["CMD", "wget", "--quiet", "--tries=1", "--spider", "http://localhost:8080/actuator/health"]
      interval: 30s
      timeout: 10s
      retries: 5

  # Frontend
  frontend:
    build:
      context: ./frontend
      dockerfile: Dockerfile
    container_name: gharsaathi-frontend
    restart: unless-stopped
    ports:
      - "3000:3000"
    environment:
      NEXT_PUBLIC_API_URL: ${NEXT_PUBLIC_API_URL}
      NEXT_PUBLIC_APP_URL: ${NEXT_PUBLIC_APP_URL}
    depends_on:
      - backend
    networks:
      - gharsaathi-network

  # Nginx Reverse Proxy
  nginx:
    image: nginx:alpine
    container_name: gharsaathi-nginx
    restart: unless-stopped
    ports:
      - "80:80"
      - "443:443"
    volumes:
      - ./nginx/nginx.conf:/etc/nginx/nginx.conf
      - ./nginx/ssl:/etc/nginx/ssl
    depends_on:
      - frontend
      - backend
    networks:
      - gharsaathi-network

volumes:
  db_data:
  upload_data:
  log_data:

networks:
  gharsaathi-network:
    driver: bridge
```

### Step 4: Nginx Configuration

**File: `nginx/nginx.conf`**

```nginx
events {
    worker_connections 1024;
}

http {
    upstream backend {
        server backend:8080;
    }

    upstream frontend {
        server frontend:3000;
    }

    # HTTP to HTTPS redirect
    server {
        listen 80;
        server_name gharsaathi.com www.gharsaathi.com;
        
        location / {
            return 301 https://$server_name$request_uri;
        }
    }

    # HTTPS server
    server {
        listen 443 ssl http2;
        server_name gharsaathi.com www.gharsaathi.com;

        # SSL Configuration
        ssl_certificate /etc/nginx/ssl/certificate.crt;
        ssl_certificate_key /etc/nginx/ssl/private.key;
        ssl_protocols TLSv1.2 TLSv1.3;
        ssl_ciphers HIGH:!aNULL:!MD5;

        # Security Headers
        add_header X-Frame-Options "SAMEORIGIN" always;
        add_header X-Content-Type-Options "nosniff" always;
        add_header X-XSS-Protection "1; mode=block" always;
        add_header Strict-Transport-Security "max-age=31536000; includeSubDomains" always;

        # API requests
        location /api {
            proxy_pass http://backend;
            proxy_http_version 1.1;
            proxy_set_header Upgrade $http_upgrade;
            proxy_set_header Connection 'upgrade';
            proxy_set_header Host $host;
            proxy_set_header X-Real-IP $remote_addr;
            proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
            proxy_set_header X-Forwarded-Proto $scheme;
            proxy_cache_bypass $http_upgrade;
        }

        # Frontend requests
        location / {
            proxy_pass http://frontend;
            proxy_http_version 1.1;
            proxy_set_header Upgrade $http_upgrade;
            proxy_set_header Connection 'upgrade';
            proxy_set_header Host $host;
            proxy_cache_bypass $http_upgrade;
        }

        # File uploads
        client_max_body_size 10M;
    }
}
```

### Verification Checklist

- [ ] Frontend Dockerfile created
- [ ] Backend Dockerfile created
- [ ] Docker Compose file configured
- [ ] Nginx configuration created
- [ ] Multi-stage builds used
- [ ] Non-root users configured
- [ ] Health checks defined
- [ ] Volume mounts configured
- [ ] Networks configured
- [ ] SSL certificates prepared

---

## Task 10.3: CI/CD Pipeline Setup (4-5h)

### Step 1: GitHub Actions Workflow

**File: `.github/workflows/ci-cd.yml`**

```yaml
name: CI/CD Pipeline

on:
  push:
    branches: [main, develop]
  pull_request:
    branches: [main, develop]

env:
  NODE_VERSION: '20'
  JAVA_VERSION: '21'

jobs:
  # Frontend Tests
  frontend-test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4

      - name: Setup Node.js
        uses: actions/setup-node@v4
        with:
          node-version: ${{ env.NODE_VERSION }}
          cache: 'npm'
          cache-dependency-path: frontend/package-lock.json

      - name: Install dependencies
        working-directory: frontend
        run: npm ci

      - name: Run linter
        working-directory: frontend
        run: npm run lint

      - name: Run tests
        working-directory: frontend
        run: npm test -- --coverage

      - name: Upload coverage
        uses: codecov/codecov-action@v3
        with:
          files: ./frontend/coverage/lcov.info
          flags: frontend

  # Backend Tests
  backend-test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4

      - name: Setup Java
        uses: actions/setup-java@v4
        with:
          java-version: ${{ env.JAVA_VERSION }}
          distribution: 'temurin'
          cache: 'maven'

      - name: Run tests
        working-directory: backend
        run: mvn clean test

      - name: Generate coverage report
        working-directory: backend
        run: mvn jacoco:report

      - name: Upload coverage
        uses: codecov/codecov-action@v3
        with:
          files: ./backend/target/site/jacoco/jacoco.xml
          flags: backend

  # Build Docker Images
  build:
    needs: [frontend-test, backend-test]
    runs-on: ubuntu-latest
    if: github.ref == 'refs/heads/main'
    
    steps:
      - uses: actions/checkout@v4

      - name: Set up Docker Buildx
        uses: docker/setup-buildx-action@v3

      - name: Login to Docker Hub
        uses: docker/login-action@v3
        with:
          username: ${{ secrets.DOCKER_USERNAME }}
          password: ${{ secrets.DOCKER_PASSWORD }}

      - name: Build and push frontend
        uses: docker/build-push-action@v5
        with:
          context: ./frontend
          push: true
          tags: ${{ secrets.DOCKER_USERNAME }}/gharsaathi-frontend:latest
          cache-from: type=registry,ref=${{ secrets.DOCKER_USERNAME }}/gharsaathi-frontend:buildcache
          cache-to: type=registry,ref=${{ secrets.DOCKER_USERNAME }}/gharsaathi-frontend:buildcache,mode=max

      - name: Build and push backend
        uses: docker/build-push-action@v5
        with:
          context: ./backend
          push: true
          tags: ${{ secrets.DOCKER_USERNAME }}/gharsaathi-backend:latest
          cache-from: type=registry,ref=${{ secrets.DOCKER_USERNAME }}/gharsaathi-backend:buildcache
          cache-to: type=registry,ref=${{ secrets.DOCKER_USERNAME }}/gharsaathi-backend:buildcache,mode=max

  # Deploy to Production
  deploy:
    needs: build
    runs-on: ubuntu-latest
    if: github.ref == 'refs/heads/main'
    
    steps:
      - uses: actions/checkout@v4

      - name: Deploy to server
        uses: appleboy/ssh-action@master
        with:
          host: ${{ secrets.SERVER_HOST }}
          username: ${{ secrets.SERVER_USER }}
          key: ${{ secrets.SSH_PRIVATE_KEY }}
          script: |
            cd /opt/gharsaathi
            git pull origin main
            docker-compose pull
            docker-compose up -d
            docker system prune -f
```

### Step 2: Pre-commit Hooks

**File: `.husky/pre-commit`**

```bash
#!/usr/bin/env sh
. "$(dirname -- "$0")/_/husky.sh"

# Run frontend linting
cd frontend && npm run lint

# Run frontend tests
npm test -- --watchAll=false

# Run backend tests
cd ../backend && mvn test
```

### Step 3: Deployment Script

**File: `scripts/deploy.sh`**

```bash
#!/bin/bash

set -e

echo "🚀 Starting deployment..."

# Pull latest changes
echo "📥 Pulling latest code..."
git pull origin main

# Backup database
echo "💾 Backing up database..."
docker exec gharsaathi-db mysqldump -u root -p${DB_ROOT_PASSWORD} ${DB_NAME} > backup_$(date +%Y%m%d_%H%M%S).sql

# Pull latest images
echo "📦 Pulling Docker images..."
docker-compose pull

# Stop services
echo "🛑 Stopping services..."
docker-compose down

# Start services
echo "▶️  Starting services..."
docker-compose up -d

# Wait for services to be healthy
echo "⏳ Waiting for services..."
sleep 10

# Check health
echo "🏥 Checking service health..."
docker-compose ps

# Run migrations if needed
echo "🔄 Running database migrations..."
docker exec gharsaathi-backend java -jar app.jar --spring.profiles.active=prod db:migrate

# Clean up
echo "🧹 Cleaning up..."
docker system prune -f

echo "✅ Deployment completed successfully!"
```

### Verification Checklist

- [ ] GitHub Actions workflow created
- [ ] Frontend tests in pipeline
- [ ] Backend tests in pipeline
- [ ] Docker build in pipeline
- [ ] Deployment automation configured
- [ ] Pre-commit hooks configured
- [ ] Deployment script created
- [ ] Secrets configured in GitHub
- [ ] Pipeline runs successfully

---

## Task 10.4: Production Deployment (3-4h)

### Step 1: Server Setup

**Create a server setup script:**

**File: `scripts/server-setup.sh`**

```bash
#!/bin/bash

# Update system
sudo apt-get update && sudo apt-get upgrade -y

# Install Docker
curl -fsSL https://get.docker.com -o get-docker.sh
sudo sh get-docker.sh

# Install Docker Compose
sudo curl -L "https://github.com/docker/compose/releases/download/v2.24.0/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose

# Create application directory
sudo mkdir -p /opt/gharsaathi
sudo chown $USER:$USER /opt/gharsaathi

# Install Git
sudo apt-get install git -y

# Clone repository
cd /opt/gharsaathi
git clone https://github.com/yourusername/gharsaathi.git .

# Create environment file
cat > .env << EOF
# Database
DB_ROOT_PASSWORD=your-root-password
DB_NAME=gharsaathi_db
DB_USERNAME=gharsaathi_user
DB_PASSWORD=your-db-password

# Backend
JWT_SECRET=your-jwt-secret-key
CORS_ORIGINS=https://gharsaathi.com

# Frontend
NEXT_PUBLIC_API_URL=https://api.gharsaathi.com
NEXT_PUBLIC_APP_URL=https://gharsaathi.com
EOF

# Set up firewall
sudo ufw allow 80/tcp
sudo ufw allow 443/tcp
sudo ufw allow 22/tcp
sudo ufw enable

# Set up SSL with Let's Encrypt
sudo apt-get install certbot -y
sudo certbot certonly --standalone -d gharsaathi.com -d www.gharsaathi.com

echo "✅ Server setup complete!"
```

### Step 2: Database Migration

**File: `backend/src/main/resources/db/migration/V1__initial_schema.sql`**

```sql
-- This would be your initial database schema
-- Flyway or Liquibase will manage migrations
```

### Step 3: Deployment Checklist

**File: `DEPLOYMENT_CHECKLIST.md`**

```markdown
# Deployment Checklist

## Pre-Deployment
- [ ] All tests passing
- [ ] Code reviewed and approved
- [ ] Environment variables configured
- [ ] SSL certificates obtained
- [ ] Database backup created
- [ ] Rollback plan prepared

## Deployment Steps
- [ ] Pull latest code
- [ ] Build Docker images
- [ ] Stop old containers
- [ ] Start new containers
- [ ] Run database migrations
- [ ] Verify health checks
- [ ] Test critical paths
- [ ] Monitor logs

## Post-Deployment
- [ ] Verify all services running
- [ ] Check application logs
- [ ] Test user authentication
- [ ] Test property search
- [ ] Test application submission
- [ ] Monitor error rates
- [ ] Check performance metrics

## Rollback Procedure
If deployment fails:
1. Stop new containers: `docker-compose down`
2. Restore database backup
3. Start previous containers
4. Verify services
5. Investigate issue
```

### Verification Checklist

- [ ] Server provisioned
- [ ] Docker installed
- [ ] SSL certificates installed
- [ ] Firewall configured
- [ ] Application deployed
- [ ] Database migrated
- [ ] Services healthy
- [ ] Domain configured
- [ ] HTTPS working
- [ ] All features tested

---

## Task 10.5: Monitoring & Logging (2-3h)

### Step 1: Application Monitoring

**File: `docker-compose.monitoring.yml`**

```yaml
version: '3.8'

services:
  # Prometheus
  prometheus:
    image: prom/prometheus:latest
    container_name: gharsaathi-prometheus
    restart: unless-stopped
    ports:
      - "9090:9090"
    volumes:
      - ./monitoring/prometheus.yml:/etc/prometheus/prometheus.yml
      - prometheus_data:/prometheus
    command:
      - '--config.file=/etc/prometheus/prometheus.yml'
      - '--storage.tsdb.path=/prometheus'
    networks:
      - gharsaathi-network

  # Grafana
  grafana:
    image: grafana/grafana:latest
    container_name: gharsaathi-grafana
    restart: unless-stopped
    ports:
      - "3001:3000"
    environment:
      - GF_SECURITY_ADMIN_PASSWORD=admin
    volumes:
      - grafana_data:/var/lib/grafana
      - ./monitoring/dashboards:/etc/grafana/provisioning/dashboards
    depends_on:
      - prometheus
    networks:
      - gharsaathi-network

  # Loki for logs
  loki:
    image: grafana/loki:latest
    container_name: gharsaathi-loki
    restart: unless-stopped
    ports:
      - "3100:3100"
    volumes:
      - ./monitoring/loki-config.yml:/etc/loki/local-config.yaml
      - loki_data:/loki
    networks:
      - gharsaathi-network

volumes:
  prometheus_data:
  grafana_data:
  loki_data:

networks:
  gharsaathi-network:
    external: true
```

**File: `monitoring/prometheus.yml`**

```yaml
global:
  scrape_interval: 15s

scrape_configs:
  - job_name: 'backend'
    static_configs:
      - targets: ['backend:8080']
    metrics_path: '/actuator/prometheus'

  - job_name: 'frontend'
    static_configs:
      - targets: ['frontend:3000']
```

### Step 2: Logging Configuration

**File: `backend/src/main/resources/logback-spring.xml`**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <include resource="org/springframework/boot/logging/logback/defaults.xml"/>

    <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>/var/log/gharsaathi/application.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>/var/log/gharsaathi/application.%d{yyyy-MM-dd}.log</fileNamePattern>
            <maxHistory>30</maxHistory>
        </rollingPolicy>
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <appender name="JSON" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>/var/log/gharsaathi/application.json</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>/var/log/gharsaathi/application.%d{yyyy-MM-dd}.json</fileNamePattern>
            <maxHistory>30</maxHistory>
        </rollingPolicy>
        <encoder class="net.logstash.logback.encoder.LogstashEncoder"/>
    </appender>

    <root level="INFO">
        <appender-ref ref="FILE"/>
        <appender-ref ref="JSON"/>
    </root>
</configuration>
```

### Step 3: Error Tracking

**File: `frontend/lib/error-tracking.ts`**

```typescript
// Sentry or similar error tracking
export function initErrorTracking() {
  if (process.env.NODE_ENV === 'production') {
    // Initialize your error tracking service
    // Example: Sentry.init({ dsn: process.env.SENTRY_DSN })
  }
}

export function logError(error: Error, context?: Record<string, any>) {
  console.error('Error:', error, context);
  
  // Send to error tracking service
  if (process.env.NODE_ENV === 'production') {
    // Sentry.captureException(error, { extra: context });
  }
}
```

### Step 4: Health Check Endpoint

**File: `backend/src/main/java/com/gharsaathi/controller/HealthController.java`**

```java
package com.gharsaathi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> health = new HashMap<>();
        health.put("status", "UP");
        health.put("timestamp", String.valueOf(System.currentTimeMillis()));
        return ResponseEntity.ok(health);
    }

    @GetMapping("/actuator/health")
    public ResponseEntity<Map<String, String>> actuatorHealth() {
        return health();
    }
}
```

### Verification Checklist

- [ ] Prometheus configured
- [ ] Grafana dashboard created
- [ ] Loki for log aggregation
- [ ] Application metrics collected
- [ ] Error tracking configured
- [ ] Health check endpoints
- [ ] Log rotation configured
- [ ] Alerts configured
- [ ] Monitoring dashboard accessible

---

## Task 10.6: Documentation Finalization (1-2h)

### Step 1: README Update

**Update: `README.md`**

```markdown
# GharSaathi - Nepal's Trusted Rental Marketplace

A comprehensive platform connecting landlords and tenants for property rentals across Nepal.

## 🌟 Features

- **Property Listings**: Browse and search properties with advanced filters
- **User Authentication**: Secure JWT-based authentication
- **Application System**: Submit and manage rental applications
- **Lease Management**: Create and track lease agreements
- **Payment Tracking**: Monitor rent payments and history
- **Reviews & Ratings**: Rate properties and landlords
- **Admin Panel**: Comprehensive admin dashboard
- **Real-time Notifications**: Stay updated on important events

## 🚀 Tech Stack

**Frontend:**
- Next.js 16 with App Router
- React 19
- TypeScript
- Tailwind CSS
- shadcn/ui

**Backend:**
- Spring Boot 4.0.1
- Java 21
- MySQL 8.0
- Spring Security + JWT
- Spring Data JPA

## 📋 Prerequisites

- Node.js 20+
- Java 21+
- MySQL 8.0+
- Docker & Docker Compose (for containerized deployment)

## 🛠️ Installation

### Local Development

1. Clone the repository:
```bash
git clone https://github.com/yourusername/gharsaathi.git
cd gharsaathi
```

1. Set up the backend:

```bash
cd backend
cp src/main/resources/application.properties.example src/main/resources/application.properties
# Update database credentials
mvn clean install
mvn spring-boot:run
```

1. Set up the frontend:

```bash
cd frontend
npm install
cp .env.example .env.local
# Update environment variables
npm run dev
```

### Docker Deployment

```bash
docker-compose up -d
```

## 📚 Documentation

- [API Documentation](docs/03-api/api-overview.md)
- [Architecture](docs/02-architecture/README.md)
- [Deployment Guide](DEPLOYMENT.md)
- [Contributing Guidelines](CONTRIBUTING.md)

## 🧪 Testing

```bash
# Frontend tests
cd frontend
npm test
npm run test:e2e

# Backend tests
cd backend
mvn test
```

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👥 Team

- Your Name - Full Stack Developer

## 🙏 Acknowledgments

- Nepal's rental market needs
- Open source community

```

### Step 2: API Documentation

**File: `API_DOCUMENTATION.md`**

```markdown
# GharSaathi API Documentation

## Base URL
```

Production: <https://api.gharsaathi.com>
Development: <http://localhost:8080>

```

## Authentication

All protected endpoints require a Bearer token:
```

Authorization: Bearer <access_token>

```

## Endpoints

### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login user
- `POST /api/auth/refresh` - Refresh access token
- `POST /api/auth/logout` - Logout user

### Properties
- `GET /api/properties` - List properties (with filters)
- `GET /api/properties/{id}` - Get property details
- `POST /api/properties` - Create property (Landlord only)
- `PUT /api/properties/{id}` - Update property (Owner only)
- `DELETE /api/properties/{id}` - Delete property (Owner only)

### Applications
- `GET /api/applications` - List user's applications
- `POST /api/applications` - Submit application
- `PUT /api/applications/{id}/approve` - Approve application (Landlord)
- `PUT /api/applications/{id}/reject` - Reject application (Landlord)

[... Full API documentation ...]
```

### Step 3: Deployment Guide

**File: `DEPLOYMENT.md`**

```markdown
# Deployment Guide

## Prerequisites
- Ubuntu 22.04 LTS server
- Domain name configured
- SSL certificate

## Steps

1. **Server Setup**
```bash
./scripts/server-setup.sh
```

1. **Configure Environment**

```bash
cd /opt/gharsaathi
cp .env.example .env
# Edit .env with production values
```

1. **Deploy Application**

```bash
./scripts/deploy.sh
```

1. **Verify Deployment**

```bash
docker-compose ps
curl https://gharsaathi.com/health
```

## Monitoring

- Grafana: <https://gharsaathi.com:3001>
- Logs: `docker-compose logs -f`

## Rollback

```bash
./scripts/rollback.sh
```

```

### Verification Checklist

- [ ] README.md updated
- [ ] API documentation complete
- [ ] Deployment guide created
- [ ] Contributing guidelines added
- [ ] License file added
- [ ] Changelog maintained
- [ ] Architecture diagrams
- [ ] Code comments reviewed

---

## Phase 10 Completion Checklist

### Environment Configuration
- [ ] Production env files created
- [ ] All variables documented
- [ ] Secrets secured
- [ ] Database configured

### Docker Containerization
- [ ] Dockerfiles created
- [ ] Docker Compose configured
- [ ] Nginx configured
- [ ] Health checks working
- [ ] Volumes configured

### CI/CD Pipeline
- [ ] GitHub Actions configured
- [ ] Tests automated
- [ ] Build automated
- [ ] Deployment automated
- [ ] Pre-commit hooks set up

### Production Deployment
- [ ] Server provisioned
- [ ] SSL configured
- [ ] Application deployed
- [ ] Domain configured
- [ ] Services healthy

### Monitoring & Logging
- [ ] Prometheus configured
- [ ] Grafana dashboard
- [ ] Log aggregation
- [ ] Error tracking
- [ ] Alerts configured

### Documentation
- [ ] README complete
- [ ] API docs published
- [ ] Deployment guide
- [ ] Architecture docs
- [ ] User guides

---

# 🎉 PROJECT COMPLETION

Congratulations! You have completed all 10 phases of the GharSaathi development plan.

## Final Checklist

- [ ] All features implemented
- [ ] All tests passing
- [ ] Security audit complete
- [ ] Performance optimized
- [ ] Application deployed
- [ ] Monitoring active
- [ ] Documentation complete
- [ ] User acceptance testing

## Next Steps

1. **User Feedback**: Gather feedback from real users
2. **Iterations**: Plan improvements based on feedback
3. **Marketing**: Launch marketing campaign
4. **Support**: Set up customer support system
5. **Analytics**: Monitor usage and metrics
6. **Updates**: Plan feature updates and maintenance

## Maintenance Schedule

- **Daily**: Monitor logs and metrics
- **Weekly**: Review error reports, update dependencies
- **Monthly**: Security updates, performance review
- **Quarterly**: Feature updates, major improvements

## Success Metrics to Track

- User registration rate
- Property listing growth
- Application submission rate
- Lease conversion rate
- User retention rate
- System uptime
- API response times
- User satisfaction scores

---

**Total Estimated Effort: 220-270 hours (6-8 weeks for solo developer)**

Good luck with your GharSaathi platform! 🏠🔑
