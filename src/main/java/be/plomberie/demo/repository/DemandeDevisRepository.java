package be.plomberie.demo.repository;

import be.plomberie.demo.model.DemandeDevis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemandeDevisRepository extends JpaRepository<DemandeDevis, Long> {
}
