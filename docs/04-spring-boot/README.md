# Spring Boot Documentation

> Spring Boot configuration and setup for GharSaathi

## 📋 Contents

### [Spring Boot Overview](./spring-boot-overview.md)
Introduction to Spring Boot, its role in GharSaathi, and key features utilized.

### [Profiles and Configuration](./profiles-and-config.md)
Environment profiles (dev, prod, test) and profile-specific configurations.

### [Application Properties](./application-properties.md)
Complete reference for `application.properties` with explanations for each setting.

### [Dependency Management](./dependency-management.md)
Maven dependencies, versioning strategy, and dependency analysis.

### [Actuator and Monitoring](./actuator-and-monitoring.md)
Health checks, metrics, and application monitoring setup.

### [External References](./external-references.md)
Official Spring documentation, guides, and external resources for Spring Boot development.

## 🚀 Spring Boot in GharSaathi

### Version
**Spring Boot 4.0.1** with Java 21

### Key Features Used
- ✅ Auto-configuration
- ✅ Embedded Tomcat server
- ✅ Spring Data JPA
- ✅ Spring Security
- ✅ Spring Web MVC
- ✅ Bean Validation
- ✅ DevTools for hot reload

### Project Structure
```
backend/
├── src/main/java/          # Java source code
├── src/main/resources/     # Configuration files
│   └── application.properties
├── src/test/java/          # Test code
├── pom.xml                 # Maven configuration
├── mvnw                    # Maven wrapper
└── target/                 # Build output
```

## ⚙️ Configuration Highlights

### Database Configuration
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/gharsaathi_db
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### Server Configuration
```properties
server.port=8080
server.servlet.context-path=/
```

### JWT Configuration
```properties
jwt.secret=your-secret-key
jwt.expiration=3600000
jwt.refresh.expiration=604800000
```

## 🔗 Quick Links

- [Application Properties Reference](./application-properties.md)
- [Architecture Overview](../02-architecture/system-architecture.md)
- [Security Configuration](../05-security/)

---

**Spring Boot Version**: 4.0.1  
**Java Version**: 21  
**Build Tool**: Maven 3.x  
**Last Updated**: January 28, 2026
