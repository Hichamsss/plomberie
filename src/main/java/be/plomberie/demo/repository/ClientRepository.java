package be.plomberie.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import be.plomberie.demo.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByEmail(String email);
    
    
}
