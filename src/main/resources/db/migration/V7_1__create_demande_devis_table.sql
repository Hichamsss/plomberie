CREATE TABLE demande_devis (
  id BIGINT NOT NULL AUTO_INCREMENT,
  client_id BIGINT NULL,
  nom VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  telephone VARCHAR(50) NOT NULL,
  message TEXT NOT NULL,
  statut ENUM('EN_ATTENTE','EN_COURS','TRAITE') NOT NULL DEFAULT 'EN_ATTENTE',
  PRIMARY KEY (id),
  INDEX idx_demande_devis_client (client_id),
  INDEX idx_demande_devis_statut (statut),
  CONSTRAINT fk_demande_devis_client FOREIGN KEY (client_id)
      REFERENCES clients(id)
      ON DELETE SET NULL
      ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;