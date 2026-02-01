# Project Scope

> Defining boundaries, deliverables, and constraints for GharSaathi

## 📦 Scope Overview

This document defines what is included and excluded from the GharSaathi project, establishing clear boundaries for development efforts and setting appropriate expectations for stakeholders.

## ✅ In Scope

### Core Modules

#### 1. Authentication & Authorization System
**Included**:
- User registration with role selection (Tenant, Landlord, Admin)
- Email/password-based login
- JWT token-based authentication
- Refresh token mechanism
- Token blacklisting for security
- Role-based access control (RBAC)
- Password change functionality
- User session management

**Deliverables**:
- Login/Registration pages
- JWT service implementation
- Security configuration
- Auth controller and endpoints
- Token blacklist database

#### 2. Property Management Module
**Included**:
- Property listing creation (title, description, rent, location, amenities)
- Property image upload (multiple images per property)
- Property status management (AVAILABLE, RENTED, UNDER_MAINTENANCE)
- Property search with filters (city, type, price range, amenities)
- Property details view
- Landlord property management (CRUD operations)
- Property availability tracking

**Deliverables**:
- Property entity and database schema
- Property CRUD API endpoints
- Property search and filter functionality
- Property listing pages (tenant and landlord views)
- Property detail page
- Property management interface for landlords

#### 3. Rental Application Module
**Included**:
- Application submission by tenants
- Application status tracking (PENDING, APPROVED, REJECTED, WITHDRAWN)
- Landlord application review interface
- Application approval/rejection workflow
- Application history for both tenants and landlords
- Duplicate application prevention
- Application withdrawal by tenants

**Deliverables**:
- Rental application entity
- Application submission API
- Application management endpoints
- Tenant application tracking interface
- Landlord application review interface

#### 4. Lease Management Module
**Included**:
- Lease creation after application approval
- Lease agreement with terms (start date, end date, rent amount)
- Active lease tracking
- Lease termination functionality
- Lease expiry notifications
- Lease renewal support
- Lease history tracking

**Deliverables**:
- Lease entity and relationships
- Lease CRUD operations
- Lease management API endpoints
- Lease view and management interfaces
- Automated lease expiry detection

#### 5. Payment Management Module
**Included**:
- Automatic payment generation for active leases
- Payment tracking (PENDING, CONFIRMED, OVERDUE)
- Two-step payment confirmation (landlord and tenant)
- Late fee calculation (2% per day after due date)
- Payment history and receipts
- Overdue payment detection
- Payment reminders (scheduled task)

**Deliverables**:
- Payment entity with status tracking
- Payment generation logic
- Payment confirmation workflow
- Late fee calculation service
- Payment dashboard
- Scheduled jobs for payment management

#### 6. User Profile Management
**Included**:
- User profile viewing
- Profile information updates (name, phone, email)
- Password change with current password verification
- Profile picture URL management
- Email verification status (infrastructure)
- Phone verification status (infrastructure)
- User account information

**Deliverables**:
- Profile API endpoints
- Profile update interface
- Password change functionality
- User settings page

#### 7. Admin User Management
**Included**:
- User listing with filters
- User detail view with aggregated statistics
- User suspension/unsuspension
- User role modification
- User activity tracking
- Soft delete support
- User search functionality

**Deliverables**:
- Admin user management API
- User list and detail views
- User action controls (suspend, role change)
- Admin user management interface

#### 8. File Upload Service
**Included**:
- File upload with validation (size, type)
- File storage management
- File download functionality
- File deletion (owner verification)
- Category-based file organization
- File metadata tracking
- Supported categories: PROPERTY_IMAGE, PROFILE_PICTURE, DOCUMENT

**Deliverables**:
- File upload entity
- File upload API endpoints
- File validation logic
- File storage service
- File management interface

#### 9. Dashboard & Analytics
**Included**:
- **Tenant Dashboard**: 
  - Total applications count
  - Approved applications count
  - Pending applications count
  - Saved properties count (future)
- **Landlord Dashboard**:
  - Total properties count
  - Total applications received
  - Total revenue earned
  - Active leases count
