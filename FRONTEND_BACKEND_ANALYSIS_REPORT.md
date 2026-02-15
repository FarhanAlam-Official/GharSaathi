# 🏠 GharSaathi - Complete Frontend & Backend Analysis Report

**Generated:** January 28, 2026  
**Project:** GharSaathi - Nepal's Trusted Rental Marketplace  
**Analysis Scope:** Full-Stack Architecture, Design System, API Integration Strategy

---

## 📋 Executive Summary

### Project Overview

GharSaathi is a modern property rental marketplace built with:

- **Frontend:** Next.js 16 with React 19, TypeScript, Tailwind CSS, shadcn/ui
- **Backend:** Spring Boot 4.0.1 with Java 21, MySQL, JWT Authentication
- **Architecture:** RESTful API, Role-Based Access Control (RBAC)

### Current Status

✅ **Backend:** 100% Complete - All APIs implemented and documented  
⚠️ **Frontend:** 30% Complete - Basic UI structure exists with mock data  
🔄 **Integration:** 0% Complete - No API connections established yet

### Key Finding

The frontend has a well-designed UI shell with mock data, but **no backend integration** exists. All components use static mock data from `lib/mock-data.ts`. A complete integration strategy is required.

---

## 🎨 FRONTEND ANALYSIS

### 1. Technology Stack

#### Core Framework

```json
{
  "framework": "Next.js 16.0.10",
  "react": "19.2.0",
  "typescript": "^5",
  "nodeVersion": "ES6"
}
```

#### UI Framework & Styling

- **Styling:** Tailwind CSS 4.1.9 with CSS Variables
- **Component Library:** shadcn/ui (New York style variant)
- **Icons:** Lucide React (0.454.0)
- **Theme System:** next-themes with dark mode support
- **Animation:** tailwindcss-animate, tw-animate-css

#### Form & Validation

- **Forms:** React Hook Form 7.60.0
- **Validation:** Zod 3.25.76
- **Resolvers:** @hookform/resolvers 3.10.0

#### State & Data

- **Charts:** Recharts 2.15.4
- **Date Handling:** date-fns 4.1.0, react-day-picker 9.8.0
- **Toast Notifications:** Sonner 1.7.4

### 2. Design System Analysis

#### Color Palette (Brand Identity)

**Primary Theme Colors:**

```css
/* Light Mode */
--primary: oklch(0.541 0.214 262.881)        /* Indigo Primary */
--background: oklch(0.985 0.002 247.858)      /* Near White */
--foreground: oklch(0.145 0.024 264.364)      /* Dark Gray */
--card: oklch(1 0 0)                          /* Pure White */

/* Dark Mode */
--primary: oklch(0.541 0.214 262.881)        /* Indigo (consistent) */
--background: oklch(0.141 0.033 264.364)      /* Deep Dark Blue */
--foreground: oklch(0.985 0.01 264.364)       /* Near White */
--card: oklch(0.18 0.04 264.364)              /* Dark Card */
```

**Custom Semantic Colors:**

```css
--success: oklch(0.627 0.194 149.214)        /* Green */
--warning: oklch(0.769 0.188 70.08)          /* Yellow */
--info: oklch(0.623 0.214 259.815)           /* Blue */
--destructive: oklch(0.577 0.245 27.325)     /* Red */
```

**Border Radius System:**

```css
--radius: 0.625rem (10px)                    /* Base */
--radius-sm: calc(var(--radius) - 4px)       /* 6px */
--radius-md: calc(var(--radius) - 2px)       /* 8px */
--radius-lg: var(--radius)                   /* 10px */
--radius-xl: calc(var(--radius) + 4px)       /* 14px */
```

#### Typography

- **Primary Font:** Geist (Sans-serif)
- **Monospace Font:** Geist Mono
- **Font Loading:** Next.js font optimization
- **Text Styles:** Antialiased rendering

#### Design Principles

1. **Modern & Clean:** Rounded corners (10px base), generous white space
2. **Accessible:** High contrast ratios, semantic color usage
3. **Consistent:** CSS variable-based theming throughout
4. **Professional:** Indigo primary color conveys trust and reliability
5. **Nepal-Focused:** Greeting messages like "Namaste" add local context

### 3. Page Structure & Routes

#### Implemented Pages

```
Frontend Routes:
/                              → Landing Page (Public)
/properties                    → Property Listings (Public)
/properties/[id]               → Property Details (Public)

/auth/login                    → Login Page (Public)
/auth/register                 → Registration Page (Public)

/tenant/dashboard              → Tenant Dashboard (Protected)
/tenant/applications           → Tenant Applications (Protected)
/tenant/saved                  → Saved Properties (Protected)

/landlord/dashboard            → Landlord Dashboard (Protected)
/landlord/properties           → Landlord Properties (Protected)
/landlord/requests             → Tenant Requests (Protected)

/admin/*                       → Admin Panel (Protected)
```

#### Route Protection Status

⚠️ **Critical Issue:** No route protection middleware implemented

- All routes are accessible without authentication
- No JWT token validation on client side
- No role-based access control (RBAC) enforcement
- No redirect logic for unauthorized access

### 4. Component Architecture

#### Core Components (Custom)

**Navigation Components:**

- `navbar.tsx` - Responsive navigation with role-based variants
- `footer.tsx` - Site footer
- `dashboard-sidebar.tsx` - Role-specific sidebar navigation

**Business Logic Components:**

- `property-card.tsx` - Property listing card (CRITICAL: Uses mock data)
- `stats-card.tsx` - Dashboard statistics display

**Layout Components:**

- `theme-provider.tsx` - Dark/light mode management

#### UI Component Library (shadcn/ui)

**59 components** available in `/components/ui/`:

- Forms: input, textarea, select, checkbox, radio-group, switch
- Feedback: toast, alert, alert-dialog, sonner
- Navigation: tabs, menubar, navigation-menu, pagination
- Overlays: dialog, sheet, drawer, popover, hover-card
- Data Display: table, card, avatar, badge, separator
- Charts: chart.tsx (with Recharts integration)
- Layout: scroll-area, resizable, sidebar
- And many more...

### 5. Mock Data Analysis

#### Current Data Structure (lib/mock-data.ts)

