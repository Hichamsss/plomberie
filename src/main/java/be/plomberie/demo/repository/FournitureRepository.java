package be.plomberie.demo.repository;

import be.plomberie.demo.model.Fourniture;
import be.plomberie.demo.model.Fourniture.Categorie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface FournitureRepository extends JpaRepository<Fourniture, Long> {

    Page<Fourniture> findByActifTrue(Pageable pageable);

    Page<Fourniture> findByCategorieAndActifTrue(Categorie categorie, Pageable pageable);

    Page<Fourniture> findByNomContainingIgnoreCaseAndActifTrue(String q, Pageable pageable);

    Page<Fourniture> findByPrixHTBetweenAndActifTrue(BigDecimal min, BigDecimal max, Pageable pageable);

    boolean existsByReference(String reference);
}
