package be.plomberie.demo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import be.plomberie.demo.model.Devis;

@Repository
public interface DevisRepository extends JpaRepository<Devis, Integer> {
    List<Devis> findByStatus(String status);
}