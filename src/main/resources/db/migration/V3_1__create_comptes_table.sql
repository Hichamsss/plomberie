CREATE TABLE comptes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(255),
    email VARCHAR(255),
    mot_de_passe VARCHAR(255),
    client_id INT UNIQUE,
    FOREIGN KEY (client_id) REFERENCES clients(id)
);