- **Admin Dashboard**:
  - Total users count
  - Total properties count
  - Total active leases
  - Total revenue on platform
  - Top landlords by revenue
  - Recent user registrations

**Deliverables**:
- Dashboard API endpoints for each role
- Dashboard UI components
- Statistics aggregation services
- Real-time data display

### Technical Implementation

#### Backend
- ✅ Spring Boot 4.0.1 framework
- ✅ Spring Security with JWT
- ✅ Spring Data JPA / Hibernate
- ✅ MySQL database
- ✅ Maven build tool
- ✅ RESTful API design
- ✅ Layered architecture (Controller, Service, Repository)
- ✅ DTO pattern for data transfer
- ✅ Global exception handling
- ✅ Validation with Bean Validation
- ✅ Scheduled tasks for automation
- ✅ Audit logging (created_at, updated_at)

#### Frontend
- ✅ Next.js 14 (React 18)
- ✅ TypeScript for type safety
- ✅ Tailwind CSS for styling
- ✅ shadcn/ui component library
- ✅ Responsive design (mobile, tablet, desktop)
- ✅ Dark mode support
- ✅ Client-side routing
- ✅ Form handling and validation
- ✅ API integration with fetch/axios
- ✅ Loading states and error handling

#### Database
- ✅ MySQL relational database
- ✅ Normalized schema design
- ✅ Entity relationships (One-to-Many, Many-to-One)
- ✅ Indexes for performance
- ✅ Constraints for data integrity
- ✅ Audit columns (timestamps)

### Documentation
- ✅ Comprehensive README
- ✅ API endpoint documentation
- ✅ Module-specific documentation
- ✅ Code comments and JavaDoc
- ✅ Setup and installation guides
- ✅ Architecture documentation
- ✅ Database schema documentation

### Testing
- ✅ API testing documentation
- ✅ Test cases for all modules
- ✅ Manual testing procedures
- 🟡 Unit tests (in progress)
- 🟡 Integration tests (planned)

## ❌ Out of Scope

### Features Not Included (Current Version)

#### 1. Real-Time Communication
- ❌ In-app messaging between users
- ❌ Live chat support
- ❌ Real-time notifications (push notifications)
- ❌ Video calling for property tours

**Rationale**: Requires WebSocket infrastructure and additional complexity. Can be added in future versions.

#### 2. Payment Gateway Integration
- ❌ Online payment processing (eSewa, Khalti, etc.)
- ❌ Credit/debit card payments
- ❌ Automated bank transfers
- ❌ Payment gateway webhooks

**Rationale**: Requires third-party integration, merchant accounts, and additional security measures. Current version focuses on payment tracking only.

#### 3. Advanced Property Features
- ❌ 360° virtual tours
- ❌ Video property tours
- ❌ Interactive floor plans
- ❌ AR/VR property viewing
- ❌ Property comparison tool
- ❌ Saved searches with alerts
- ❌ Property valuation tools

**Rationale**: Requires significant additional resources and specialized technologies. Basic property viewing is sufficient for MVP.

#### 4. Social Features
- ❌ User reviews and ratings
- ❌ Social media integration
- ❌ Property sharing on social platforms
- ❌ Referral program
- ❌ User reputation system

**Rationale**: Requires critical mass of users and additional moderation infrastructure.

#### 5. Mobile Applications
- ❌ Native iOS application
- ❌ Native Android application
- ❌ React Native mobile app
- ❌ Progressive Web App (PWA)

**Rationale**: Responsive web application serves mobile users adequately for current scope.

#### 6. Advanced Analytics
- ❌ Predictive analytics
- ❌ Market trend analysis
- ❌ Price recommendations
- ❌ Demand forecasting
- ❌ ML-based property recommendations

**Rationale**: Requires significant historical data and ML infrastructure.

#### 7. Integration Features
- ❌ Google Maps integration for property locations
- ❌ Email service integration (SendGrid, etc.)
- ❌ SMS service integration
- ❌ Cloud storage integration (AWS S3, etc.)
- ❌ Calendar integrations

