
---
CREATE DATABASE taskmanager;

USE taskmanager;

CREATE TABLE task (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  description VARCHAR(255),
  status VARCHAR(255) NOT NULL,
  due_date DATETIME NOT NULL
);


