package be.plomberie.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import be.plomberie.demo.model.Client;
import be.plomberie.demo.model.UrgenceRequest;

public interface UrgenceRequestRepository extends JpaRepository<UrgenceRequest, Long> {

    Optional<UrgenceRequest> findByStripeSessionId(String stripeSessionId);

    // 🔹 Recherche par client, téléphone ou email de contact
    List<UrgenceRequest> findAllByClientOrTelephoneOrContactEmailOrderByCreatedAtDesc(
            Client client, String telephone, String contactEmail);
}
