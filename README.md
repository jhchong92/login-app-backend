# Login App Backend

A Spring Boot project with Cookie-based Authentication

## Requirements
* Postgres Server
* Java 11

## APIs
| Path                | Method | Protected | Description      |
|---------------------|--------|-----------|------------------|
| `/api/hello`        | GET    | No        | Hello World      |
| `/api/registration` | POST   | No        | Register User    |
| `/api/login`        | POST   | No        | Login User       |
| `/api/logout`       | POST   | Yes       | Logout User      |
| `/api/profile`      | GET    | Yes       | Get User profile |

## Starting the Server
```
mvn spring-boot:run
```
