# Glossary

> Definitions of terms, acronyms, and concepts used in GharSaathi

## 📖 General Terms

### GharSaathi

The name of the platform. "Ghar" means "home" and "Saathi" means "companion" in Nepali. Together, "GharSaathi" translates to "Home Companion" - a platform that accompanies users in their home rental journey.

### Property

A real estate unit available for rent. In GharSaathi, properties are residential units such as apartments, houses, or rooms listed by landlords.

### Tenant

A user who is seeking to rent a property. Tenants can browse properties, submit applications, and manage their rental agreements.

### Landlord

A property owner who lists properties for rent on the platform. Landlords can manage their properties, review applications, and track lease agreements.

### Administrator (Admin)

A platform manager with elevated privileges who oversees user management, content moderation, and platform operations.

### Rental Application

A formal request submitted by a tenant to rent a specific property. Applications go through an approval workflow managed by the landlord.

### Lease

A formal agreement between a landlord and tenant that grants the tenant the right to occupy a property for a specified period in exchange for rent payments.

### Payment

A monetary transaction recorded in the system representing rent payment from tenant to landlord. In the current version, payments are tracked but not processed through the platform.

## 🏗️ Technical Terms

### API (Application Programming Interface)

A set of endpoints that allow the frontend to communicate with the backend, or external systems to integrate with GharSaathi.

### Backend

The server-side application built with Spring Boot that handles business logic, data persistence, and API endpoints.

### Frontend

The client-side application built with Next.js that provides the user interface and user experience.

### REST (Representational State Transfer)

An architectural style for designing networked applications. GharSaathi uses RESTful APIs for client-server communication.

### JSON (JavaScript Object Notation)

A lightweight data interchange format used for API requests and responses in GharSaathi.

### JWT (JSON Web Token)

A compact, URL-safe token format used for authentication. Contains encoded user information and is used to verify user identity.

### Authentication

The process of verifying a user's identity (typically through username and password).

### Authorization

The process of determining what actions an authenticated user is permitted to perform based on their role.

### RBAC (Role-Based Access Control)

A security approach that restricts system access based on user roles (Tenant, Landlord, Admin in GharSaathi).

### Token

A string representing a user's authenticated session. GharSaathi uses access tokens and refresh tokens.

### Access Token

A short-lived JWT token (1 hour) used to authenticate API requests.

### Refresh Token

A long-lived JWT token (7 days) used to obtain new access tokens without requiring the user to log in again.

### Token Blacklist

A database of revoked tokens that prevents their reuse even if they haven't expired, enhancing security.

## 🗂️ Architecture Terms

### Layered Architecture

A design pattern that organizes code into distinct layers (Controller, Service, Repository) with specific responsibilities.

### Controller Layer

The API layer that handles HTTP requests and responses. Controllers validate input and call service methods.

### Service Layer

The business logic layer that implements core functionality and orchestrates operations.

### Repository Layer

The data access layer that interfaces with the database using Spring Data JPA.

### Entity

A Java class that represents a database table. Entities are annotated with JPA annotations.

### DTO (Data Transfer Object)

A pattern for transferring data between layers. DTOs define the structure of API requests and responses.

### Model

Another term for Entity. Represents domain objects and their relationships.

### Dependency Injection

A design pattern where objects receive their dependencies from an external source rather than creating them internally. Spring Framework uses DI extensively.

### Bean

A Spring-managed object. Beans are instantiated, configured, and managed by the Spring IoC container.

### Annotation

Metadata added to Java code (e.g., `@Entity`, `@Service`, `@Controller`) that provides configuration information to frameworks.

## 🔐 Security Terms

### BCrypt

A password hashing algorithm used to securely store user passwords. GharSaathi uses BCrypt with strength 12.

### CORS (Cross-Origin Resource Sharing)

A security mechanism that allows or restricts resources on a web server to be requested from another domain. Configured to allow frontend access.

### CSRF (Cross-Site Request Forgery)

A type of attack that forces authenticated users to execute unwanted actions. GharSaathi uses JWT instead of session cookies, mitigating CSRF risks.

### XSS (Cross-Site Scripting)

An attack where malicious scripts are injected into trusted websites. GharSaathi includes XSS protection headers.

### Salt

Random data added to passwords before hashing to ensure unique hashes even for identical passwords. BCrypt handles salting automatically.

### JWT Claims

The information stored in a JWT token, such as user ID, role, email, and token type.

### JTI (JWT ID)

A unique identifier for each JWT token, used for tracking and token blacklisting.

## 💾 Database Terms

### Schema

The structure of the database, including tables, columns, relationships, and constraints.

### Entity Relationship

Connections between database tables (One-to-Many, Many-to-One, Many-to-Many).

### Foreign Key

A column that references the primary key of another table, establishing a relationship.

### Primary Key

A unique identifier for each row in a database table (usually `id`).

### Index

A database structure that improves query performance on specific columns.

### Migration

The process of evolving database schema over time while preserving data.

### Cascade

An operation that automatically applies actions (delete, update) to related entities.

### Lazy Loading

