package be.plomberie.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import be.plomberie.demo.model.Compte;

public interface CompteRepository extends JpaRepository<Compte, Integer> {
	
	Optional<Compte> findByEmail(String email);


}
