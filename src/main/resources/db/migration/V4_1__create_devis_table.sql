CREATE TABLE devis (
    id_devis INT AUTO_INCREMENT PRIMARY KEY,
    description TEXT,
    date_creation DATETIME NOT NULL,
    status VARCHAR(50) NOT NULL
);