**Interfaces Defined:**

```typescript
Property {
  id, title, location, city, price, currency
  bedrooms, bathrooms, area, type, status
  verified, featured, isNew, rating
  image, amenities[], landlord?
}

User {
  id, name, email, role, avatar, phone
  status, joinedDate, propertiesCount, activity
}

LeaseInfo {
  id, propertyId, propertyTitle, propertyAddress
  tenantName, tenantEmail, startDate, endDate
  monthlyRent, status
}

RentPayment {
  id, month, amount, status, paidDate
}

TenantRequest, Visit, Application
```

#### Mock Data Sets

- `mockProperties` - 10+ sample properties
- Sample users, leases, payments, applications
- **Issue:** All components consume this mock data directly
- **Impact:** Complete refactoring needed to consume real APIs

### 6. Frontend Gaps & Issues

#### Critical Issues

1. **No API Integration Layer**
   - No API client/service layer
   - No HTTP client configuration (Axios/Fetch wrapper)
   - No base URL configuration for backend
   - No error handling for API calls

2. **No Authentication State Management**
   - No JWT token storage/retrieval
   - No authentication context provider
   - No user session management
   - No token refresh logic

3. **No State Management**
   - No global state management (Redux/Zustand/Context)
   - Each component manages its own state
   - No shared state for user, properties, etc.

4. **No Route Protection**
   - Missing authentication middleware
   - No role-based route guards
   - Public access to all routes

5. **Missing Form Handling**
   - Login/Register forms have no submit handlers
   - Property creation forms not connected
   - Application submission not functional

#### Missing Features

1. **File Upload Integration**
   - Backend has file upload API (`/api/files/*`)
   - Frontend has no file upload UI implementation
   - Property images rely on placeholder URLs

2. **Real-time Features**
   - No notification system connected
   - No real-time updates (WebSocket/polling)

3. **Search & Filtering**
   - Search UI exists but not functional
   - Filter forms not connected to backend

4. **Payment Integration**
   - Payment display components exist
   - No payment gateway integration
   - No payment status updates

---

## 🔧 BACKEND ANALYSIS

### 1. Technology Stack

#### Core Framework

```xml
<spring-boot-version>4.0.1</spring-boot-version>
<java-version>21</java-version>
<mysql-connector>runtime</mysql-connector>
```

#### Security & Authentication

- **Spring Security:** JWT-based authentication
- **JWT Library:** jjwt 0.12.6 (api, impl, jackson)
- **Token Types:** Access token (1 hour), Refresh token (7 days)
- **Password:** BCrypt hashing

#### Database

- **ORM:** Spring Data JPA / Hibernate
- **Database:** MySQL (port 3307)
- **Database Name:** gharsaathi_db
- **DDL:** Auto-update mode

### 2. Package Structure

```
com.gharsaathi/
├── auth/                      # Authentication & User Management
│   ├── model/                 # User, Role, RefreshToken entities
│   ├── dto/                   # Login, Register, Auth responses
│   ├── service/               # AuthService, RefreshTokenService
│   ├── controller/            # AuthController
│   └── repository/            # UserRepository, RefreshTokenRepository
│
├── property/                  # Property Management
│   ├── model/                 # Property, PropertyType, PropertyStatus
│   ├── dto/                   # Property requests/responses
│   ├── service/               # PropertyService
│   ├── controller/            # PropertyController
│   └── repository/            # PropertyRepository
│
├── rental.application/        # Rental Applications (Refactored)
│   ├── model/                 # RentalApplication, ApplicationStatus
│   ├── dto/                   # Application requests/responses
│   ├── service/               # RentalApplicationService
│   ├── controller/            # RentalApplicationController
│   └── repository/            # RentalApplicationRepository
│
├── lease/                     # Lease Management
│   ├── model/                 # Lease, LeaseStatus
│   ├── dto/                   # Lease requests/responses
│   ├── service/               # LeaseService (auto-creates from applications)
│   ├── controller/            # LeaseController
│   └── repository/            # LeaseRepository
│
├── payment/                   # Payment System
│   ├── model/                 # Payment, PaymentStatus, PaymentType
│   ├── dto/                   # Payment requests/responses/statistics
│   ├── service/               # PaymentService (auto-generates from leases)
│   ├── controller/            # PaymentController
│   └── repository/            # PaymentRepository
│
├── profile/                   # User Profile Management
│   ├── dto/                   # ProfileDTO, UpdateProfileRequest
│   ├── service/               # ProfileService
│   └── controller/            # ProfileController
│
├── notification/              # Notification System
│   ├── model/                 # Notification, NotificationType
│   ├── dto/                   # NotificationDTO
│   ├── service/               # NotificationService
│   ├── controller/            # NotificationController
│   └── repository/            # NotificationRepository
│
├── dashboard/                 # Role-based Dashboards
│   ├── dto/                   # TenantDashboard, LandlordDashboard, AdminDashboard
│   ├── service/               # DashboardService
│   └── controller/            # DashboardController
│
├── property.review/           # Property Reviews
│   ├── model/                 # PropertyReview
│   ├── dto/                   # Review requests/responses
│   ├── service/               # PropertyReviewService
│   ├── controller/            # PropertyReviewController
│   └── repository/            # PropertyReviewRepository
│
├── fileupload/                # File Upload Service
│   ├── model/                 # UploadedFile
│   ├── dto/                   # FileUploadResponse
│   ├── service/               # FileUploadService
│   ├── controller/            # FileUploadController
│   └── repository/            # UploadedFileRepository
│
├── admin/                     # Admin User Management
│   ├── dto/                   # AdminUserDTO, role/suspend requests
│   ├── service/               # AdminUserService
│   └── controller/            # AdminUserController
│
└── common/                    # Shared Resources
    ├── dto/                   # Shared DTOs (CreatePropertyRequest, etc.)
    ├── exception/             # GlobalExceptionHandler, Custom Exceptions
    └── security/              # JwtService, SecurityConfig, JwtAuthFilter
```

### 3. API Endpoints Summary

#### 🔐 Authentication Module (`/api/auth`)

