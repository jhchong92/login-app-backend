DROP TABLE IF EXISTS users;
CREATE TABLE users
(
    id         VARCHAR(50) PRIMARY KEY,
    firstName  VARCHAR(255),
    lastName   VARCHAR(255),
    email      VARCHAR(255) UNIQUE NOT NULL,
    created_on TIMESTAMP           NOT NULL
);
