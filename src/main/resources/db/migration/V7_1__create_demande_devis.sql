CREATE TABLE demande_devis (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(255),
    email VARCHAR(255),
    telephone VARCHAR(50),
    message VARCHAR(1000),
    statut VARCHAR(20),
    date_soumission DATETIME
);
