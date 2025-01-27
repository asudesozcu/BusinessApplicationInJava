# Business Application Programming in Java (BAPJ) - Project Overview  

This repository contains the implementation for the **Business Application Programming in Java (BAPJ)** course.  
This project implements a banking system using **Java**, **JPA**, **Jakarta EE**, and various unit testing frameworks. It is divided into five tasks, each introducing new functionalities and improving the user experience.  

---

## Table of Contents  

1. [Task 1: Unit Testing](#task-1-unit-testing)  
2. [Task 2: JPA Integration](#task-2-jpa-integration)  
3. [Task 3: JPA Account Operations](#task-3-jpa-account-operations)  
4. [Task 4: Jakarta EE Containers and Servers](#task-4-jakarta-ee-containers-and-servers)  
5. [Task 5: Jakarta EE Authentication](#task-5-jakarta-ee-authentication)  

---

## Task 1: Unit Testing  

In this phase, unit tests were written for the `Bank` interface, and the core functionalities of the system were implemented.  

### Features:  
- Basic banking operations:  
  - Creating accounts  
  - Depositing and withdrawing funds  
  - Transferring funds between accounts  
- Custom exception handling:  
  - `AccountIdException` for invalid account IDs  
  - `InsufficientFundsException` for insufficient balance  
- High test coverage reports were generated using **JaCoCo**.  

---

## Task 2: JPA Integration  

In this phase, the application transitioned to persistent data storage using JPA and an H2 database.  

### Features:  
- Replaced in-memory account storage with a JPA-powered `AccountRepository`.  
- Utilized the H2 database in embedded mode for easy testing and development.  
- Updated existing tests to work seamlessly with JPA integration.  
- Enhanced test coverage for CRUD operations and ensured data integrity.  

---

## Task 3: JPA Account Operations  

This phase focused on tracking account operations.  

### Features:  
- Introduced the following operations:  
  - Withdrawal  
  - Deposit  
  - Transfer  
- Defined and implemented account operation management (saving/deleting operations).  
- Verified **JPQL queries** with comprehensive unit tests.  

---

## Task 4: Jakarta EE Containers and Servers  

This phase improved the user interface and introduced new functionalities.  

### Features:  
- Added an editable **Notes** field to the account table.  
- Enabled sorting for table columns.  
- Introduced the option to deactivate accounts instead of deleting them, preserved historical data.  
- Added user confirmation prompts for specific operations.  

---

## Task 5: Jakarta EE Authentication  

The final phase implemented user authentication and access control.  

### Features:  
- Secured customer and account information through username and password authentication.  
- Created a dedicated admin page for user management.  

---


