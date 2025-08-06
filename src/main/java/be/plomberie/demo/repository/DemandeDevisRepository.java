package be.plomberie.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import be.plomberie.demo.model.DemandeDevis;

public interface DemandeDevisRepository extends JpaRepository<DemandeDevis, Long> {
}