```
POST   /register              - Create account
POST   /login                 - Authenticate user
POST   /refresh               - Refresh access token
POST   /logout                - Logout (invalidate token)
POST   /logout/all            - Logout from all devices
GET    /health                - Health check
```

#### 🏠 Property Module (`/api/properties`, `/api/landlord/properties`)

```
PUBLIC ENDPOINTS:
GET    /properties                           - List all properties (paginated)
GET    /properties/{id}                      - Property details
POST   /properties/search                    - Search with filters

LANDLORD ENDPOINTS:
GET    /landlord/properties                  - My properties
POST   /landlord/properties                  - Create property
PUT    /landlord/properties/{id}             - Update property
DELETE /landlord/properties/{id}             - Soft delete
PATCH  /landlord/properties/{id}/status      - Change status

ADMIN ENDPOINTS:
DELETE /admin/properties/{id}                - Force delete
```

#### 📝 Rental Applications (`/api/tenant/applications`, `/api/landlord/applications`)

```
TENANT ENDPOINTS:
POST   /tenant/applications                  - Submit application
GET    /tenant/applications                  - My applications
PATCH  /tenant/applications/{id}/withdraw    - Withdraw application

LANDLORD ENDPOINTS:
GET    /landlord/applications                - Applications for my properties
PATCH  /landlord/applications/{id}/approve   - Approve application
PATCH  /landlord/applications/{id}/reject    - Reject application
```

#### 📋 Lease Management (`/api/tenant/leases`, `/api/landlord/leases`)

```
TENANT ENDPOINTS:
GET    /tenant/leases                        - My leases
GET    /tenant/leases/{id}                   - Lease details
GET    /tenant/leases/active                 - Active leases only

LANDLORD ENDPOINTS:
GET    /landlord/leases                      - Leases for my properties
GET    /landlord/leases/{id}                 - Lease details
POST   /landlord/leases/{id}/terminate       - Terminate lease
```

#### 💳 Payment System (`/api/payments`)

```
GET    /payments/{paymentId}                         - Payment details
GET    /payments/lease/{leaseId}                     - Payments by lease

TENANT:
GET    /payments/tenant/{tenantId}                   - My payments (paginated)
PUT    /payments/{paymentId}/mark-paid               - Mark as paid
GET    /payments/tenant/{tenantId}/statistics        - Payment statistics
GET    /payments/tenant/{tenantId}/upcoming          - Upcoming payments

LANDLORD:
GET    /payments/landlord/{landlordId}               - Tenant payments (paginated)
PUT    /payments/{paymentId}/confirm                 - Confirm payment
GET    /payments/landlord/{landlordId}/statistics    - Revenue statistics
```

#### 👤 Profile Management (`/api/users/profile`)

```
GET    /profile                              - Get my profile
PUT    /profile                              - Update profile
PATCH  /profile/password                     - Change password
POST   /profile/verify-email                 - Verify email
```

#### 🔔 Notifications (`/api/notifications`)

```
GET    /notifications                        - All notifications
GET    /notifications/unread                 - Unread only
GET    /notifications/read                   - Read only
GET    /notifications/type/{type}            - By type
PATCH  /notifications/{id}/read              - Mark as read
PATCH  /notifications/read-all               - Mark all as read
DELETE /notifications/{id}                   - Delete notification
POST   /notifications (ADMIN only)           - Create notification
```

#### 📊 Dashboards (`/api/dashboard`)

```
GET    /dashboard/tenant                     - Tenant dashboard
GET    /dashboard/landlord                   - Landlord dashboard
GET    /dashboard/admin                      - Admin dashboard
```

#### ⭐ Property Reviews (`/api/reviews`)

```
GET    /reviews/property/{propertyId}        - Reviews for property
POST   /reviews                              - Submit review (TENANT)
PUT    /reviews/{id}                         - Update my review
DELETE /reviews/{id}                         - Delete my review
PATCH  /reviews/{id}/verify (ADMIN)          - Verify review
```

#### 📁 File Upload (`/api/files`)

```
POST   /files/upload                         - Upload file
GET    /files/{id}                           - Download file
DELETE /files/{id}                           - Delete file
GET    /files/property/{propertyId}          - Files by property
```

#### 👥 Admin User Management (`/api/admin/users`)

```
GET    /admin/users                          - All users (with role filter)
GET    /admin/users/suspended                - Suspended users
GET    /admin/users/{userId}                 - User details
PATCH  /admin/users/{userId}/suspend         - Suspend user
PATCH  /admin/users/{userId}/activate        - Activate user
PATCH  /admin/users/{userId}/role            - Change role
DELETE /admin/users/{userId}                 - Delete user
```

### 4. Database Schema

#### Core Entities

**users**

- id, email, password_hash, first_name, last_name
- role (TENANT, LANDLORD, ADMIN)
- phone_number, avatar_url, email_verified
- is_active, is_suspended, created_at, updated_at

**properties**

- id, title, description, property_type, status
- address, city, area, latitude, longitude
- price, security_deposit, bedrooms, bathrooms, property_area
- furnished, parking_available, amenities (JSON)
- available_from, landlord_id, created_at, updated_at

**rental_applications**

- id, property_id, tenant_id, status
- message, move_in_date, lease_duration_months
- number_of_occupants, employment_status
- annual_income, has_pets, emergency_contact
- created_at, updated_at, reviewed_at, review_notes

**leases**

- id, property_id, tenant_id, landlord_id, application_id
- lease_start_date, lease_end_date, monthly_rent, security_deposit
- status (DRAFT, ACTIVE, EXPIRED, TERMINATED, RENEWED)
- number_of_occupants, special_terms, auto_renew
- early_termination_notice_days, termination_date, termination_reason
- created_at, updated_at, signed_at

**payments**

- id, lease_id, tenant_id, landlord_id, property_id
- payment_type (RENT, SECURITY_DEPOSIT, LATE_FEE, MAINTENANCE)
- amount, due_date, paid_date, status
- payment_method, transaction_reference, month_year
- notes, late_fee, confirmed_by_landlord, confirmation_date
- created_at, updated_at

**notifications**

- id, user_id, title, message, type, reference_url
- is_read, created_at, read_at

**property_reviews**

- id, property_id, tenant_id, lease_id
- rating (1-5), comment, is_verified
- created_at, updated_at, verified_at, verified_by

