# Restaurant Search API

A Spring Boot + MongoDB application that helps users find nearby restaurants based on their location (x, y coordinates). Restaurants are stored with visibility radii, and the system filters results dynamically.
## 📦 Tech Stack

- Java 21
- Spring Boot
- MongoDB
- MapStruct
- JUnit 5, Mockito
- Maven
- Spring Cache (for GET operations)
## 🛠️ Setup Instructions

### 🔧 Prerequisites

Make sure you have the following installed:
- Java 21
- Maven
- Docker (If you prefer setup via containers)
- MongoDB (either via Docker or standalone setup, see below)

Inorder to build & run the application, follow these steps:
1) Build the application :
```bash
mvn clean package
```

2) The Application startup depends on MongoDB instance, which can be easily setup using `Docker Compose`.

Install `docker-compose` if you haven't already.
   The `local-environment/docker-compose.db.yml` file contains the configuration for the MongoDB container, including dataset initialization.
To start the MongoDB container, run the following command in the terminal from the project root directory:
```bash
docker compose -f local-environment/docker-compose.db.yml up -d
```
   `OR`
   If you prefer to setup mongodb with standalone setup, [Follow the instructions here](Mongo-standalone-setup.md) to setup without Docker.

3) Run the application locally(port: `8082`):
  
```bash
mvn spring-boot:run
```

`OR`
If you prefer via `Docker`🐳setup , ensure you have Docker and Docker Compose installed on your machine.
You can download them from the [official Docker website](https://www.docker.com/products/docker-desktop).
and then build the Docker image using the following command:
```bash
docker build -t restaurant-search .
```
and then run the application using Docker Compose:
```bash
docker compose -f local-environment/docker-compose.app.yml up -d
```
Once application is running, you can access it at swagger `http://localhost:8082/swagger-ui/index.html`.

To bring down the application run:
```bash
docker compose -f local-environment/docker-compose.app.yml down
```

### 🧪 Running Tests
You can run the tests using Maven:
```bash
mvn clean test
```
JUnit and Mockito are used to test service, controller, and mapper layers.

### 📁 Loading Sample Data
Sample restaurant records are provided in:
```bash
src/main/resources/locations.json
```

### 📌 API Endpoints
- **GET** Search Nearby Restaurants
  - {baseurl}/locations/search?x={x}&y={y}
- **GET** Get Restaurant by ID
  - {baseurl}/locations/{id}
- **PUT** Update or Add Restaurant
  - {baseurl}/locations/{id}

### 📄 Swagger Documentation
The API documentation is available via Swagger UI. You can access it at:
- [Swagger UI](http://localhost:8082/swagger-ui/index.html)

#### 🗂️ Project Structure
```bash
restaurant-search/
├── Dockerfile
├── local-environment
      ├── docker-compose.db.yml
      ├── docker-compose.app.yml
├── README.md
├── src/
      ├── controller        # REST endpoints
      ├── service           # Business logic
      ├── config            # Configuration
      ├── model             # Entities (Restaurant, Coordinates)
      ├── dto               # Request/Response DTOs
      ├── mapper            # MapStruct mappers
      ├── repository        # MongoDB access layer
      └── resources
        ├── locations.json  # Sample data
        └── application.properties  # Configuration
      └── test
        ├── service       # Unit tests for services
        ├── mapper        # Unit tests for mappers
        ├── repository   # Unit tests for repository
        └── resources     # Test resources
``` 

### 💡 Technical Decisions

- Spring Boot: Chosen for its production-ready setup, rapid development capabilities, and strong support for REST APIs and integration with MongoDB via Spring Data.
- MongoDB: A NoSQL document database ideal for flexible and scalable storage.
- MapStruct: Enables compile-time mapping between domain entities and DTOs, minimizing boilerplate code and improving maintainability and testability.
- Java 21: Latest LTS version (as of 2023) with cutting-edge language features, performance improvements, and long-term support.
- JUnit 5 & Mockito: Industry-standard libraries for unit and integration testing, offering modularity and robust mocking capabilities.
- Spring Cache: Used to cache frequent read operations (GET /locations/search) to improve response time and reduce database hits.
- Docker: Docker & Docker Compose simplifies the setup of isolated, reproducible development environments.

### Future scope (if I had more time)
- **Pagination**: Add pagination to search results for better performance with large datasets.
- **Logging Improvements**: Implement structured logging for better observability.
- **Resiliency**: Add circuit breaker patterns, retry mechanisms, and fallback methods for better fault tolerance.
