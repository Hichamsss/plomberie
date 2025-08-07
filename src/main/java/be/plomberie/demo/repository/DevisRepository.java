package be.plomberie.demo.repository;

import be.plomberie.demo.model.Devis;
import be.plomberie.demo.model.Devis.Statut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DevisRepository extends JpaRepository<Devis, Long> {
    List<Devis> findByStatut(Statut statut);
}
