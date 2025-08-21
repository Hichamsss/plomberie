package be.plomberie.demo.repository;

import be.plomberie.demo.model.ConseilEntretien;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConseilEntretienRepository extends JpaRepository<ConseilEntretien, Long> {
    List<ConseilEntretien> findTop50ByActifTrueOrderByIdDesc();
    List<ConseilEntretien> findTop50ByCategorieIgnoreCaseAndActifTrueOrderByIdDesc(String categorie);
    List<ConseilEntretien> findTop50ByTitreContainingIgnoreCaseAndActifTrueOrderByIdDesc(String q);
}