**uploaded_files**

- id, filename, original_filename, file_path, file_size
- content_type, uploaded_by, entity_type, entity_id
- created_at

**refresh_tokens**

- id, token, user_id, expiry_date, is_revoked, created_at

### 5. Security Configuration

#### JWT Configuration

```properties
jwt.secret: 7082cee86dfba39cda1108cec1eccdb53080fcb4af596e72acf89b0e481125ba
jwt.expiration: 3600000 (1 hour)
jwt.refresh-expiration: 604800000 (7 days)
jwt.issuer: gharsaathi
```

#### CORS Configuration

```java
@CrossOrigin(origins = "http://localhost:3000")  // Auth endpoints
@CrossOrigin(origins = "*", maxAge = 3600)       // Property endpoints
```

⚠️ **Security Issue:** Wildcard CORS should be restricted in production

#### Role-Based Access Control

- `@PreAuthorize("hasRole('TENANT')")`
- `@PreAuthorize("hasRole('LANDLORD')")`
- `@PreAuthorize("hasRole('ADMIN')")`
- `@PreAuthorize("hasAnyRole('TENANT', 'LANDLORD', 'ADMIN')")`
- `@PreAuthorize("isAuthenticated()")`

### 6. Backend Features

#### ✅ Implemented Features

1. **Complete Authentication System**
   - User registration with validation
   - Login with JWT tokens
   - Refresh token mechanism
   - Logout (single device & all devices)
   - Password hashing (BCrypt)

2. **Property Management**
   - CRUD operations for properties
   - Search and filtering
   - Status management (AVAILABLE, RENTED, etc.)
   - Soft delete
   - Image management (via file upload)
   - Public browsing

3. **Rental Application Workflow**
   - Tenant application submission
   - Landlord approval/rejection
   - Duplicate prevention
   - Status tracking
   - Auto-lease creation on approval

4. **Lease Management**
   - Auto-creation from approved applications
   - Manual lease creation
   - Lease termination
   - Status transitions
   - Date validation

5. **Payment System**
   - Auto-generation of monthly payments from leases
   - Tenant payment marking
   - Landlord confirmation
   - Payment statistics
   - Overdue tracking
   - Late fee calculation

6. **Notification System**
   - Application status notifications
   - Payment reminders
   - Lease updates
   - Type-based filtering
   - Read/unread tracking

7. **Role-Based Dashboards**
   - Tenant dashboard (applications, leases, payments)
   - Landlord dashboard (revenue, occupancy, leads)
   - Admin dashboard (platform statistics)

8. **Property Reviews**
   - Tenant reviews for leased properties
   - Rating system (1-5 stars)
   - Admin verification
   - Review editing/deletion

9. **File Upload Service**
   - Multiple file upload
   - Property images
   - Document attachments
   - File metadata tracking

10. **Admin Controls**
    - User management (suspend/activate)
    - Role changes
    - User deletion
    - Property force delete

---

## 🔗 FRONTEND-BACKEND INTEGRATION STRATEGY

### 1. Critical Integration Gaps

#### Gap Analysis Table

| Feature | Frontend Status | Backend Status | Integration Required |
|---------|----------------|----------------|---------------------|
| **Authentication** | UI complete, no logic | ✅ Complete | High Priority |
| **Property Listing** | UI with mock data | ✅ Complete | High Priority |
| **Property Details** | UI with mock data | ✅ Complete | High Priority |
| **Property Search** | UI exists | ✅ Complete | High Priority |
| **Tenant Dashboard** | UI with mock stats | ✅ Complete | High Priority |
| **Landlord Dashboard** | UI with mock stats | ✅ Complete | High Priority |
| **Applications** | UI with mock data | ✅ Complete | Medium Priority |
| **Lease Management** | UI with mock data | ✅ Complete | Medium Priority |
| **Payments** | UI with mock data | ✅ Complete | Medium Priority |
| **Notifications** | No UI | ✅ Complete | Medium Priority |
| **Profile Management** | No UI | ✅ Complete | Low Priority |
| **File Upload** | No UI | ✅ Complete | Low Priority |
| **Reviews** | No UI | ✅ Complete | Low Priority |
| **Admin Panel** | Placeholder only | ✅ Complete | Low Priority |

### 2. Required Infrastructure

#### A. API Client Setup

**Create API configuration layer:**

```typescript
// lib/api/client.ts
export const API_CONFIG = {
  baseURL: process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080/api',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json'
  }
}

// lib/api/axios.ts
import axios from 'axios'

export const apiClient = axios.create({
  baseURL: API_CONFIG.baseURL,
  timeout: API_CONFIG.timeout
})

// Request interceptor for JWT
apiClient.interceptors.request.use((config) => {
  const token = localStorage.getItem('accessToken')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

// Response interceptor for token refresh
apiClient.interceptors.response.use(
  (response) => response,
  async (error) => {
    if (error.response?.status === 401) {
      // Handle token refresh logic
    }
    return Promise.reject(error)
  }
)
```

#### B. Authentication Context

**Create auth provider:**

```typescript
// contexts/AuthContext.tsx
interface AuthContextType {
  user: User | null
  login: (email: string, password: string) => Promise<void>
  register: (data: RegisterRequest) => Promise<void>
  logout: () => Promise<void>
  refreshToken: () => Promise<void>
  isAuthenticated: boolean
  isLoading: boolean
}

export function AuthProvider({ children }) {
  const [user, setUser] = useState<User | null>(null)
  const [isLoading, setIsLoading] = useState(true)
  
  // Implementation...
  
  return (
    <AuthContext.Provider value={{ user, login, register, logout, ... }}>
      {children}
    </AuthContext.Provider>
  )
}
```

#### C. API Service Layer

**Create service modules:**

