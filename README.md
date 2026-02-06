# 🏠 GharSaathi

> **Your Trusted Property Rental Platform in Nepal**

GharSaathi is a comprehensive property rental management platform designed to connect property owners (landlords) with potential renters (tenants) in Nepal. Built with modern web technologies, it provides a seamless experience for listing, discovering, and managing rental properties.

[![Next.js](https://img.shields.io/badge/Next.js-14.0-black?style=flat-square&logo=next.js)](https://nextjs.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?style=flat-square&logo=spring)](https://spring.io/projects/spring-boot)
[![TypeScript](https://img.shields.io/badge/TypeScript-5.0-blue?style=flat-square&logo=typescript)](https://www.typescriptlang.org/)
[![Java](https://img.shields.io/badge/Java-17-orange?style=flat-square&logo=java)](https://www.oracle.com/java/)

---

## 📋 Table of Contents

- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Architecture](#-architecture)
- [Getting Started](#-getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Running the Application](#running-the-application)
- [Project Structure](#-project-structure)
- [API Documentation](#-api-documentation)
- [User Roles](#-user-roles)
- [Screenshots](#-screenshots)
- [Contributing](#-contributing)
- [License](#-license)
- [Contact](#-contact)

---

## ✨ Features

### For Tenants 🏘️

- **Browse Properties**: Explore available rental properties with advanced filters
- **Save Favorites**: Bookmark properties for later viewing
- **Submit Applications**: Apply for rental properties directly through the platform
- **Dashboard**: Track application status and manage saved properties
- **Property Details**: View comprehensive property information with image galleries

### For Landlords 🏢

- **List Properties**: Create and manage property listings with detailed information
- **Application Management**: Review and respond to rental applications
- **Dashboard Analytics**: Monitor property performance and rental requests
- **Property Updates**: Edit and update property information in real-time
- **Request Tracking**: Keep track of all incoming rental requests

### For Administrators 🛡️

- **User Management**: Oversee and manage all platform users
- **Property Moderation**: Review and approve property listings
- **System Analytics**: Access comprehensive platform statistics
- **Content Management**: Monitor and manage all platform content

### Core Features 🔐

- **Secure Authentication**: JWT-based authentication system with refresh tokens
- **Role-Based Access Control**: Differentiated access for Tenants, Landlords, and Admins
- **Responsive Design**: Fully responsive UI that works on all devices
- **Dark Mode Support**: Built-in light/dark theme switching
- **Real-time Updates**: Dynamic content updates without page refresh
- **Search & Filters**: Advanced property search with multiple filter options

---

## 🛠️ Tech Stack

### Frontend

- **Framework**: [Next.js 14](https://nextjs.org/) (React 18)
- **Language**: [TypeScript](https://www.typescriptlang.org/)
- **Styling**: [Tailwind CSS](https://tailwindcss.com/)
- **UI Components**: [shadcn/ui](https://ui.shadcn.com/)
- **State Management**: React Hooks & Context API
- **Package Manager**: [pnpm](https://pnpm.io/)
- **Form Handling**: React Hook Form
- **HTTP Client**: Fetch API / Axios

### Backend

- **Framework**: [Spring Boot 3.x](https://spring.io/projects/spring-boot)
- **Language**: Java 17
- **Security**: Spring Security + JWT
- **ORM**: Spring Data JPA / Hibernate
- **Database**: PostgreSQL / MySQL (configurable)
- **Build Tool**: Maven
- **API Documentation**: Swagger / OpenAPI (planned)
- **Authentication**: JWT with Refresh Token mechanism

### DevOps & Tools

- **Version Control**: Git
- **Code Editor**: VS Code
- **API Testing**: Postman (optional)
- **Database Tools**: DBeaver / pgAdmin

---

## 🏗️ Architecture

```
GharSaathi/
│
├── frontend/              # Next.js Frontend Application
│   ├── app/              # Next.js App Router
│   │   ├── auth/         # Authentication pages
│   │   ├── tenant/       # Tenant dashboard
│   │   ├── landlord/     # Landlord dashboard
│   │   ├── admin/        # Admin dashboard
│   │   └── properties/   # Property pages
│   ├── components/       # Reusable React components
│   │   └── ui/          # shadcn/ui components
│   ├── lib/             # Utility functions
│   └── public/          # Static assets
│
└── backend/             # Spring Boot Backend Application
    └── src/
        └── main/java/com/gharsaathi/
            ├── auth/            # Authentication module
            │   ├── controller/  # REST controllers
            │   ├── service/     # Business logic
            │   ├── model/       # Entity models
            │   └── repository/  # Data access layer
            ├── common/          # Shared utilities
            │   ├── dto/         # Data Transfer Objects
            │   ├── security/    # Security configuration
            │   └── exception/   # Exception handlers
            └── GharSaathiApplication.java
```

---

## 🚀 Getting Started

### Prerequisites

Before you begin, ensure you have the following installed:

- **Node.js** (v18 or higher) - [Download](https://nodejs.org/)
- **pnpm** (v8 or higher) - `npm install -g pnpm`
- **Java JDK** (17 or higher) - [Download](https://www.oracle.com/java/technologies/downloads/)
- **Maven** (3.8 or higher) - [Download](https://maven.apache.org/download.cgi)
- **PostgreSQL** or **MySQL** - [PostgreSQL](https://www.postgresql.org/) | [MySQL](https://www.mysql.com/)
- **Git** - [Download](https://git-scm.com/)

### Installation

1. **Clone the repository**

   ```bash
   git clone https://github.com/yourusername/gharsaathi.git
   cd gharsaathi
   ```

2. **Setup Backend**

   ```bash
   cd backend
   
   # Configure database in application.properties
   # Edit src/main/resources/application.properties
   
   # Install dependencies
   mvn clean install
   ```

3. **Setup Frontend**

   ```bash
   cd frontend
   
   # Install dependencies
   pnpm install
   ```

### Configuration

#### Backend Configuration

Edit `backend/src/main/resources/application.properties`:

```properties
# Server Configuration
server.port=8080

# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/gharsaathi
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# JWT Configuration
jwt.secret=your-secret-key-here-min-256-bits
jwt.access-token-expiration=900000
jwt.refresh-token-expiration=604800000
```

#### Frontend Configuration

Create `.env.local` in the `frontend` directory:

```env
NEXT_PUBLIC_API_URL=http://localhost:8080/api
```

### Running the Application

#### Start Backend Server

```bash
cd backend
mvn spring-boot:run
```

The backend will start at `http://localhost:8080`

#### Start Frontend Development Server

```bash
cd frontend
pnpm dev
```

The frontend will start at `http://localhost:3000`

#### Build for Production

**Frontend:**

```bash
cd frontend
pnpm build
pnpm start
```

**Backend:**

```bash
cd backend
mvn clean package
java -jar target/gharsaathi-backend-0.0.1-SNAPSHOT.jar
```

---

## 📁 Project Structure

### Frontend Structure

```
frontend/
├── app/                    # Next.js 14 App Router
│   ├── layout.tsx         # Root layout
│   ├── page.tsx           # Home page
│   ├── loading.tsx        # Loading UI
│   ├── auth/              # Authentication
│   │   ├── login/
│   │   └── register/
│   ├── properties/        # Property listings
│   │   ├── page.tsx       # All properties
│   │   └── [id]/          # Property details
│   ├── tenant/            # Tenant dashboard
│   ├── landlord/          # Landlord dashboard
│   └── admin/             # Admin dashboard
├── components/            # React components
│   ├── ui/               # shadcn/ui components
│   ├── navbar.tsx
│   ├── footer.tsx
│   └── property-card.tsx
├── lib/                   # Utilities
│   ├── utils.ts
│   └── mock-data.ts
├── hooks/                 # Custom React hooks
├── styles/                # Global styles
└── public/                # Static assets
```

### Backend Structure

```
backend/
└── src/main/java/com/gharsaathi/
    ├── GharSaathiApplication.java    # Main application class
    ├── auth/                         # Authentication & authorization
    │   ├── controller/
    │   ├── service/
    │   ├── model/
    │   └── repository/
    ├── property/                     # Property management
    │   ├── controller/
    │   ├── service/
    │   ├── model/
    │   ├── repository/
    │   └── review/                   # Property reviews subdomain
    │       ├── controller/
    │       ├── service/
    │       ├── model/
    │       ├── repository/
    │       └── dto/
    ├── rental/                       # Rental domain
    │   └── application/              # Rental applications
    │       ├── controller/
    │       ├── service/
    │       ├── model/
    │       ├── repository/
    │       ├── dto/
    │       └── exception/
    ├── lease/                        # Lease management
    │   ├── controller/
    │   ├── service/
    │   ├── model/
    │   ├── repository/
    │   └── scheduler/
    ├── payment/                      # Payment system
    │   ├── controller/
    │   ├── service/
    │   ├── model/
    │   ├── repository/
    │   └── scheduler/
    ├── notification/                 # Notification system
    │   ├── controller/
    │   ├── service/
    │   ├── model/
    │   ├── repository/
    │   └── dto/
    └── common/                       # Shared components
        ├── dto/                      # Data Transfer Objects
        ├── security/                 # Security config
        │   ├── JwtUtil.java
        │   ├── JwtAuthenticationFilter.java
        │   └── SecurityConfig.java
        ├── exception/                # Exception handling
        └── util/                     # Utilities
```

---

## 📚 API Documentation

### Authentication Endpoints

#### Register User

```http
POST /api/auth/register
Content-Type: application/json

{
  "fullName": "John Doe",
  "email": "john@example.com",
  "password": "securePassword123",
  "phoneNumber": "9841234567",
  "role": "TENANT"
}
```

#### Login

```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "john@example.com",
  "password": "securePassword123"
}
```

**Response:**

```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tokenType": "Bearer",
  "expiresIn": 900000,
  "user": {
    "id": 1,
    "fullName": "John Doe",
    "email": "john@example.com",
    "role": "TENANT"
  }
}
```

#### Refresh Token

```http
POST /api/auth/refresh
Content-Type: application/json

{
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

#### Logout

```http
POST /api/auth/logout
Authorization: Bearer {accessToken}
Content-Type: application/json

{
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

> **Note**: Full API documentation will be available via Swagger UI at `/swagger-ui.html` (planned feature)

---

## 👥 User Roles

### 1. Tenant (TENANT)

- Browse and search properties
- Save favorite properties
- Submit rental applications
- View application status
- Update profile information

### 2. Landlord (LANDLORD)

- Create and manage property listings
- Review rental applications
- Accept/reject applications
- Communicate with tenants
- Access analytics dashboard

### 3. Administrator (ADMIN)

- Manage all users (approve, suspend, delete)
- Moderate property listings
- View system-wide analytics
- Manage platform settings
- Access to all features

---

## 🎨 Screenshots

### Home Page
>
> Landing page with featured properties and search functionality

### Property Listings
>
> Browse all available properties with filters and search

### Property Details
>
> Detailed view of a property with image gallery, amenities, and contact information

### Tenant Dashboard
>
> Overview of saved properties and application status

### Landlord Dashboard
>
> Property management and rental request tracking

### Admin Dashboard
>
> User and property management with analytics

---

## 🤝 Contributing

We welcome contributions to GharSaathi! Here's how you can help:

1. **Fork the repository**
2. **Create a feature branch**

   ```bash
   git checkout -b feature/AmazingFeature
   ```

3. **Commit your changes**

   ```bash
   git commit -m 'feat: add some amazing feature'
   ```

4. **Push to the branch**

   ```bash
   git push origin feature/AmazingFeature
   ```

5. **Open a Pull Request**

### Commit Convention

We follow conventional commits:

- `feat:` - New feature
- `fix:` - Bug fix
- `docs:` - Documentation changes
- `style:` - Code style changes (formatting, etc.)
- `refactor:` - Code refactoring
- `test:` - Adding tests
- `chore:` - Build process or auxiliary tool changes

---

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 📧 Contact

**Project Maintainer**: Your Name  
**Email**: <your.email@example.com>  
**GitHub**: [@yourusername](https://github.com/yourusername)

**Project Link**: [https://github.com/yourusername/gharsaathi](https://github.com/yourusername/gharsaathi)

---

## 🙏 Acknowledgments

- [Next.js](https://nextjs.org/) - The React framework
- [Spring Boot](https://spring.io/projects/spring-boot) - The Java framework
- [shadcn/ui](https://ui.shadcn.com/) - Beautiful UI components
- [Tailwind CSS](https://tailwindcss.com/) - Utility-first CSS framework
- [Lucide Icons](https://lucide.dev/) - Beautiful icons
- All contributors who helped shape this project

---

## 🗺️ Roadmap

- [ ] Property search with geolocation
- [ ] Real-time chat between landlords and tenants
- [ ] Payment gateway integration
- [ ] Email notifications
- [ ] Advanced analytics dashboard
- [ ] Mobile application (React Native)
- [ ] Property recommendations using ML
- [ ] Virtual property tours
- [ ] Multi-language support (Nepali/English)
- [ ] Property verification system

---

<div align="center">

**Made with ❤️ in Nepal**

⭐ Star this repo if you find it helpful!

</div>
