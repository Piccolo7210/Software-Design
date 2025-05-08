# User Management System

## Overview

The User Management System is a Spring Boot application designed to manage users and roles through a RESTful API. It follows Clean Architecture principles, separating the application into layers (domain, application, infrastructure, and config). The system allows creating users and roles, assigning roles to users, and retrieving user details.

### Features
- Create a user with a name and email.
- Create a role with a role name.
- Assign a role to a user.
- Retrieve user details, including assigned roles.
- In-memory H2 database for persistence.
- Unit tests for the application layer.

## Project Structure

- **Domain Layer**: Contains core business entities (`User`, `Role`).
- **Application Layer**: Contains business logic (`UserService`, `RoleService`) and interfaces (`UserRepository`, `RoleRepository`).
- **Infrastructure Layer**: Contains persistence logic (`UserJpaRepository`, `RoleJpaRepository`, `UserSpringDataRepository`, `RoleSpringDataRepository`).
- **Config Layer**: Contains Spring configuration (`BeanConfig`).
- **Tests**: Unit tests in `src/test/java/org.example.usermanagement/application`.

## Prerequisites

- **Java**: JDK 17 (or 24, as per your setup).
- **Maven**: 3.6.3 or higher.
- **IntelliJ IDEA**: Recommended for development (or any IDE supporting Spring Boot).
- **Postman**: For testing the REST API (download from https://www.postman.com/downloads/).

## Setup Instructions

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/Piccolo7210/Sem6
   cd SoftwareDesign/usermangement
   ```

2. **Import the Project into IntelliJ IDEA**:
    - Open IntelliJ IDEA.
    - Select `File > Open` and choose the `UserManagement` directory.
    - IntelliJ will automatically detect the `pom.xml` file and set up the project.

3. **Verify Dependencies**:
    - Ensure the `pom.xml` includes necessary dependencies:
      ```xml
      <dependency>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter-web</artifactId>
      </dependency>
      <dependency>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter-data-jpa</artifactId>
      </dependency>
      <dependency>
          <groupId>com.h2database</groupId>
          <artifactId>h2</artifactId>
          <scope>runtime</scope>
      </dependency>
      <dependency>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter-validation</artifactId>
      </dependency>
      <dependency>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter-test</artifactId>
          <scope>test</scope>
      </dependency>
      ```

4. **Run the Application**:
    - Right-click `UserManagementApplication.java` (in `src/main/java/org.example.usermanagement`) and select `Run 'UserManagementApplication'`.
    - The application will start on `http://localhost:8081`.

5. **Access the H2 Console** (Optional):
    - Open `http://localhost:8081/h2-console`.
    - Use the following settings:
        - **JDBC URL**: `jdbc:h2:mem:usermanagement`
        - **Username**: `sa`
        - **Password**: (leave blank)
    - Click `Connect` to view the database.

## API Endpoints

The application exposes the following REST API endpoints:

| Method | Endpoint                         | Description                     | Request Body Example                     | Response Example                     |
|--------|----------------------------------|---------------------------------|------------------------------------------|--------------------------------------|
| POST   | `/users`                        | Create a new user               | `{"name":"John Doe","email":"john@example.com"}` | `"550e8400-e29b-41d4-a716-446655440001"` |
| POST   | `/roles`                        | Create a new role               | `{"roleName":"ADMIN"}`                   | `"550e8400-e29b-41d4-a716-446655440000"` |
| POST   | `/users/{userId}/assign-role/{roleId}` | Assign a role to a user         | (None)                                   | `"Role assigned successfully"`      |
| GET    | `/users/{id}`                   | Retrieve user details           | (None)                                   | `{"id":"550e8400-e29b-41d4-a716-446655440001","name":"John Doe","email":"john@example.com","roles":[{"id":"550e8400-e29b-41d4-a716-446655440000","roleName":"ADMIN"}]}` |

## Example API Usage with Postman

1. **Download and Install Postman**:
    - Download Postman from https://www.postman.com/downloads/ and install it.

2. **Create a Role**:
    - Open Postman.
    - Set the HTTP method to `POST`.
    - Enter the URL: `http://localhost:8081/roles`.
    - Go to the `Body` tab, select `raw`, and choose `JSON` from the dropdown.
    - Enter the following JSON:
      ```json
      {"roleName":"ADMIN"}
      ```
    - Click `Send`.
    - Copy the returned `roleId` (e.g., `"550e8400-e29b-41d4-a716-446655440000"`).

3. **Create a User**:
    - Set the HTTP method to `POST`.
    - Enter the URL: `http://localhost:8081/users`.
    - In the `Body` tab, select `raw` and `JSON`.
    - Enter the following JSON:
      ```json
      {"name":"John Doe","email":"john@example.com"}
      ```
    - Click `Send`.
    - Copy the returned `userId` (e.g., `"550e8400-e29b-41d4-a716-446655440001"`).

4. **Assign Role to User**:
    - Set the HTTP method to `POST`.
    - Enter the URL with the `userId` and `roleId` from the previous steps: `http://localhost:8081/users/<userId>/assign-role/<roleId>` (e.g., `http://localhost:8081/users/550e8400-e29b-41d4-a716-446655440001/assign-role/550e8400-e29b-41d4-a716-446655440000`).
    - No request body is needed.
    - Click `Send`.
    - Verify the response: `"Role assigned successfully"`.

5. **Get User Details**:
    - Set the HTTP method to `GET`.
    - Enter the URL with the `userId`: `http://localhost:8081/users/<userId>` (e.g., `http://localhost:8081/users/550e8400-e29b-41d4-a716-446655440001`).
    - Click `Send`.
    - Verify the response, e.g.:
      ```json
      {
          "id": "550e8400-e29b-41d4-a716-446655440001",
          "name": "John Doe",
          "email": "john@example.com",
          "roles": [
              {
                  "id": "550e8400-e29b-41d4-a716-446655440000",
                  "roleName": "ADMIN"
              }
          ]
      }
      ```

## Running Tests

- **Unit Tests**:
    - Right-click the `application` package under `src/test/java/org.example.usermanagement` and select `Run Tests in 'application'`.
    - Tests include `UserServiceTest` and `RoleServiceTest`.

## Additional Documentation

For more reference material, see the [HELP.md](HELP.md) file, which includes links to Spring Boot and Maven documentation.

