# 🛒 Order Service — E-Commerce Microservice

A production-ready **Order Service** built with **Spring Boot 3.5.4** as part of a microservices-based e-commerce application. Follows **SOLID principles**, clean architecture, and industry-standard best practices.

---

## 📌 Table of Contents

- [Overview](#overview)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [API Endpoints](#api-endpoints)
- [Request & Response Examples](#request--response-examples)
- [Validation Rules](#validation-rules)
- [Exception Handling](#exception-handling)
- [SOLID Principles Applied](#solid-principles-applied)
- [Database Configuration](#database-configuration)
- [How to Run](#how-to-run)

---

## 📖 Overview

The **Order Service** is responsible for:
- Placing new customer orders
- Managing order line items
- Tracking order status (PENDING → CONFIRMED → SHIPPED → DELIVERED)
- Calculating total order amount
- Handling exceptions with proper HTTP status codes

---

## 🛠 Tech Stack

| Technology        | Version   | Purpose                    |
|-------------------|-----------|----------------------------|
| Java              | 24        | Programming language       |
| Spring Boot       | 3.5.4     | Application framework      |
| Spring Data JPA   | 3.5.2     | Database ORM               |
| Spring Validation | 3.5.4     | Request validation         |
| MySQL             | 8.0.35    | Relational database        |
| Lombok            | 1.18.38   | Boilerplate reduction      |
| HikariCP          | 6.3.1     | Connection pooling         |
| Hibernate         | 6.6.22    | ORM implementation         |
| Maven             | -         | Build tool                 |

---

## 📁 Project Structure---

## 🚀 Getting Started

### Prerequisites

- Java 17+
- Maven 3.8+
- MySQL 8.0+
- IntelliJ IDEA
- Postman

### Clone the Repository

```bash
git clone https://github.com/your-username/order-service.git
cd order-service
```

---

## ⚙️ Database Configuration

Create the database in MySQL:

```sql
CREATE DATABASE order_db;
```

Update `src/main/resources/application.properties`:

```properties
spring.application.name=order-service
server.port=8081

spring.datasource.url=jdbc:mysql://localhost:3306/order_db
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.open-in-view=false
```

---

## ▶️ How to Run

```bash
mvn clean install
mvn spring-boot:run
```

App starts at: `http://localhost:8081`

---

## 📡 API Endpoints

| Method   | URL                                | Description                  | Status |
|----------|------------------------------------|------------------------------|--------|
| POST     | /api/order                         | Place a new order            | 201    |
| GET      | /api/order/{id}                    | Get order by ID              | 200    |
| GET      | /api/order/number/{orderNumber}    | Get order by number          | 200    |
| GET      | /api/order/customer/{customerId}   | Get all orders by customer   | 200    |
| PUT      | /api/order/{id}/status             | Update order status          | 200    |
| DELETE   | /api/order/{id}                    | Cancel order                 | 200    |

---

## 📨 Request & Response Examples

### Place Order — POST /api/order

**Request Body:**
```json
{
    "customerId": 1,
    "shippingAddress": "123 MG Road, Bangalore, Karnataka - 560001",
    "billingAddress": "123 MG Road, Bangalore, Karnataka - 560001",
    "paymentMethod": "UPI",
    "couponCode": "SAVE10",
    "orderLineItemsDtoList": [
        {
            "skuCode": "IPHONE-15-BLK",
            "price": 79999.00,
            "quantity": 1
        },
        {
            "skuCode": "AIRPODS-PRO",
            "price": 24999.00,
            "quantity": 2
        }
    ]
}
```

**Success Response — 201 Created:**
```json
{
    "orderId": 1,
    "orderNumber": "550e8400-e29b-41d4-a716",
    "status": "PENDING",
    "message": "Order Placed Successfully",
    "customerId": 1,
    "orderLineItems": [
        {
            "skuCode": "IPHONE-15-BLK",
            "price": 79999.00,
            "quantity": 1,
            "totalPrice": 79999.00
        },
        {
            "skuCode": "AIRPODS-PRO",
            "price": 24999.00,
            "quantity": 2,
            "totalPrice": 49998.00
        }
    ],
    "totalAmount": 129997.00,
    "createdAt": "2024-01-15T10:30:00"
}
```

---

## ✅ Validation Rules

| Field                   | Rule                  | Error Message                     |
|-------------------------|-----------------------|-----------------------------------|
| customerId              | Not null              | Customer ID is required           |
| orderLineItemsDtoList   | Not null, not empty   | Order must have at least one item |
| shippingAddress         | Not blank             | Shipping address is required      |
| paymentMethod           | Not blank             | Payment method is required        |
| skuCode                 | Not blank             | SKU code is required              |
| price                   | Greater than 0        | Price must be greater than 0      |
| quantity                | Minimum 1             | Quantity must be at least 1       |

---

## ⚠️ Exception Handling

| Exception                         | Status | Error                 |
|-----------------------------------|--------|-----------------------|
| OrderNotFoundException            | 404    | Order Not Found       |
| OrderServiceException             | 500    | Order Service Error   |
| MethodArgumentNotValidException   | 400    | Validation Failed     |
| IllegalArgumentException          | 400    | Invalid Argument      |
| Exception                         | 500    | Internal Server Error |

**Error Response Format:**
```json
{
    "status": 404,
    "error": "Order Not Found",
    "message": "Order not found with id : 99",
    "timestamp": "2024-01-15T10:30:00"
}
```

---

## 🏗 SOLID Principles Applied

| Principle               | Implementation                                                        |
|-------------------------|-----------------------------------------------------------------------|
| Single Responsibility   | OrderMapper, OrderPriceCalculator each have one job                  |
| Open/Closed             | Add new handlers without modifying existing code                     |
| Liskov Substitution     | OrderServiceImpl swappable via IOrderService interface               |
| Interface Segregation   | IOrderService focused with only order-related methods                |
| Dependency Inversion    | OrderController depends on IOrderService not concrete class          |

---

## 📊 Order Status Flow
PENDING → CONFIRMED → PROCESSING → SHIPPED → DELIVERED → CANCELLED / REFUNDED
