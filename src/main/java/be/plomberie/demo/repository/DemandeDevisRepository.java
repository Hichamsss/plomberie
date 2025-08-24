package be.plomberie.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import be.plomberie.demo.model.Client;
import be.plomberie.demo.model.DemandeDevis;

public interface DemandeDevisRepository extends JpaRepository<DemandeDevis, Long> {
    List<DemandeDevis> findAllByClientOrEmailOrderByIdDesc(Client client, String email);
    
    @Modifying
    @Query("delete from DemandeDevis d where d.client = :client")
    void deleteByClient(Client client);

}
