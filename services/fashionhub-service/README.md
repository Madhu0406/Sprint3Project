# FashionHub Microservice

This is the refactored FashionHub backend application as a microservice, migrated from MySQL to MS SQL Server.

## Overview

This microservice contains all the functionality from the original fashionhub-backend, including:
- User authentication (registration/login)
- Product management and search
- Category management
- RESTful API endpoints

## Database Configuration

The microservice now uses **MS SQL Server** instead of MySQL with the following configurations:
- **Runtime Database**: MS SQL Server with SQLServerDialect
- **Test Database**: H2 in-memory database for testing

## Configuration

### MS SQL Server Configuration

Update `application.properties` with your MS SQL Server details:

```properties
# Database Configuration
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=fashionhub_db;encrypt=true;trustServerCertificate=true
spring.datasource.username=sa
spring.datasource.password=Password123!
spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.SQLServerDialect
```

## API Endpoints

All original REST API endpoints are preserved:

### Authentication
- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login

### Products
- `GET /api/products` - Get all products
- `GET /api/products/{productId}` - Get product by ID
- `GET /api/products/category/{categoryId}` - Get products by category
- `GET /api/products/search?keyword={keyword}` - Search products
- `GET /api/products/search/category?categoryId={categoryId}&keyword={keyword}` - Search products by category and keyword

### Categories
- `GET /api/categories` - Get all categories
- `GET /api/categories/with-count` - Get categories with product count

## Building and Running

### Prerequisites
- Java 17
- Maven 3.6+
- MS SQL Server (for production)

### Build
```bash
mvn clean compile
```

### Test
```bash
mvn test
```

### Run
```bash
mvn spring-boot:run
```

## Entities

- **User**: User account information
- **Product**: Product catalog with details, pricing, and inventory
- **Category**: Product categories
- **ProductSize**: Available sizes for products
- **Offer**: Special offers on products

## Key Changes from Original

1. **Database**: Migrated from MySQL to MS SQL Server
2. **Dependencies**: Updated to use `mssql-jdbc` driver
3. **Hibernate Dialect**: Changed to `SQLServerDialect`
4. **Column Definitions**: Updated TEXT columns to use `NVARCHAR(MAX)` for MS SQL Server compatibility
5. **Test Configuration**: Added H2 dependency for testing

## Future Decomposition

The microservice is designed with modularity in mind for future decomposition:
- **Auth Service**: User authentication and authorization
- **Product Service**: Product catalog management
- **Category Service**: Category management

Each domain is clearly separated with its own controllers, services, repositories, and entities.