package be.plomberie.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import be.plomberie.demo.model.Realisation;

@Repository
public interface RealisationRepository extends JpaRepository<Realisation, Integer> {
	List<Realisation> findByTitre (String titre);
	
}