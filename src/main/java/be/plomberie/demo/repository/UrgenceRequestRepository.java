package be.plomberie.demo.repository;

import be.plomberie.demo.model.Client;
import be.plomberie.demo.model.UrgenceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UrgenceRequestRepository extends JpaRepository<UrgenceRequest, Long> {

    // Pour la page admin (uniquement payées)
    List<UrgenceRequest> findByPayeTrueOrderByIdDesc();

    // Pour "Mon compte" : payées ET liées à l'utilisateur (client OU tel OU email)
    @Query("""
           SELECT u FROM UrgenceRequest u
           WHERE u.paye = true
             AND ( ( :client IS NOT NULL AND u.client = :client )
                   OR ( :telephone IS NOT NULL AND u.telephone = :telephone )
                   OR ( :email IS NOT NULL AND u.contactEmail = :email ) )
           ORDER BY u.createdAt DESC
           """)
    List<UrgenceRequest> findPaidForCurrentUser(
            @Param("client") Client client,
            @Param("telephone") String telephone,
            @Param("email") String email
    );
}
