package be.plomberie.demo.repository;

import be.plomberie.demo.model.Client;
import be.plomberie.demo.model.DemandeDevis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DemandeDevisRepository extends JpaRepository<DemandeDevis, Long> {
    List<DemandeDevis> findAllByClientOrEmailOrderByIdDesc(Client client, String email);
}
