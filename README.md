# Retirement Planning System

A production-grade REST API system for automated retirement savings through expense-based micro-investments.

## Overview

This system implements automated savings mechanisms using expense rounding to help individuals save for retirement. It handles complex temporal constraints, validates financial transactions, calculates investment returns across multiple investment vehicles (NPS and Index Funds), and provides inflation-adjusted projections.

## Technology Stack

- **Java 21**
- **Spring Boot 4.0.3**
- **Maven**
- **Docker**
- **Lombok**

## Prerequisites

- Java 21 or higher
- Maven 3.6+
- Docker (for containerization)

## Building the Application

### Local Build

```bash
mvn clean package
```

### Docker Build

```bash
docker build -t blk-hacking-ind-mritunjay-rai .
```

## Running the Application

### Local Run

```bash
mvn spring-boot:run
```

Or run the JAR directly:

```bash
java -jar target/retirement-planning-system-0.0.1-SNAPSHOT.jar
```

### Docker Run

```bash
docker run -d -p 5477:5477 blk-hacking-ind-mritunjay-rai
```

The application will be available at: `http://localhost:5477/blackrock`

## API Endpoints

### 1. Transaction Parser
**POST** `/blackrock/challenge/v1/transactions:parse`

Parses expenses and calculates ceiling and remanent values.

**Request:**
```json
[
  {"date": "2023-10-12 20:15:30", "amount": 250},
  {"date": "2023-02-28 15:49:20", "amount": 375}
]
```

**Response:**
```json
[
  {"date": "2023-10-12 20:15:30", "amount": 250, "ceiling": 300, "remanent": 50},
  {"date": "2023-02-28 15:49:20", "amount": 375, "ceiling": 400, "remanent": 25}
]
```

### 2. Transaction Validator
**POST** `/blackrock/challenge/v1/transactions:validator`

Validates transactions for negative amounts and duplicates.

**Request:**
```json
{
  "wage": 50000,
  "transactions": [
    {"date": "2023-01-15 10:30:00", "amount": 2000, "ceiling": 300, "remanent": 50}
  ]
}
```

**Response:**
```json
{
  "valid": [...],
  "invalid": [...]
}
```

### 3. Temporal Constraints Filter
**POST** `/blackrock/challenge/v1/transactions:filter`

Applies q, p, and k period rules to transactions.

**Request:**
```json
{
  "q": [{"fixed": 0, "start": "2023-07-01 00:00:00", "end": "2023-07-31 23:59:59"}],
  "p": [{"extra": 25, "start": "2023-10-01 08:00:00", "end": "2023-12-31 19:59:59"}],
  "k": [{"start": "2023-03-01 00:00:00", "end": "2023-11-30 23:59:59"}],
  "transactions": [...]
}
```

### 4. NPS Returns Calculation
**POST** `/blackrock/challenge/v1/returns:nps`

Calculates returns for NPS investment (7.11% with tax benefits).

**Request:**
```json
{
  "age": 29,
  "wage": 50000,
  "inflation": 5.5,
  "q": [...],
  "p": [...],
  "k": [...],
  "transactions": [...]
}
```

**Response:**
```json
{
  "totalTransactionAmount": 1725.0,
  "totalCeiling": 1900.0,
  "savingsByDates": [
    {
      "start": "2023-01-01 00:00:00",
      "end": "2023-12-31 23:59:59",
      "amount": 145.0,
      "profit": 86.88,
      "taxBenefit": 0.0
    }
  ]
}
```

### 5. Index Fund Returns Calculation
**POST** `/blackrock/challenge/v1/returns:index`

Calculates returns for Index Fund investment (14.49%, no tax benefits).

Same request/response structure as NPS endpoint.

### 6. Performance Metrics
**GET** `/blackrock/challenge/v1/performance`

Returns system performance metrics.

**Response:**
```json
{
  "time": "1970-01-01 00:11:44.133",
  "memory": "95.11",
  "threads": 16
}
```

## Business Logic

### Rounding Strategy
- Each expense is rounded up to the next multiple of 100
- The difference (remanent) is invested
- Example: ₹250 → ceiling ₹300, remanent ₹50

### Period Rules

**q Periods (Fixed Amount Override):**
- Replaces calculated remanent with fixed amount
- If multiple q periods match, uses the one with latest start date

**p Periods (Extra Amount Addition):**
- Adds extra amount to remanent
- Multiple p periods are cumulative

**k Periods (Evaluation Grouping):**
- Groups transactions for return calculation
- Transactions can belong to multiple k periods

### Investment Options

**NPS (National Pension Scheme):**
- Interest rate: 7.11% compounded annually
- Tax benefit: Up to ₹2,00,000 or 10% of annual income
- Tax slabs applied as per Indian tax structure

**Index Fund (NIFTY 50):**
- Interest rate: 14.49% compounded annually
- No tax benefits or restrictions

### Calculations

**Compound Interest:**
```
A = P × (1 + r)^t
```

**Inflation Adjustment:**
```
Real Value = A / (1 + inflation)^t
```

## Testing

Run tests with:
```bash
mvn test
```

## Docker Compose

If you need additional services, use:
```bash
docker-compose up
```

## Project Structure

```
src/main/java/com/github/mritunjayr/retirement_planning_system/
├── controller/
│   └── TransactionController.java
├── dto/
│   ├── ExpenseRequest.java
│   ├── Transaction.java
│   ├── ValidatorRequest.java
│   ├── ValidatorResponse.java
│   ├── FilterRequest.java
│   ├── FilterResponse.java
│   ├── ReturnsRequest.java
│   ├── ReturnsResponse.java
│   └── ...
├── service/
│   ├── TransactionService.java
│   ├── FilterService.java
│   ├── ReturnsService.java
│   └── PerformanceService.java
├── util/
│   ├── DateUtil.java
│   └── CalculationUtil.java
└── RetirementPlanningSystemApplication.java
```

## Configuration

Application runs on:
- **Port:** 5477
- **Context Path:** /blackrock
- **Base API Path:** /challenge/v1

Configuration can be modified in `src/main/resources/application.yaml`

## Author

Mritunjay Rai

## License

This project is part of a coding challenge submission.
