package be.plomberie.demo.repository;

import be.plomberie.demo.model.Client;
import be.plomberie.demo.model.Compte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompteRepository extends JpaRepository<Compte, Long> {
    boolean existsByEmail(String email);
    Optional<Compte> findByEmail(String email);
    void deleteByClient(Client client);
}
