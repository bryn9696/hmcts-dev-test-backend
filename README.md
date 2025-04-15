# Task Manager API

A simple Spring Boot backend for managing caseworker tasks.

## Features

- Create, read, update, and delete tasks
- Store tasks in database
- Swagger UI for API documentation
- Input validation and error handling
- Unit tested service layer

## Endpoints

| Method | Path         | Description       |
|--------|--------------|-------------------|
| POST   | /tasks       | Create a new task |
| GET    | /tasks/{id}  | Get a task by ID  |
| PUT    | /tasks/{id}  | Update task       |
| DELETE | /tasks/{id}  | Delete a task     |

## How to Run

```bash
git clone https://github.com/bryn9696/hmcts-dev-test-backend.git
cd hmcts-task-manager-backend
./gradlew build
./gradlew bootRun

http://localhost:4000

to run all tests
./gradlew test


### For database use
Ensure MySQL is installed and running. Then from your terminal or client run contents of hmcts-schema.sql:
CREATE DATABASE taskmanager;

USE taskmanager;

CREATE TABLE task (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  description VARCHAR(255),
  status VARCHAR(255) NOT NULL,
  due_date DATETIME NOT NULL
);

Update application.yaml to point to your MySQL instance.

# HMCTS Dev Test Backend
This will be the backend for the brand new HMCTS case management system. As a potential candidate we are leaving
this in your hands. Please refer to the brief for the complete list of tasks! Complete as much as you can and be
as creative as you want.

You should be able to run `./gradlew build` to start with to ensure it builds successfully. Then from that you
can run the service in IntelliJ (or your IDE of choice) or however you normally would.

There is an example endpoint provided to retrieve an example of a case. You are free to add/remove fields as you
wish.

