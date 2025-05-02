## FinanceM

## Description## Features
- **Flyway Database Migrations**: Manages versioned database schema migrations, ensuring consistency and easy updates across environments.

## Prerequisites
- Java 17 or higher
- Gradle 7.x or higher
- A compatible SQL database (e.g., MySQL, PostgreSQL)
- Flyway (configured in the project for database migrations)

## Getting Started


## Features
- **Spring Boot Framework**: Simplifies application development with built-in configurations and features.
- **Gradle Build Tool**: Efficient dependency management and build automation.
- **SQL Integration**: Database operations for data persistence and retrieval.
- **Java**: Core programming language for the application.
- **RESTful APIs**: Exposes endpoints for seamless integration with frontend or external systems.
- **Custom Exception Handling**: Provides meaningful error messages and improves debugging.
- **Logging**: Integrated logging using SLF4J and Logback for better monitoring and debugging.
- **Unit and Integration Testing**: Ensures code reliability and functionality.
- **Environment Profiles**: Supports multiple configurations (e.g., `dev`, `prod`).
- **Security**: Basic authentication and authorization using Spring Security.
- **Modular Pages**:
  - **Overview Page**: Displays all personal finance data at-a-glance.
  - **Transactions Page**: Lists all transactions with pagination (10 transactions per page), search, sort, and filter functionality.
  - **Budgets and Pots**: Allows users to create, read, update, and delete budgets and saving pots. Displays the latest three transactions for each budget category and progress towards each pot. Users can add or withdraw money from pots.
  - **Recurring Bills**: Displays recurring bills and their status for the current month. Includes search and sort functionality.
- **Form Validation**: Ensures required fields are completed, providing validation messages when necessary.
- **Keyboard Navigation**: Enables users to navigate the app and perform all actions using only their keyboard.
- **Responsive Design**: Optimized layout for various screen sizes, ensuring a seamless experience on all devices.
- **Interactive Elements**: Includes hover and focus states for all interactive elements.
- **Bonus Features**:
  - **Full-Stack Ready**: Save details to a database for persistent storage.
  - **User Authentication**: Allows users to create accounts and log in securely.

### Database Migrations with Flyway
Flyway is used to manage database schema migrations. To apply migrations, ensure your database is configured in the `application.properties` or `application.yml` file, then run the application. Flyway will automatically apply any pending migrations located in the `db/migration` directory.
FinanceM is a personal finance management application built with Spring Boot and Gradle. It leverages Java and SQL to provide a robust backend for managing and processing financial data. The app is designed to help users track their finances effectively, offering a user-friendly interface and powerful backend features.

## Prerequisites
- Java 17 or higher
- Gradle 7.x or higher
- A compatible SQL database (e.g., MySQL, PostgreSQL)

## Getting Started

### Clone the Repository
```bash
git clone https://github.com/friasmariano/financeM-api.git
cd financeM-api


## Prerequisites
- Java 17 or higher
- Gradle 7.x or higher
- A compatible SQL database (e.g., MySQL, PostgreSQL)