```typescript
// services/auth.service.ts
export const authService = {
  login: (credentials) => apiClient.post('/auth/login', credentials),
  register: (data) => apiClient.post('/auth/register', data),
  logout: () => apiClient.post('/auth/logout'),
  refresh: (token) => apiClient.post('/auth/refresh', { token })
}

// services/property.service.ts
export const propertyService = {
  getAll: (params) => apiClient.get('/properties', { params }),
  getById: (id) => apiClient.get(`/properties/${id}`),
  search: (criteria) => apiClient.post('/properties/search', criteria),
  create: (data) => apiClient.post('/landlord/properties', data),
  update: (id, data) => apiClient.put(`/landlord/properties/${id}`, data),
  delete: (id) => apiClient.delete(`/landlord/properties/${id}`)
}

// services/application.service.ts
export const applicationService = {
  submit: (data) => apiClient.post('/tenant/applications', data),
  getMyApplications: (params) => apiClient.get('/tenant/applications', { params }),
  withdraw: (id) => apiClient.patch(`/tenant/applications/${id}/withdraw`)
}

// ... More services for lease, payment, notification, etc.
```

#### D. TypeScript Type Definitions

**Create type definitions matching backend DTOs:**

```typescript
// types/property.types.ts
export interface Property {
  id: number
  title: string
  description: string
  propertyType: PropertyType
  status: PropertyStatus
  address: string
  city: string
  area: string
  price: number
  securityDeposit: number
  bedrooms: number
  bathrooms: number
  propertyArea: number
  furnished: boolean
  amenities: string[]
  images: PropertyImage[]
  landlord: LandlordInfo
  createdAt: string
  updatedAt: string
}

export type PropertyType = 'APARTMENT' | 'HOUSE' | 'ROOM' | 'COMMERCIAL' | 'LAND'
export type PropertyStatus = 'AVAILABLE' | 'RENTED' | 'MAINTENANCE' | 'UNAVAILABLE'

// types/auth.types.ts
export interface User {
  id: number
  email: string
  firstName: string
  lastName: string
  role: Role
  phoneNumber?: string
  avatarUrl?: string
  emailVerified: boolean
  createdAt: string
}

export type Role = 'TENANT' | 'LANDLORD' | 'ADMIN'

// ... More type definitions
```

#### E. Route Protection Middleware

**Create protected route wrapper:**

```typescript
// components/ProtectedRoute.tsx
export function ProtectedRoute({ 
  children, 
  allowedRoles 
}: { 
  children: ReactNode
  allowedRoles?: Role[]
}) {
  const { user, isLoading, isAuthenticated } = useAuth()
  const router = useRouter()
  
  useEffect(() => {
    if (!isLoading && !isAuthenticated) {
      router.push('/auth/login')
    }
    
    if (allowedRoles && user && !allowedRoles.includes(user.role)) {
      router.push('/unauthorized')
    }
  }, [user, isLoading, isAuthenticated, router])
  
  if (isLoading) return <LoadingSpinner />
  if (!isAuthenticated) return null
  
  return <>{children}</>
}
```

#### F. State Management (Optional but Recommended)

**Consider using Zustand for global state:**

```typescript
// store/usePropertyStore.ts
import create from 'zustand'

interface PropertyStore {
  properties: Property[]
  selectedProperty: Property | null
  filters: PropertyFilters
  isLoading: boolean
  error: string | null
  
  fetchProperties: (params) => Promise<void>
  setFilters: (filters) => void
  clearFilters: () => void
}

export const usePropertyStore = create<PropertyStore>((set) => ({
  properties: [],
  selectedProperty: null,
  filters: {},
  isLoading: false,
  error: null,
  
  fetchProperties: async (params) => {
    set({ isLoading: true })
    try {
      const response = await propertyService.getAll(params)
      set({ properties: response.data.properties, isLoading: false })
    } catch (error) {
      set({ error: error.message, isLoading: false })
    }
  },
  
  // ... more actions
}))
```

### 3. Component Refactoring Required

#### High Priority Refactoring

**1. Property Card Component**

```typescript
// Current: Uses mock data
import { mockProperties } from '@/lib/mock-data'

// Required: Use API data
import { usePropertyStore } from '@/store/usePropertyStore'

export function PropertyCard({ propertyId }: { propertyId: number }) {
  const { properties, fetchProperties } = usePropertyStore()
  const property = properties.find(p => p.id === propertyId)
  
  useEffect(() => {
    fetchProperties()
  }, [])
  
  // ... render with real data
}
```

**2. Login Page**

```typescript
// Current: No submit handler
<form>
  <Input type="email" />
  <Input type="password" />
  <Button type="submit">Log In</Button>
</form>

// Required: Add form handling
import { useAuth } from '@/contexts/AuthContext'
import { useForm } from 'react-hook-form'
import { zodResolver } from '@hookform/resolvers/zod'

export default function LoginPage() {
  const { login } = useAuth()
  const { register, handleSubmit, formState: { errors } } = useForm({
    resolver: zodResolver(loginSchema)
  })
  
  const onSubmit = async (data) => {
    try {
      await login(data.email, data.password)
      router.push('/tenant/dashboard')
    } catch (error) {
      toast.error(error.message)
    }
  }
  
  return (
    <form onSubmit={handleSubmit(onSubmit)}>
      <Input {...register('email')} error={errors.email} />
      <Input {...register('password')} type="password" error={errors.password} />
      <Button type="submit">Log In</Button>
    </form>
  )
}
```

**3. Dashboard Components**

```typescript
// Current: Mock statistics
const stats = {
  activeApplications: 2,
  savedProperties: 12,
  upcomingViewings: 1
}

// Required: Fetch from API
const { data: dashboardData } = useTenantDashboard()

const stats = {
  activeApplications: dashboardData.activeApplicationsCount,
  savedProperties: dashboardData.savedPropertiesCount,
  upcomingViewings: dashboardData.upcomingViewingsCount
}
```

### 4. New Components Required

#### Components to Build

1. **Notification System**
   - Notification bell with unread count
   - Notification dropdown
   - Notification page

2. **File Upload**
   - Image uploader for properties
   - Drag-and-drop interface
   - Image preview and management

3. **Profile Management**
   - Profile view/edit page
   - Password change form
   - Avatar upload

4. **Admin Dashboard**
   - User management table
   - Property approval system
   - Analytics charts

5. **Review System**
   - Review submission form
   - Review display component
   - Rating stars component

6. **Payment UI**
   - Payment history table
   - Payment marking form
   - Payment statistics charts

### 5. Environment Configuration

**Required environment variables:**

