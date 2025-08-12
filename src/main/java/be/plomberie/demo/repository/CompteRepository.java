package be.plomberie.demo.repository;

import be.plomberie.demo.model.Compte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompteRepository extends JpaRepository<Compte, Long> {
    Optional<Compte> findByEmailIgnoreCase(String email);
    boolean existsByEmail(String email);
}
