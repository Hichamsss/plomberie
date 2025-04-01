CREATE TABLE realisations (
    id_realisation INT AUTO_INCREMENT PRIMARY KEY,
    titre VARCHAR(100) NOT NULL,
    description TEXT,
    date_publication DATETIME NOT NULL
);