```bash
# .env.local (Frontend)
NEXT_PUBLIC_API_URL=http://localhost:8080/api
NEXT_PUBLIC_APP_NAME=GharSaathi
NEXT_PUBLIC_FILE_UPLOAD_MAX_SIZE=10485760

# application.properties (Backend - already configured)
spring.datasource.url=jdbc:mysql://localhost:3307/gharsaathi_db
jwt.secret=7082cee86dfba39cda1108cec1eccdb53080fcb4af596e72acf89b0e481125ba
jwt.expiration=3600000
```

---

## 📝 IMPLEMENTATION ROADMAP

### Phase 1: Foundation (Week 1-2)

**Priority: CRITICAL**

#### 1.1 API Infrastructure

- [ ] Install Axios: `npm install axios`
- [ ] Create `/lib/api/client.ts` - API client configuration
- [ ] Create `/lib/api/axios.ts` - Axios instance with interceptors
- [ ] Add environment variable: `NEXT_PUBLIC_API_URL`
- [ ] Test connection to backend

#### 1.2 Authentication System

- [ ] Create `/contexts/AuthContext.tsx` - Authentication context
- [ ] Create `/services/auth.service.ts` - Auth API service
- [ ] Create `/types/auth.types.ts` - Auth type definitions
- [ ] Implement token storage (localStorage/cookies)
- [ ] Implement token refresh logic
- [ ] Add auth provider to `app/layout.tsx`

#### 1.3 Route Protection

- [ ] Create `/components/ProtectedRoute.tsx` - Route guard
- [ ] Create `/middleware.ts` - Next.js middleware for auth
- [ ] Protect tenant routes with TENANT role
- [ ] Protect landlord routes with LANDLORD role
- [ ] Protect admin routes with ADMIN role
- [ ] Add redirect logic for unauthorized access

#### 1.4 Form Integration

- [ ] Connect login form to `/api/auth/login`
- [ ] Connect register form to `/api/auth/register`
- [ ] Add form validation with Zod schemas
- [ ] Add error handling and toast notifications
- [ ] Test successful login flow

### Phase 2: Property Module (Week 3-4)

**Priority: HIGH**

#### 2.1 Property Service Layer

- [ ] Create `/services/property.service.ts`
- [ ] Create `/types/property.types.ts`
- [ ] Implement `getAll()`, `getById()`, `search()` methods
- [ ] Implement `create()`, `update()`, `delete()` methods

#### 2.2 Property Pages

- [ ] Refactor `/properties/page.tsx` - Fetch from API
- [ ] Refactor `/properties/[id]/page.tsx` - Fetch property details
- [ ] Update `PropertyCard` component to use real data
- [ ] Implement search functionality
- [ ] Add pagination controls
- [ ] Add loading states and skeletons

#### 2.3 Landlord Property Management

- [ ] Create property creation form
- [ ] Create property edit form
- [ ] Implement property deletion
- [ ] Implement status updates
- [ ] Refactor `/landlord/properties/page.tsx`

### Phase 3: Dashboards (Week 5)

**Priority: HIGH**

#### 3.1 Dashboard Services

- [ ] Create `/services/dashboard.service.ts`
- [ ] Create dashboard type definitions
- [ ] Test dashboard endpoints

#### 3.2 Dashboard Pages

- [ ] Refactor `/tenant/dashboard/page.tsx` - Fetch tenant dashboard
- [ ] Refactor `/landlord/dashboard/page.tsx` - Fetch landlord dashboard
- [ ] Create `/admin/dashboard/page.tsx` - Admin dashboard
- [ ] Update `StatsCard` components with real data
- [ ] Add charts for revenue/statistics

### Phase 4: Application & Lease (Week 6-7)

**Priority: MEDIUM**

#### 4.1 Application System

- [ ] Create `/services/application.service.ts`
- [ ] Create application type definitions
- [ ] Build application submission form
- [ ] Refactor tenant applications page
- [ ] Refactor landlord applications page
- [ ] Implement approve/reject/withdraw actions

#### 4.2 Lease System

- [ ] Create `/services/lease.service.ts`
- [ ] Create lease type definitions
- [ ] Build lease display components
- [ ] Implement lease termination
- [ ] Add lease history view

### Phase 5: Payment System (Week 8)

**Priority: MEDIUM**

#### 5.1 Payment Service

- [ ] Create `/services/payment.service.ts`
- [ ] Create payment type definitions
- [ ] Test payment endpoints

#### 5.2 Payment UI

- [ ] Build payment history table
- [ ] Build payment marking form (tenant)
- [ ] Build payment confirmation form (landlord)
- [ ] Add payment statistics display
- [ ] Add upcoming payments widget

### Phase 6: Supporting Features (Week 9-10)

**Priority: MEDIUM-LOW**

#### 6.1 Notification System

- [ ] Create `/services/notification.service.ts`
- [ ] Build notification bell component
- [ ] Build notification dropdown
- [ ] Build notification page
- [ ] Implement mark as read
- [ ] Add real-time updates (optional)

#### 6.2 File Upload

- [ ] Create `/services/file.service.ts`
- [ ] Build image uploader component
- [ ] Integrate with property forms
- [ ] Add image preview and management
- [ ] Handle file validation

#### 6.3 Profile Management

- [ ] Create `/services/profile.service.ts`
- [ ] Build profile view page
- [ ] Build profile edit form
- [ ] Build password change form
- [ ] Add avatar upload

#### 6.4 Review System

- [ ] Create `/services/review.service.ts`
- [ ] Build review submission form
- [ ] Build review display component
- [ ] Add rating stars component
- [ ] Integrate with property details page

### Phase 7: Admin Panel (Week 11-12)

**Priority: LOW**

#### 7.1 Admin Services

- [ ] Create `/services/admin.service.ts`
- [ ] Create admin type definitions

#### 7.2 Admin Pages

- [ ] Build user management table
- [ ] Build user detail view
- [ ] Implement suspend/activate actions
- [ ] Implement role change
- [ ] Build admin dashboard
- [ ] Add platform statistics

### Phase 8: Testing & Optimization (Week 13-14)

**Priority: ONGOING**

#### 8.1 Testing