A strategy where related entities are loaded from the database only when accessed.

### Eager Loading

A strategy where related entities are loaded immediately with the parent entity.

### JPA (Java Persistence API)

A specification for object-relational mapping in Java. Hibernate is the JPA implementation used in GharSaathi.

### Hibernate

An ORM (Object-Relational Mapping) framework that maps Java objects to database tables.

## 📦 Module-Specific Terms

### Property Status

The current state of a property:

- **AVAILABLE**: Property is available for rent
- **RENTED**: Property is currently rented
- **UNDER_MAINTENANCE**: Property is not available temporarily

### Application Status

The state of a rental application:

- **PENDING**: Application submitted, awaiting landlord review
- **APPROVED**: Landlord approved the application
- **REJECTED**: Landlord rejected the application
- **WITHDRAWN**: Tenant withdrew the application

### Payment Status

The state of a rent payment:

- **PENDING**: Payment generated but not confirmed
- **CONFIRMED**: Both parties confirmed the payment
- **OVERDUE**: Payment past due date
- **LATE_FEE_APPLIED**: Late fee added to overdue payment

### Amenities

Features or facilities available with a property (e.g., parking, WiFi, gym, swimming pool).

### Property Type

Category of property:

- **APARTMENT**: Multi-unit residential building unit
- **HOUSE**: Standalone residential building
- **ROOM**: Single room for rent in a shared property

### Monthly Rent

The amount of money (in NPR - Nepali Rupees) charged per month to rent a property.

### Security Deposit

An upfront payment made by tenant to cover potential damages (tracked but not processed in current version).

### Late Fee

A penalty charge applied to overdue payments. GharSaathi calculates late fees at 2% per day after the due date.

### Due Date

The date by which a rent payment must be made to avoid late fees.

### Lease Term

The duration of a lease agreement, specified by start date and end date.

## 🔧 Development Terms

### Maven

A build automation tool used for Java projects. Manages dependencies and builds the GharSaathi backend.

### pnpm

A package manager for JavaScript used in the frontend project. Alternative to npm.

### Spring Boot

A framework that simplifies Java application development. Provides auto-configuration and embedded server.

### Next.js

A React framework that provides server-side rendering, routing, and other features for building web applications.

### TypeScript

A typed superset of JavaScript that adds static typing.

### Tailwind CSS

A utility-first CSS framework used for styling the frontend.

### shadcn/ui

A collection of reusable React components built with Tailwind CSS.

### Hot Reload

Development feature where code changes are reflected immediately without restarting the server.

### Build

The process of compiling source code into executable artifacts.

### Deploy

The process of making the application available to users on a server.

## 📊 Business Terms

### Dashboard

A user interface that displays key metrics and information relevant to the user's role.

### Analytics

Data analysis and visualization of platform metrics (users, properties, revenue, etc.).

### Revenue

Money earned by landlords from rent payments. Platform revenue tracking is included in admin analytics.

### Active Lease

A lease agreement that is currently in effect (between start date and end date).

### Vacancy

A period when a property is not rented out and not generating income.

### Application Approval Rate

Percentage of applications that are approved by landlords.

### User Retention

Measure of how many users continue to use the platform over time.

## 🔤 Acronyms

| Acronym | Full Form | Description |
|---------|-----------|-------------|
| API | Application Programming Interface | Interface for software communication |
| CRUD | Create, Read, Update, Delete | Basic data operations |
| DTO | Data Transfer Object | Object for data transfer between layers |
| HTTP | Hypertext Transfer Protocol | Web communication protocol |
| HTTPS | HTTP Secure | Encrypted HTTP |
| JPA | Java Persistence API | Java database API specification |
| JWT | JSON Web Token | Token format for authentication |
| NPR | Nepali Rupee | Currency of Nepal |
| ORM | Object-Relational Mapping | Database-to-object mapping |
| REST | Representational State Transfer | API architectural style |
| RBAC | Role-Based Access Control | Authorization approach |
| SQL | Structured Query Language | Database query language |
| UI | User Interface | Visual elements users interact with |
| UX | User Experience | Overall experience of using the platform |
| CORS | Cross-Origin Resource Sharing | Browser security mechanism |
| CSRF | Cross-Site Request Forgery | Type of web attack |
| XSS | Cross-Site Scripting | Type of web vulnerability |

## 📱 Status Indicators in Documentation

| Symbol | Meaning |
|--------|---------|
| ✅ | Complete / Implemented / Achieved |
| 🟡 | In Progress / Partial |
| ❌ | Not Implemented / Out of Scope |
| 🔴 | Critical Issue |
| 🟢 | Low Risk / All Good |
| 🟡 | Medium Risk / Caution |

## 🔗 Related Documents

- [Project Overview](./project-overview.md) - Introduction to GharSaathi
- [Requirements](../01-requirements/) - Detailed system requirements
- [Architecture](../02-architecture/) - Technical architecture details

---

**Last Updated**: January 28, 2026  
**Version**: 1.0.0  
**Note**: This glossary will be updated as new terms are introduced.
