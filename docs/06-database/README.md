# Database Documentation

> Database design and implementation for GharSaathi

## 📋 Contents

### [Database Design](./database-design.md)

Complete database schema with entity-relationship diagrams and table specifications.

### [JPA Entities](./jpa-entities.md)

Detailed documentation of all JPA entities with annotations and relationships.

### [Repositories](./repositories.md)

Spring Data JPA repositories with custom queries and query methods.

### [Schema Migration](./schema-migration.md)

Database versioning strategy and migration procedures.

### [Indexing Strategy](./indexing-strategy.md)

Index design for optimal query performance.

## 💾 Database Overview

### Database Management System

**MySQL 8.0+** - Relational Database

### Key Features

- ACID compliance
- Foreign key constraints
- Indexes for performance
- Audit columns (created_at, updated_at)
- Soft delete support

## 📊 Database Schema Summary

### Total Tables: 18+

#### Core Tables

1. **users** - User accounts and authentication
2. **properties** - Property listings
3. **property_images** - Property photos
4. **rental_applications** - Tenant applications
5. **leases** - Lease agreements
6. **payments** - Rent payments
7. **file_uploads** - Uploaded files

#### Supporting Tables

8. **jwt_blacklist** - Revoked tokens
2. **scheduled_tasks** - Task execution logs
3. **amenities** - Property amenities (planned)
4. **property_reviews** - Property reviews (planned)

### Relationships

- **One-to-Many**: User → Properties, Property → Applications
- **Many-to-One**: Application → Property, Lease → Property
- **One-to-One**: Application → Lease (approved)

## 🗂️ Table Categories

### Authentication & Users

- `users` - User accounts
- `jwt_blacklist` - Revoked tokens

### Property Management

- `properties` - Property listings
- `property_images` - Property photos
- `amenities` - Available amenities

### Rental Process

- `rental_applications` - Applications
- `leases` - Active leases
- `payments` - Payment records

### Support

- `file_uploads` - File metadata
- `notifications` - User notifications (planned)
- `audit_logs` - System audit trail (planned)

## 🔑 Primary Entities

### Users Table

```sql
CREATE TABLE users (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  email VARCHAR(255) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  first_name VARCHAR(100) NOT NULL,
  last_name VARCHAR(100) NOT NULL,
  role ENUM('TENANT', 'LANDLORD', 'ADMIN') NOT NULL,
  phone VARCHAR(20),
  is_active BOOLEAN DEFAULT TRUE,
  is_suspended BOOLEAN DEFAULT FALSE,
  email_verified BOOLEAN DEFAULT FALSE,
  phone_verified BOOLEAN DEFAULT FALSE,
  profile_picture_url VARCHAR(500),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

### Properties Table

```sql
CREATE TABLE properties (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(255) NOT NULL,
  description TEXT,
  address VARCHAR(500) NOT NULL,
  city VARCHAR(100) NOT NULL,
  monthly_rent DECIMAL(10,2) NOT NULL,
  security_deposit DECIMAL(10,2),
  property_type ENUM('APARTMENT', 'HOUSE', 'ROOM') NOT NULL,
  number_of_bedrooms INT,
  number_of_bathrooms INT,
  area_sqft INT,
  status ENUM('AVAILABLE', 'RENTED', 'UNDER_MAINTENANCE') DEFAULT 'AVAILABLE',
  landlord_id BIGINT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (landlord_id) REFERENCES users(id)
);
```

## 📈 Performance Optimization

### Indexes

- Primary keys on all tables
- Foreign key indexes
- Unique indexes on email, application uniqueness
- Composite indexes for common queries

### Query Optimization

- Lazy loading for relationships
- Pagination for large result sets
- Query method optimization
- Connection pooling (HikariCP)

## 🔧 Naming Conventions

### Tables

- **Format**: `snake_case`, plural
- **Example**: `rental_applications`, `property_images`

### Columns

- **Format**: `snake_case`
- **Primary Key**: `id`
- **Foreign Keys**: `<entity>_id` (e.g., `user_id`, `property_id`)
- **Timestamps**: `created_at`, `updated_at`
- **Booleans**: `is_*`, `has_*`

### Constraints

- **Primary Key**: `pk_<table>`
- **Foreign Key**: `fk_<table>_<referenced_table>`
- **Unique**: `uk_<table>_<column>`
- **Index**: `idx_<table>_<column(s)>`

## 🔗 Quick Links

- [Database Design Details](./database-design.md)
- [Entity Specifications](./jpa-entities.md)
- [Architecture](../02-architecture/)

---

**Database**: MySQL 8.0+  
**Tables**: 18+  
**ORM**: Hibernate (JPA)  
**Last Updated**: January 28, 2026
