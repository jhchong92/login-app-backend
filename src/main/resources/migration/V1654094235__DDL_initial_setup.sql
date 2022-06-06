DROP TABLE IF EXISTS app_user;
CREATE TABLE app_user
(
    id         VARCHAR(50) PRIMARY KEY,
    first_name  VARCHAR(255),
    last_name   VARCHAR(255),
    email      VARCHAR(255) UNIQUE NOT NULL,
    created_on TIMESTAMP           NOT NULL
);
