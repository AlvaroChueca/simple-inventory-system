# Simple Inventory System

This is a simple inventory system REST API built with Spring Boot.

## Features

* List products
* Get a product by ID
* Create a new product
* Update an existing product
* Delete a product

## Technologies Used

* Java 21
* Spring Boot
* Spring Data JPA
* Spring Web MVC
* PostgreSQL
* Lombok
* Maven

## Getting Started

### Prerequisites

* Java 21 or higher
* Maven
* PostgreSQL

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/simple-inventory-system.git
   ```
2. Configure the database in `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/inventory
   spring.datasource.username=postgres
   spring.datasource.password=admin
   ```
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

## API Endpoints

| Method | Endpoint              | Description          |
|--------|-----------------------|----------------------|
| GET    | `/api/products`       | Get all products     |
| GET    | `/api/products/{id}`  | Get a product by ID  |
| POST   | `/api/products`       | Create a new product |
| PUT    | `/api/products/{id}`  | Update a product     |
| DELETE | `/api/products/{id}`  | Delete a product     |
