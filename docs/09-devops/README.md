# DevOps Documentation

> Deployment, operations, and infrastructure documentation for GharSaathi

## 📋 Contents

### [Environment Setup](./environment-setup.md)
Complete guide to setting up development, staging, and production environments.

### [Build and Run](./build-and-run.md)
Step-by-step instructions for building and running the application locally.

### [Dockerization](./dockerization.md)
Docker containerization strategy including Dockerfile and docker-compose configurations.

### [CI/CD Pipeline](./ci-cd-pipeline.md)
Continuous Integration and Continuous Deployment pipeline setup and workflows.

### [Production Deployment](./production-deployment.md)
Production deployment procedures, configurations, and best practices.

## 🚀 Quick Start

### Development Environment Setup

#### Prerequisites
- Java 21 (JDK)
- Node.js 18+ (with pnpm)
- MySQL 8.0+
- Git
- IDE (VS Code / IntelliJ IDEA)

#### Backend Setup
```bash
cd backend
./mvnw clean install
./mvnw spring-boot:run
```

Server runs on: `http://localhost:8080`

#### Frontend Setup
```bash
cd frontend
pnpm install
pnpm dev
```

Application runs on: `http://localhost:3000`

#### Database Setup
```sql
CREATE DATABASE gharsaathi_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

Configure in `backend/src/main/resources/application.properties`

## 📦 Build Process

### Backend Build
```bash
# Clean and build
./mvnw clean package

# Output: target/backend-0.0.1-SNAPSHOT.jar

# Run JAR
java -jar target/backend-0.0.1-SNAPSHOT.jar
```

### Frontend Build
```bash
# Production build
pnpm build

# Output: .next/ directory

# Start production server
pnpm start
```

## 🐳 Docker Deployment

### Docker Compose (Full Stack)
```bash
docker-compose up -d
```

Services:
- Backend: `http://localhost:8080`
- Frontend: `http://localhost:3000`
- MySQL: `localhost:3306`

### Individual Containers
```bash
# Backend
docker build -t gharsaathi-backend ./backend
docker run -p 8080:8080 gharsaathi-backend

# Frontend
docker build -t gharsaathi-frontend ./frontend
docker run -p 3000:3000 gharsaathi-frontend
```

## 🔧 Environment Configuration

### Development
```properties
Environment: development
Database: localhost:3306
Debug: enabled
CORS: http://localhost:3000
```

### Production
```properties
Environment: production
Database: production-db-url
Debug: disabled
CORS: https://gharsaathi.com
SSL: enabled
```

## 📊 Deployment Checklist

### Pre-Deployment
- [ ] All tests passing
- [ ] Database backed up
- [ ] Environment variables configured
- [ ] SSL certificates ready
- [ ] Domain DNS configured
- [ ] Monitoring setup

### During Deployment
- [ ] Build artifacts
- [ ] Database migrations
- [ ] Deploy backend
- [ ] Deploy frontend
- [ ] Health checks
- [ ] Smoke tests

### Post-Deployment
- [ ] Verify all endpoints
- [ ] Check logs
- [ ] Monitor performance
- [ ] Test critical flows
- [ ] Update documentation

## 🔍 Monitoring & Health Checks

### Application Health
```bash
# Backend health check
curl http://localhost:8080/actuator/health

# Response: {"status":"UP"}
```

### Monitoring Points
- Application uptime
- API response times
- Database connection pool
- Error rates
- Memory usage
- CPU usage

## 🔗 Quick Reference

### Environment Variables
```bash
# Backend
SPRING_PROFILES_ACTIVE=production
DATABASE_URL=jdbc:mysql://...
JWT_SECRET=your-secret-key

# Frontend
NEXT_PUBLIC_API_URL=http://localhost:8080/api/v1
```

### Port Configuration
- Backend: 8080
- Frontend: 3000
- MySQL: 3306

## 🔗 Related Documentation

- [Environment Setup Details](./environment-setup.md)
- [Architecture](../02-architecture/)
- [Spring Boot Configuration](../04-spring-boot/)

---

**Deployment**: Local / Docker / Cloud  
**CI/CD**: Planned (GitHub Actions)  
**Monitoring**: Spring Boot Actuator  
**Last Updated**: January 28, 2026