- [ ] Test all authentication flows
- [ ] Test all CRUD operations
- [ ] Test role-based access
- [ ] Test error handling
- [ ] Test loading states

#### 8.2 Optimization

- [ ] Add request caching (React Query)
- [ ] Optimize image loading
- [ ] Add error boundaries
- [ ] Improve loading states
- [ ] Add retry logic for failed requests

#### 8.3 Documentation

- [ ] Document API integration
- [ ] Document component usage
- [ ] Document state management
- [ ] Create developer guide

---

## 🚨 CRITICAL ISSUES TO ADDRESS

### Security Issues

1. **CORS Configuration**
   - Backend: `@CrossOrigin(origins = "*")` on some endpoints
   - **Fix:** Restrict to specific frontend origin in production

   ```java
   @CrossOrigin(origins = "${app.frontend.url}", maxAge = 3600)
   ```

2. **JWT Secret in Code**
   - JWT secret hardcoded in `application.properties`
   - **Fix:** Use environment variables in production

   ```properties
   jwt.secret=${JWT_SECRET}
   ```

3. **No HTTPS Configuration**
   - Development uses HTTP
   - **Fix:** Configure SSL for production deployment

4. **Token Storage**
   - Frontend needs secure token storage
   - **Consider:** HttpOnly cookies vs localStorage trade-offs

### Data Consistency Issues

1. **Mock Data Structure Mismatch**
   - Frontend mock data types don't match backend DTOs exactly
   - **Fix:** Generate TypeScript types from backend OpenAPI spec

2. **Date Format Inconsistency**
   - Backend: ISO 8601 format
   - Frontend: Various string formats in mock data
   - **Fix:** Use date-fns for consistent parsing

3. **Currency Format**
   - Backend: BigDecimal (NPR)
   - Frontend: Number with manual currency symbol
   - **Fix:** Use proper number formatting library

### Missing Features

1. **No Error Boundary**
   - Frontend has no error boundaries
   - **Fix:** Add React error boundaries

2. **No Loading States**
   - Most components don't show loading indicators
   - **Fix:** Add loading skeletons

3. **No Offline Support**
   - No PWA features
   - **Consider:** Add service worker for offline mode

4. **No Image Optimization**
   - Images use placeholder URLs
   - **Fix:** Implement proper image upload and Next.js Image optimization

---

## 💡 RECOMMENDATIONS

### Immediate Actions (Week 1)

1. **Set up API client infrastructure** - Foundation for all integrations
2. **Implement authentication** - Blocking all protected routes
3. **Connect property listing** - Most visible feature
4. **Test end-to-end flow** - Ensure backend-frontend communication works

### Short Term (Month 1)

1. **Complete user dashboards** - Primary user experience
2. **Implement application workflow** - Core business logic
3. **Add file upload** - Required for property images
4. **Build notification system** - User engagement

### Medium Term (Month 2-3)

1. **Complete payment system** - Revenue tracking
2. **Build admin panel** - Platform management
3. **Add review system** - Trust building
4. **Optimize performance** - User experience

### Long Term (Month 3+)

1. **Add real-time features** - WebSocket for notifications
2. **Implement analytics** - Business intelligence
3. **Add search optimization** - Elasticsearch integration
4. **Mobile responsiveness** - Already good, but test thoroughly
5. **PWA features** - Offline support

### Technology Recommendations

1. **State Management:** Consider Zustand or React Query
   - Zustand: Lightweight, simple API
   - React Query: Built-in caching, refetching, optimistic updates

2. **API Client:** Use Axios (already in dependencies)
   - Better error handling than fetch
   - Interceptors for auth tokens
   - Request/response transformation

3. **Form Handling:** Use React Hook Form + Zod (already in dependencies)
   - Type-safe validation
   - Better performance than Formik
   - Great TypeScript support

4. **Type Safety:** Generate types from backend
   - Use openapi-typescript or similar
   - Ensure frontend-backend type consistency

5. **Testing:**
   - Unit: Jest + React Testing Library
   - E2E: Playwright or Cypress
   - API: Postman collections (already exist)

### Best Practices

1. **API Error Handling**

   ```typescript
   try {
     const response = await propertyService.getAll()
     setProperties(response.data)
   } catch (error) {
     if (error.response?.status === 401) {
       // Redirect to login
     } else if (error.response?.status === 403) {
       // Show forbidden message
     } else {
       // Show generic error
       toast.error('Failed to load properties')
     }
   }
   ```

2. **Loading States**

   ```typescript
   const [isLoading, setIsLoading] = useState(false)
   
   if (isLoading) return <Skeleton />
   if (error) return <ErrorMessage error={error} />
   if (!data) return <EmptyState />
   
   return <PropertyList properties={data} />
   ```

3. **Optimistic Updates**

   ```typescript
   const handleLike = async (propertyId) => {
     // Update UI immediately
     setLiked(true)
     
     try {
       await propertyService.like(propertyId)
     } catch (error) {
       // Revert on error
       setLiked(false)
       toast.error('Failed to save property')
     }
   }
   ```

4. **Request Debouncing**

   ```typescript
   const debouncedSearch = useMemo(
     () => debounce((query) => {
       propertyService.search(query)
     }, 300),
     []
   )
   ```

---

## 📊 INTEGRATION COMPLEXITY MATRIX

| Module | Frontend Work | Backend Ready | Integration Difficulty | Estimated Hours |
|--------|--------------|---------------|----------------------|-----------------|
| **Authentication** | Medium | ✅ Yes | Low | 8-12h |
| **Property Listing** | Low | ✅ Yes | Low | 4-6h |
| **Property Details** | Low | ✅ Yes | Low | 2-4h |
| **Property Search** | Medium | ✅ Yes | Medium | 6-8h |
| **Property CRUD** | High | ✅ Yes | Medium | 12-16h |
| **Tenant Dashboard** | Low | ✅ Yes | Low | 4-6h |
| **Landlord Dashboard** | Low | ✅ Yes | Low | 4-6h |
| **Applications** | Medium | ✅ Yes | Medium | 10-14h |
| **Leases** | Medium | ✅ Yes | Medium | 8-12h |
| **Payments** | High | ✅ Yes | High | 16-20h |
| **Notifications** | High | ✅ Yes | Medium | 10-14h |
| **File Upload** | High | ✅ Yes | Medium | 12-16h |
| **Profile** | Medium | ✅ Yes | Low | 6-8h |
| **Reviews** | Medium | ✅ Yes | Medium | 8-10h |
| **Admin Panel** | High | ✅ Yes | High | 20-24h |

