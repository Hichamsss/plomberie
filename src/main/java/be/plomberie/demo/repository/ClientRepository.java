package be.plomberie.demo.repository;

<<<<<<< HEAD
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import be.plomberie.demo.model.Client;
=======
import be.plomberie.demo.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
>>>>>>> origin/main

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findByLastname(String lastname); 
}
