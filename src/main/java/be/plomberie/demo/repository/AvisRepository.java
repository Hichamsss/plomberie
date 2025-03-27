package be.plomberie.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import be.plomberie.demo.model.Avis;

@Repository
public interface AvisRepository extends JpaRepository<Avis, Integer> {
    
    List<Avis> findByNote(int note);
}
