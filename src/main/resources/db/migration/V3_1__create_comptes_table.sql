CREATE TABLE comptes (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nom VARCHAR(255) NOT NULL,
    email VARCHAR(191) NOT NULL,
    mot_de_passe VARCHAR(255) NOT NULL,
    client_id BIGINT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_comptes_email (email),
    UNIQUE KEY uk_comptes_client (client_id),
    CONSTRAINT fk_comptes_client FOREIGN KEY (client_id)
        REFERENCES clients(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
) ENGINE=InnoDB;
