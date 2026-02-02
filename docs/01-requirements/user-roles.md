# User Roles - GharSaathi

> Comprehensive guide to user roles, permissions, and responsibilities

## 👥 Role Overview

GharSaathi implements a Role-Based Access Control (RBAC) system with three distinct user roles, each with specific permissions and capabilities. This ensures proper separation of concerns and security.

## 🎭 User Roles

### 1. TENANT 🏘️

#### Description
A user who is seeking to rent a property. Tenants are the primary consumers of the platform, searching for and applying to properties.

#### Primary Responsibilities
- Browse and search available properties
- Save favorite properties (planned feature)
- Submit rental applications
- Track application status
- Manage active lease agreements
- Confirm rent payments
- Maintain user profile

#### Permissions

**Property Module**:
- ✅ View all AVAILABLE properties
- ✅ View detailed property information
- ✅ Search and filter properties
- ❌ Create or edit properties
- ❌ Delete properties
- ❌ View RENTED properties (except their own)

**Rental Application Module**:
- ✅ Submit new applications
- ✅ View own applications
- ✅ Withdraw pending applications
- ❌ Approve/reject applications
- ❌ View other users' applications

**Lease Module**:
- ✅ View own active leases
- ✅ View lease details and terms
- ❌ Create leases (created by landlords)
- ❌ Terminate leases (must request landlord)
- ❌ View other users' leases

**Payment Module**:
- ✅ View own payment history
- ✅ Confirm payments made
- ✅ View payment receipts
- ❌ Generate payments
- ❌ View others' payments

**Profile Module**:
- ✅ View own profile
- ✅ Update own profile information
- ✅ Change password
- ❌ View other users' profiles
- ❌ Modify other users' data

**Dashboard**:
- ✅ Access tenant dashboard
- ✅ View application statistics
- ✅ View saved properties count
- ❌ Access landlord/admin dashboards

#### API Endpoints Access

```
GET    /api/v1/properties                    ✅ Allowed
GET    /api/v1/properties/{id}               ✅ Allowed
POST   /api/v1/rental-applications           ✅ Allowed
GET    /api/v1/rental-applications/my-applications  ✅ Allowed
DELETE /api/v1/rental-applications/{id}/withdraw    ✅ Allowed
GET    /api/v1/leases/my-leases              ✅ Allowed
GET    /api/v1/payments/my-payments          ✅ Allowed
POST   /api/v1/payments/{id}/tenant-confirm  ✅ Allowed
GET    /api/v1/dashboard/tenant              ✅ Allowed
GET    /api/v1/profile                       ✅ Allowed
PUT    /api/v1/profile                       ✅ Allowed
```

#### User Journey
1. Register as TENANT
2. Browse available properties
3. Submit application for desired property
4. Wait for landlord approval
5. Receive lease agreement (if approved)
6. Confirm rent payments
7. Manage ongoing lease

---

### 2. LANDLORD 🏢

#### Description
A property owner who lists properties for rent and manages rental operations. Landlords are the supply side of the marketplace.

#### Primary Responsibilities
- List and manage properties
- Upload property images
- Review rental applications
- Approve or reject applications
- Create lease agreements
- Generate and track payments
- Confirm received payments
- Monitor property performance

#### Permissions

**Property Module**:
- ✅ Create new property listings
- ✅ Edit own properties
- ✅ Delete own properties
- ✅ View all own properties (any status)
- ✅ View all available properties (search)
- ✅ Update property status
- ❌ Edit/delete others' properties

