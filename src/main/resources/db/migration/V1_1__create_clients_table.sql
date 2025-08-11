CREATE TABLE clients (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  firstname VARCHAR(255),
  lastname  VARCHAR(255),
  email     VARCHAR(255) UNIQUE,
  telephone VARCHAR(50)
);