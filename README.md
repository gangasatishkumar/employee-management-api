# Employee Management API

## Overview

This is a Spring Boot-based Employee Management API that allows you to manage employee details and compute tax deductions based on their salary. The API includes endpoints to create employee records and retrieve tax deductions.

## Features

- **Store Employee Details**: An API endpoint to store employee details with validation (mandatory fields).
- **Tax Deduction Calculation**: An API to calculate the tax deductions for an employee based on their salary and date of joining (DOJ).
- **In-memory H2 Database**: Utilizes an H2 in-memory database for quick development and testing.
- **Error Handling**: Proper validation and error handling for invalid data.

## Technologies Used

- Spring Boot 3.3.4
- Java 17
- H2 Database (In-memory)
- REST APIs
- JPA (Optional for database interaction, though in-memory used here)

---

## Setup

### Prerequisites

- Java 17 or higher
- Maven or Gradle (depending on your project setup)

### Clone the Repository

git clone https://github.com/your-repository/employee-management-api.git
cd employee-management-api

Build the Project
If you are using Maven:


mvn clean install
If you are using Gradle:

gradle build
Running the Application
To run the application locally, use the following command:

mvn spring-boot:run
Alternatively, you can run the application from your IDE.

Access the Application
Once the application is up and running, you can access the API at:


http://localhost:8080/api/employees
You can also access the H2 console at:


http://localhost:8080/h2-console
The database connection URL for the H2 console is:


jdbc:h2:mem:testdb
Username: sa
Password: (empty)
----

# API Endpoints
## 1. Create Employee
### POST /api/employees

This endpoint is used to create a new employee. The request body must contain all required fields.

Request Body (JSON):

{
  "employeeId": 1,
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "phoneNumbers": ["1234567890", "0987654321"],
  "doj": "2023-01-01",
  "salary": 50000
}
Response (Success):
HTTP Status: 201 Created


{
  "employeeId": 1,
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "phoneNumbers": ["1234567890", "0987654321"],
  "doj": "2023-01-01",
  "salary": 50000
}
Response (Error):
HTTP Status: 400 Bad Request


{
  "error": "Salary must be a positive number."
}
## 2. Get Tax Deduction for Employee

### GET /api/employees/{employeeId}/tax-deduction

This endpoint retrieves the tax deduction details for a specific employee based on the employee ID.

### Response (Success):
**HTTP Status:** 200 OK

```json
{
  "employeeId": "1",
  "firstName": "John",
  "lastName": "Doe",
  "yearlySalary": 600000,
  "taxAmount": 30000,
  "cessAmount": 0
}
```


# Constants

## Employee Fields
- **employeeId**: Unique identifier for the employee (integer).
- **firstName**: Employee's first name (String).
- **lastName**: Employee's last name (String).
- **email**: Employee's email address (String).
- **phoneNumbers**: List of phone numbers (String[]).
- **doj**: Date of joining (Date, in the format YYYY-MM-DD).
- **salary**: Employee's monthly salary (double).

## Tax Calculation Constants

### Income Tax Slabs:
- 5% tax on yearly income above ₹250,000.
- 10% tax on yearly income above ₹500,000.
- 20% tax on yearly income above ₹1,000,000.

### Cess:
- 2% on yearly salary exceeding ₹2,500,000.

## Configuration

The application uses an in-memory H2 database, which is configured in the `application.yml`:

```yaml
spring:
  application:
    name: Employee Management API
  datasource:
    url: jdbc:h2:mem:testdb
    driver-class-name: org.h2.Driver
    username: sa
    password:
  h2:
    console:
      enabled: true
server:
  port: 8080



spring:
  application:
    name: Employee Management API
  datasource:
    url: jdbc:h2:mem:testdb
    driver-class-name: org.h2.Driver
    username: sa
    password:
  h2:
    console:
      enabled: true
server:
  port: 8080

```

## Constants File

The application uses constants for tax calculations defined in `TaxServiceConstants.java`:

```java
package com.example.employee.constants;

public class TaxServiceConstants {
    public static final int FINANCIAL_YEAR_START_MONTH = 4;
    public static final int FINANCIAL_YEAR_END_MONTH = 3;
    public static final double TAX_THRESHOLD_5_PERCENT = 250000;
    public static final double TAX_THRESHOLD_10_PERCENT = 500000;
    public static final double TAX_RATE_5_PERCENT = 0.05;
    public static final double TAX_RATE_10_PERCENT = 0.10;
    public static final double TAX_RATE_20_PERCENT = 0.20;
    public static final double CESS_RATE_2_PERCENT = 0.02;
    public static final double TAX_THRESHOLD_10_PERCENT_CESS = 2500000;
    public static final String INVALID_SALARY_MESSAGE = "Salary must be a positive number.";
    public static final String INVALID_DOJ_MESSAGE = "Date of Joining (DOJ) must be a valid past or current date.";
}

``

# License
This project is licensed under the MIT License - see the LICENSE file for details.

This README.md provides an outline of the application, including its structure, setup, and how to interact with the API endpoints.


---

This version correctly uses Markdown features such as code blocks, headings, and bullet poin
