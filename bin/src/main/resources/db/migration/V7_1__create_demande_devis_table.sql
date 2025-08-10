CREATE TABLE demande_devis (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    telephone VARCHAR(50),
    message TEXT,
    statut VARCHAR(20) NOT NULL DEFAULT 'EN_ATTENTE',
    date_soumission DATETIME DEFAULT CURRENT_TIMESTAMP,
    client_id INT,
    CONSTRAINT fk_demande_client FOREIGN KEY (client_id)
        REFERENCES clients(id)
        ON DELETE SET NULL
        ON UPDATE CASCADE
);