**Rationale**: Third-party services require API keys, costs, and additional setup complexity.

#### 8. Multi-Language Support
- ❌ Nepali language support
- ❌ Other regional languages
- ❌ Language switching
- ❌ Localization

**Rationale**: English-only for current version to reduce complexity.

#### 9. Advanced Admin Features
- ❌ Content management system
- ❌ Email campaign management
- ❌ Advanced reporting tools
- ❌ Bulk operations
- ❌ Data export functionality

**Rationale**: Current admin features sufficient for initial version.

#### 10. Legal Features
- ❌ Digital signature for lease agreements
- ❌ Legal document generation
- ❌ Contract management
- ❌ Dispute resolution system

**Rationale**: Requires legal compliance and integration with document services.

## 🎯 Scope Boundaries

### Geographic Scope
**Included**: Nepal (all cities and regions)  
**Excluded**: International properties or multi-country support

### User Types
**Included**: Individual tenants, individual landlords, platform administrators  
**Excluded**: Real estate agencies, corporate users, property management companies

### Property Types
**Included**: Residential properties (apartments, houses, rooms)  
**Excluded**: Commercial properties, land, vacation rentals

### Transaction Types
**Included**: Long-term rentals (minimum 1 month)  
**Excluded**: Short-term rentals, vacation rentals, daily rentals

## ⚖️ Constraints

### Technical Constraints
1. **Development Time**: Limited to 7th semester duration (approx. 4-5 months)
2. **Team Size**: Small development team (1-3 developers)
3. **Budget**: Zero budget (using free tools and services)
4. **Infrastructure**: Local development environment initially
5. **Technology Stack**: Committed to Spring Boot and Next.js

### Resource Constraints
1. **Hardware**: Development on personal computers
2. **Testing**: Limited to manual and automated testing without dedicated QA team
3. **Design Resources**: Using free UI component libraries
4. **Third-Party Services**: Limited to free tiers

### Knowledge Constraints
1. **Learning Curve**: Time required to learn new technologies
2. **Best Practices**: Limited by team's experience level
3. **Architecture**: Constrained by academic project requirements

### Regulatory Constraints
1. **Data Protection**: Basic security practices, no formal compliance certifications
2. **Legal Documents**: No legal review of lease templates
3. **Financial Transactions**: No real money processing (tracking only)

## 📊 Deliverables Summary

### Code Deliverables
- ✅ Backend API (Spring Boot application)
- ✅ Frontend Application (Next.js application)
- ✅ Database Schema (MySQL scripts)
- ✅ Configuration Files
- ✅ Build and Deployment Scripts

### Documentation Deliverables
- ✅ Project Documentation (this docs folder)
- ✅ API Documentation
- ✅ User Guide
- ✅ Developer Guide
- ✅ Setup Instructions
- ✅ Architecture Documentation

### Testing Deliverables
- ✅ Test Cases Documentation
- ✅ API Test Collection
- 🟡 Test Reports (in progress)

### Deployment Deliverables
- 🟡 Deployed Application (planned)
- 🟡 Deployment Guide (planned)
- 🟡 User Manual (planned)

## 🔄 Scope Management

### Change Control
Any changes to scope must:
1. Be documented in this file
2. Have clear justification
3. Consider impact on timeline and resources
4. Be approved by team/advisor

### Scope Creep Prevention
- Regular scope reviews
- Prioritization of features
- "Must-have" vs "Nice-to-have" distinction
- Deferral of non-critical features to future versions

### Future Enhancements
Features marked as "Out of Scope" are candidates for future versions and can be prioritized based on:
- User feedback
- Technical feasibility
- Resource availability
- Market demand

## 🔗 Related Documents

- [Project Overview](./project-overview.md) - Full project context
- [Objectives](./objectives.md) - What we aim to achieve within scope
- [Requirements](../01-requirements/) - Detailed requirements for in-scope features

---

**Last Updated**: January 28, 2026  
**Version**: 1.0.0  
**Status**: Scope Locked (with controlled change process)
