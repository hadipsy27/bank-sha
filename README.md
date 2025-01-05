# Springboot Application

## Introduction

This is a Springboot Application that is used to manage the banking system.

## Features

- User Management
- Wallet Management
- Data Plan Management
- Payment Method Management
- Transaction Management
- Transfer Management
- Tip Management

## Installation

1. Clone the repository
2. Open the project in your IDE
3. Run the application (Configure the database connection in the application.properties file)
4. To seed the database, run unit tests

## Commands

1. To run the application, use the following command:

```
mvn spring-boot:run
```

2. To run unit tests, use the following command:

```
mvn test
```

3. To run Clean and Install without seeding the database (without run unit tests), use the following command:

```
mvn clean install -DskipTests
```