CREATE TABLE IF NOT EXISTS services (
    id_service INT AUTO_INCREMENT PRIMARY KEY,
    service VARCHAR(255) NOT NULL,
    description TEXT,
    prix DOUBLE
);