**Total Estimated Hours:** 130-176 hours (~3-4 weeks for 1 developer at 40h/week)

---

## 🎯 SUCCESS CRITERIA

### Phase 1 Success (Authentication & Properties)

- [ ] User can register and login
- [ ] JWT tokens are stored and refreshed
- [ ] Property listing displays real data from API
- [ ] Property details page shows real data
- [ ] Landlord can create a new property
- [ ] Protected routes redirect to login

### Phase 2 Success (Dashboards & Core Features)

- [ ] Tenant dashboard shows real statistics
- [ ] Landlord dashboard shows real statistics
- [ ] Application submission works end-to-end
- [ ] Landlord can approve/reject applications
- [ ] Notifications appear for status changes

### Phase 3 Success (Complete Integration)

- [ ] All pages consume real API data
- [ ] All forms submit to backend successfully
- [ ] File upload works for property images
- [ ] Payment system displays and updates correctly
- [ ] Admin panel manages users effectively
- [ ] Error handling works across all features
- [ ] Loading states appear appropriately

### Production Readiness

- [ ] All API endpoints integrated
- [ ] Authentication flow is secure
- [ ] Role-based access control enforced
- [ ] Error boundaries implemented
- [ ] Loading states and error messages
- [ ] Mobile responsive (already good)
- [ ] Performance optimized
- [ ] CORS configured for production
- [ ] Environment variables for secrets
- [ ] Documentation complete

---

## 📚 APPENDIX

### A. Design System Color Reference

```css
/* Primary Palette */
Indigo Primary: oklch(0.541 0.214 262.881)
Background Light: oklch(0.985 0.002 247.858)
Background Dark: oklch(0.141 0.033 264.364)

/* Semantic Colors */
Success Green: oklch(0.627 0.194 149.214)
Warning Yellow: oklch(0.769 0.188 70.08)
Info Blue: oklch(0.623 0.214 259.815)
Destructive Red: oklch(0.577 0.245 27.325)

/* Neutral Colors */
Foreground Light: oklch(0.145 0.024 264.364)
Foreground Dark: oklch(0.985 0.01 264.364)
Muted: oklch(0.556 0.02 264.364)
Border: oklch(0.922 0.01 264.364)
```

### B. API Base URLs

```
Development:
- Frontend: http://localhost:3000
- Backend: http://localhost:8080/api

Production (To be configured):
- Frontend: https://gharsaathi.com
- Backend: https://api.gharsaathi.com/api
```

### C. Key File Locations

**Frontend:**

- Config: `/frontend/next.config.mjs`, `/frontend/tsconfig.json`
- Styles: `/frontend/app/globals.css`
- Components: `/frontend/components/**`
- Pages: `/frontend/app/**`
- Mock Data: `/frontend/lib/mock-data.ts`

**Backend:**

- Config: `/backend/src/main/resources/application.properties`
- Controllers: `/backend/src/main/java/com/gharsaathi/*/controller/**`
- Services: `/backend/src/main/java/com/gharsaathi/*/service/**`
- Entities: `/backend/src/main/java/com/gharsaathi/*/model/**`
- DTOs: `/backend/src/main/java/com/gharsaathi/*/dto/**`

### D. Component Library Reference

**shadcn/ui Components Available (59 total):**

- Layout: sidebar, resizable, scroll-area, separator
- Forms: input, textarea, select, checkbox, radio-group, switch, slider
- Feedback: toast, alert, alert-dialog, sonner, spinner, progress
- Navigation: tabs, menubar, navigation-menu, pagination, breadcrumb
- Data: table, card, avatar, badge, calendar
- Overlays: dialog, sheet, drawer, popover, hover-card, tooltip, context-menu
- Interactive: button, toggle, toggle-group, collapsible, accordion, carousel
- Advanced: command, chart, input-otp, form, field

### E. Testing Endpoints

**Postman Collection Available:**

- Location: `/backend/tests/`
- Files: Individual module test files (*.txt)
- Covers: All API modules with sample requests

---

## 🏁 CONCLUSION

### Summary

GharSaathi has a **solid foundation** with:

- ✅ **100% Complete Backend** - All APIs implemented, tested, documented
- ✅ **Well-Designed Frontend UI** - Modern, accessible, consistent design system
- ⚠️ **Zero Integration** - Complete disconnect between frontend and backend

### Next Steps

1. **Week 1-2:** Set up API infrastructure and authentication
2. **Week 3-4:** Connect property module and dashboards
3. **Week 5-8:** Complete application workflow, leases, and payments
4. **Week 9-12:** Add supporting features (notifications, files, reviews, admin)
5. **Week 13-14:** Testing, optimization, and production preparation

### Effort Estimation

**Total Development Time:** 130-176 hours (~1 month with 1 full-time developer)

- Critical Path (Authentication + Properties + Dashboards): 40-50 hours
- Core Features (Applications + Leases + Payments): 50-60 hours
- Supporting Features (Notifications + Files + Reviews): 30-40 hours
- Admin Panel: 20-24 hours

### Risk Assessment

**Low Risk:**

- Backend is stable and well-documented
- Frontend design is complete
- Tech stack is modern and well-supported

**Medium Risk:**

- Type consistency between frontend and backend
- Performance with real data (pagination needed)
- Security configuration for production

**Mitigation:**

- Generate TypeScript types from backend
- Implement pagination and infinite scroll early
- Configure CORS, HTTPS, and secrets management

### Final Recommendation

**Proceed with integration using the phased approach outlined above.**

The project architecture is sound, and with systematic integration following this roadmap, GharSaathi can be production-ready within 4-6 weeks. The biggest challenge is not technical but organizational - ensuring consistent API contract between frontend and backend types.

**Key Success Factor:** Start with authentication and property listing to establish the integration pattern, then replicate across other modules.

---

**Report End**  
*Generated by: AI Analysis System*  
*Date: January 28, 2026*
