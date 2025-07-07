CREATE TABLE comptes (
    id INT AUTO_INCREMENT PRIMARY KEY,
<<<<<<< HEAD
    nom VARCHAR(255),
    email VARCHAR(255),
    mot_de_passe VARCHAR(255),
    client_id INT UNIQUE,
    FOREIGN KEY (client_id) REFERENCES clients(id)
);
=======
    nom VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    mot_de_passe VARCHAR(255) NOT NULL
);
>>>>>>> 34af47935b1f19504099ebd74d22b7b6ab2b31bc
