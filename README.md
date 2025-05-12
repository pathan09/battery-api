# Battery API

A Spring Boot REST API for managing battery data, including names, postcodes, and watt capacities. Supports bulk insertion and retrieving statistics for batteries within a postcode range.

## Features

- Bulk upload of batteries
- Fetch batteries by postcode range
- Calculate total and average watt capacity
- Integration tests using Testcontainers

## Tech Stack

- Java 17+
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Testcontainers
- JUnit 5
- Maven

## Running the Application
#### Make sure PostgreSQL is running and configured in application.yml.

- ./mvnw spring-boot:run

## Running Tests
#### Tests use Testcontainers to spin up a temporary PostgreSQL database for integration testing, and the system will have Docker installed.
- ./mvnw test

## API Endpoints

### `GET /api/batteries?minPostcode=2000&maxPostcode=3000`
Fetch batteries that fall within the specified postcode range. Results include sorted battery names and watt capacity statistics.

#### Query Parameters
minPostcode (required): lower bound of postcode range (inclusive)
maxPostcode (required): upper bound of postcode range (inclusive)

### `POST /api/batteries`
Save a list of batteries.

#### Request Body

```json
[
  {
    "name": "Alpha",
    "postcode": "2000",
    "wattCapacity": 100
  },
  {
    "name": "Beta",
    "postcode": "2200",
    "wattCapacity": 150
  }
]


