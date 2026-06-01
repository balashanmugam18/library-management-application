# 📚 Library Management — Spring Boot Backend

![Java-17-orange](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)
![SpringBoot-4.0.3-brightgreen](https://img.shields.io/badge/SpringBoot-4.0.3-brightgreen?style=for-the-badge&logo=springboot)
![PostgreSQL-blue](https://img.shields.io/badge/PostgreSQL-blue?style=for-the-badge&logo=postgresql)
![Maven-Build-red](https://img.shields.io/badge/Maven-Build-red?style=for-the-badge&logo=apachemaven)

## 🚀 Overview

A simple library management backend built with Spring Boot (4.x), JPA/Hibernate and PostgreSQL. The application manages books, members, and book transfers (holdings/loans) with basic validation and exception handling.

This repository contains a minimal service layer that coordinates multi-entity operations (for example transferring a book to a member) and demonstrates transactional boundaries, custom exceptions, repositories and REST controllers.

## 🛠 Tech Stack

- Java 17
- Spring Boot 4.x (WebMVC, Data JPA)
- Hibernate / JPA
- PostgreSQL (recommended) or H2 for quick testing
- Maven
- Lombok (may be used in entities)

## ✅ Features

- Book management: add books and query by author or title
- Member management: store member details and active status
- Transfer (loan) flow: create TransferBook records and decrement book stock
- Custom exceptions for invalid operations and global exception handling
- Basic validation and transactional service method for multi-entity changes

## 📂 Project Structure

```
src
├── main
│   ├── java
│   │   └── com.practice.librarymanagement
│   │       ├── controller        # REST controllers (e.g. LibController)
│   │       ├── entity            # JPA entities (Book, Member, TransferBook)
│   │       ├── exception         # CustomException, GlobalExceptionHandler
│   │       ├── libEnum           # Enums (e.g. Category)
│   │       ├── model             # DTOs/requests/responses (BookRequest, ErrorResponse)
│   │       ├── repository        # Spring Data repositories (BookRepository, MemberRepository, TransferBookRepository)
│   │       └── service           # Business logic (LibService)
│   └── resources
│       └── application.properties
└── test
    └── java
```

## 🔗 Key Components

- `Book` entity: title, author, isbn, category, availableCopies
- `Member` entity: memberId (String), activeStatus (boolean)
- `TransferBook` entity: links Book and Member with holdings, overDew and pending flags
- `LibService`: service layer where `transfer(memberId, bookTitle)` coordinates checks and persistence
- `LibController`: exposes REST endpoints to consume the service
- `CustomException` / `GlobalExceptionHandler`: map domain errors to HTTP responses

## 📡 API Endpoints (example)

Your controller exposes endpoints similar to the following (adjust paths & payloads according to your controller):

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET    | /books?author={author} | Get books by author |
| POST   | /books | Add a new book (consumes BookRequest) |
| POST   | /transfer | Transfer a book to a member (memberId, bookTitle) |

Exact paths and request shapes live in `LibController`.

## 🧭 Transactional guidance

- The transfer flow modifies multiple entities (decrements book stock and creates a TransferBook). Put `@Transactional` on that service method so the changes are atomic and roll back on error.
- Use `org.springframework.transaction.annotation.Transactional` for Spring transaction semantics.
- `CustomException` extends `RuntimeException` so Spring will roll back transactions by default when it is thrown.

## ⚙ Configuration (example)

Place DB credentials in `src/main/resources/application.properties`:

```
# Database (Postgres example)
spring.datasource.url=jdbc:postgresql://localhost:5432/librarydb
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

For quick testing you can switch to an embedded H2 profile.

## 🧪 Run & Test (Windows)

From the project root run (Windows Command Prompt):

```
.\mvnw.cmd clean package
.\mvnw.cmd spring-boot:run
```

Run tests:

```
.\mvnw.cmd test
```

After the app starts, call your endpoints (Postman/Insomnia/curl). If Swagger/OpenAPI is configured, check `http://localhost:8080/swagger-ui.html` or the configured path.

## ✅ Common checks / Troubleshooting

- If you see NoSuchElementException in the service, guard `Optional` before calling `.get()` (validate `isPresent()` first).
- Check your `@Transactional` import — prefer `org.springframework.transaction.annotation.Transactional`.
- Ensure repositories and services use correct stereotypes (`@Repository` for DAO implementations / Spring Data interfaces; `@Service` for business logic) so exception translation and AOP targeting behave as intended.
- If using Spring Data JPA interfaces (extend `JpaRepository`), you usually don't need to annotate them — Spring Data creates proxies for you.

## 🧩 Development notes & TODOs

- Add unit and integration tests for `LibService.transfer(...)` to assert rollback on error scenarios.
- Add validation (request DTO) and better error messages in `GlobalExceptionHandler`.
- Add paging/filtering endpoints for books and members.
- Consider adding authentication (JWT) and role-based access (admin vs member).

## 👨‍💻 Author


**Bala Shanmugam R**
**| Java Backend Developer**

⭐ **Star this repo if you find it helpful!** 🚀
