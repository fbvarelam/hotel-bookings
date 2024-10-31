# TestApp

## Author

- **Fernando Varela Martínez**

## Description

Bookings is a Spring Boot application which implement a REST API for hotel booking searches.

## Technologies Used

- Java 22
- Spring Boot 3.3.3
- Maven
- H2 Database
- Swagger/OpenAPI
- Docker

## Getting Started

### API Documentation

The hotel-bookings-api.yml file contains the API documentation. You can
import this file to Postman or Swagger UI to see the API documentation.

### Application

1. Build the project:

```sh
  ./mvnw clean install
```

2. Run the application:

```sh
./mvnw spring-boot:run
```

### Running Tests

- To run the tests, use the following command:

```sh
./mvnw test  
```

### Generate API classes

- To generate API Java interfaces and models, use the following command:

```sh
./mvnw generate-sources 
```

### Docker

The file *docker-compose.yaml* is used to spin up kafka and Zookeeper containers.  
You do it manually using the following command:

```sh
docker-compose down
docker-compose up
```

### Postman

The file *hotel-bookings-api.postman_collection.json* in resources contains a Postman collection with some examples of
requests.