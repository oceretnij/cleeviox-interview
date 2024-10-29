# Project Setup

## Prerequisites
- Docker
- Maven
- Java

## Steps to Run the Project

1. **Start Docker Containers**
   ```sh
   docker compose up
   ```

2. **Install Dependencies**
   ```sh
   mvn clean install -DskipTests
   ```

3. **Run Spring Boot Application**
   ```sh
   mvn spring-boot:run
   ```