**Rental Application Module**:
- ✅ View applications for own properties
- ✅ Approve applications
- ✅ Reject applications
- ✅ View applicant details
- ❌ Submit applications (landlords don't apply)
- ❌ View applications for others' properties

**Lease Module**:
- ✅ Create leases after application approval
- ✅ View leases for own properties
- ✅ Terminate leases
- ✅ Extend/renew leases
- ❌ View leases for others' properties
- ❌ Modify lease terms after creation

**Payment Module**:
- ✅ Generate payment records
- ✅ View payment history for own properties
- ✅ Confirm payments received
- ✅ View overdue payments
- ❌ View payments for others' properties

**Profile Module**:
- ✅ View own profile
- ✅ Update own profile information
- ✅ Change password
- ❌ View other users' profiles (except applicants)
- ❌ Modify other users' data

**Dashboard**:
- ✅ Access landlord dashboard
- ✅ View property statistics
- ✅ View revenue analytics
- ✅ View application metrics
- ❌ Access tenant/admin specific features

#### API Endpoints Access

```
POST   /api/v1/properties                    ✅ Allowed
PUT    /api/v1/properties/{id}               ✅ Allowed (own only)
DELETE /api/v1/properties/{id}               ✅ Allowed (own only)
GET    /api/v1/properties/my-properties      ✅ Allowed
GET    /api/v1/rental-applications/for-my-properties  ✅ Allowed
POST   /api/v1/rental-applications/{id}/approve      ✅ Allowed
POST   /api/v1/rental-applications/{id}/reject       ✅ Allowed
POST   /api/v1/leases                        ✅ Allowed
GET    /api/v1/leases/my-properties          ✅ Allowed
POST   /api/v1/leases/{id}/terminate         ✅ Allowed
POST   /api/v1/payments/generate             ✅ Allowed
POST   /api/v1/payments/{id}/landlord-confirm ✅ Allowed
GET    /api/v1/dashboard/landlord            ✅ Allowed
```

#### User Journey
1. Register as LANDLORD
2. Create property listings
3. Upload property images
4. Receive applications from tenants
5. Review and approve/reject applications
6. Create lease agreement
7. Generate monthly payments
8. Confirm received payments
9. Monitor dashboard analytics

---

### 3. ADMIN 🛡️

#### Description
Platform administrators with elevated privileges responsible for overall platform management, user moderation, and system oversight.

#### Primary Responsibilities
- Manage all users (suspend, unsuspend, role changes)
- Moderate property listings
- View platform-wide analytics
- Resolve user disputes
- Monitor system health
- Ensure platform quality
- Review flagged content

#### Permissions

**User Management**:
- ✅ View all users
- ✅ View user details with statistics
- ✅ Suspend/unsuspend users
- ✅ Change user roles
- ✅ Soft delete users
- ✅ Search and filter users
- ⚠️ Cannot delete users permanently

**Property Module**:
- ✅ View all properties (any status)
- ✅ View property details
- ✅ Moderate/flag inappropriate listings
- ❌ Create properties (not property owner)
- ⚠️ Can edit for moderation purposes

**Application & Lease Module**:
- ✅ View all applications
- ✅ View all leases
- ✅ View application/lease statistics
- ❌ Approve/reject applications (landlord's role)
- ❌ Create leases (landlord's role)

**Payment Module**:
- ✅ View all payment records
- ✅ View payment statistics
- ✅ View platform revenue
- ❌ Generate payments (landlord's role)
- ❌ Confirm payments (parties' role)

**Profile Module**:
- ✅ View any user's profile
- ✅ Edit user profiles for moderation
- ✅ Reset user passwords (if needed)
- ⚠️ Should respect privacy concerns

**Dashboard**:
- ✅ Access admin dashboard
- ✅ View platform-wide statistics
- ✅ View user growth metrics
- ✅ View top landlords by revenue
- ✅ View recent registrations
- ✅ Monitor all platform activities

#### API Endpoints Access

```
GET    /api/v1/admin/users                   ✅ Allowed
GET    /api/v1/admin/users/{id}              ✅ Allowed
POST   /api/v1/admin/users/{id}/suspend      ✅ Allowed
POST   /api/v1/admin/users/{id}/unsuspend    ✅ Allowed
PUT    /api/v1/admin/users/{id}/role         ✅ Allowed
GET    /api/v1/admin/properties              ✅ Allowed
GET    /api/v1/admin/applications            ✅ Allowed
GET    /api/v1/admin/leases                  ✅ Allowed
GET    /api/v1/admin/payments                ✅ Allowed
GET    /api/v1/dashboard/admin               ✅ Allowed
```

#### User Journey
1. Assigned ADMIN role by system/superadmin
2. Access admin dashboard
3. Monitor platform statistics
4. Review flagged content/users
5. Take moderation actions
6. Respond to user issues
7. Generate platform reports

---

## 🔐 Access Control Matrix

### Feature Access Summary

| Feature | Tenant | Landlord | Admin |
|---------|--------|----------|-------|
| **View Properties** | ✅ Available only | ✅ All own + Available | ✅ All |
| **Create Property** | ❌ | ✅ | ❌ |
| **Edit Property** | ❌ | ✅ Own only | ⚠️ Moderation |
| **Delete Property** | ❌ | ✅ Own only | ⚠️ Moderation |
| **Submit Application** | ✅ | ❌ | ❌ |
| **Review Applications** | ❌ | ✅ For own properties | ✅ View all |
| **Approve/Reject Application** | ❌ | ✅ | ❌ |
| **Create Lease** | ❌ | ✅ | ❌ |
| **View Lease** | ✅ Own only | ✅ Own properties | ✅ All |
| **Terminate Lease** | ❌ | ✅ | ⚠️ Moderation |
| **Generate Payment** | ❌ | ✅ | ❌ |
| **Confirm Payment** | ✅ As tenant | ✅ As landlord | ❌ |
| **View Payments** | ✅ Own only | ✅ Own properties | ✅ All |
| **User Management** | ❌ | ❌ | ✅ |
| **Platform Analytics** | ❌ Personal | ❌ Own data | ✅ All data |

### HTTP Method Permissions

| HTTP Method | Tenant | Landlord | Admin |
|-------------|--------|----------|-------|
| **GET** (view) | ✅ Own data + public | ✅ Own data + public | ✅ All data |
| **POST** (create) | ✅ Applications | ✅ Properties, Leases | ❌ Most entities |
| **PUT** (update) | ✅ Own profile | ✅ Own resources | ✅ Moderation |
| **DELETE** (remove) | ✅ Withdraw apps | ✅ Own properties | ⚠️ Soft delete |

---

## 🔄 Role Assignment

### How Roles Are Assigned

#### During Registration
- User selects role during registration
- Available roles: TENANT, LANDLORD
- Role is immediately assigned
- Cannot be ADMIN during registration

#### Admin Role Assignment
- Only existing admins can create new admins
- Done through user role change functionality
- Requires verification and approval
- Logged for audit purposes

### Role Change Process

#### User-Initiated
- Users cannot change their own roles
- Must contact admin for role changes
- Justification may be required

#### Admin-Initiated
```
PUT /api/v1/admin/users/{userId}/role
{
  "newRole": "LANDLORD"
}
```

#### Rules
- ✅ TENANT → LANDLORD (if user wants to list properties)
- ✅ LANDLORD → TENANT (if user wants to stop listing)
- ⚠️ Any → ADMIN (requires admin approval)
- ❌ ADMIN → Other (must be done by another admin)

### Role Validation
- Role checked on every API request
- JWT token contains user role
- Spring Security enforces role-based access
- Invalid role attempts are logged

---

## 🎯 Role-Specific Features

### Tenant-Specific
- Application tracking dashboard
- Saved properties list
- Payment due reminders
- Lease expiry notifications

### Landlord-Specific
- Property performance analytics
- Application management interface
- Revenue tracking
- Occupancy rate calculations
- Tenant payment history

### Admin-Specific
- User growth charts
- Platform revenue overview
- Top performing landlords
- User activity monitoring
- Flagged content review queue

---

## 🔍 Role Identification

### In Database
```sql
CREATE TABLE users (
  id BIGINT PRIMARY KEY,
  email VARCHAR(255) UNIQUE,
  role ENUM('TENANT', 'LANDLORD', 'ADMIN'),
  ...
);
```

### In JWT Token
```json
{
  "sub": "user@email.com",
  "userId": 123,
  "role": "LANDLORD",
  "authorities": ["ROLE_LANDLORD"],
  ...
}
```

### In Code
```java
@PreAuthorize("hasRole('LANDLORD')")
public ResponseEntity<?> createProperty() {
    // Only landlords can execute
}

@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<?> suspendUser() {
    // Only admins can execute
}
```

---

## 📋 Best Practices

### For Developers
1. **Always check role before granting access**
2. **Use `@PreAuthorize` annotations on sensitive endpoints**
3. **Validate role from JWT, never trust client input**
4. **Log role-based actions for audit trail**
5. **Test each role's permissions thoroughly**

### For Users
1. **Choose the appropriate role during registration**
2. **Understand your role's capabilities**
3. **Don't attempt unauthorized actions**
4. **Contact admin for legitimate role changes**

### Security Considerations
- ✅ Role stored in database and JWT
- ✅ Role validated on every request
- ✅ Role changes logged
- ✅ Unauthorized access attempts logged
- ✅ Role cannot be changed without authentication

---

## 🔗 Related Documents

- [Functional Requirements](./functional-requirements.md) - Requirements by role
- [Security Overview](../05-security/spring-security-overview.md) - Security implementation
- [API Documentation](../03-api/) - API endpoints by role

---

**Last Updated**: January 28, 2026  
**Version**: 1.0.0  
**Roles Implemented**: 3 (TENANT, LANDLORD, ADMIN)
