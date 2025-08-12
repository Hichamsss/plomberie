CREATE TABLE urgences (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  prenom VARCHAR(255) NOT NULL,
  telephone VARCHAR(50) NOT NULL,
  disponibilite VARCHAR(50) NOT NULL,   -- format texte ex: "2025-08-12 14:00"
  description TEXT,
  statut VARCHAR(20) NOT NULL,
  stripe_session_id VARCHAR(255),
  paye BOOLEAN DEFAULT FALSE,           -- suivi paiement
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  contact_email VARCHAR(255),
  client_id BIGINT NULL,
  INDEX idx_urgences_client (client_id),
  INDEX idx_urgences_contact_email (contact_email),
  INDEX idx_urgences_telephone (telephone),
  CONSTRAINT fk_urgences_client
    FOREIGN KEY (client_id) REFERENCES clients(id)
    ON DELETE SET NULL ON UPDATE CASCADE
);
