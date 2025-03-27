package be.plomberie.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import be.plomberie.demo.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findByLastname(String lastname); 
}
