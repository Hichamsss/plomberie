package be.plomberie.demo.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AvisEligibilityService {

    @PersistenceContext
    private EntityManager em;

    public boolean canLeaveReview(String email) {
        if (email == null || email.isBlank()) return false;

        // Devis TRAITÉS par email
        Long devisTraites = em.createQuery(
                "select count(d) " +
                "from DemandeDevis d " +
                "where lower(d.email) = lower(:mail) " +
                "and d.statut = 'TRAITE'",
                Long.class)
            .setParameter("mail", email)
            .getSingleResult();

        // Urgences TRAITÉES & PAYÉES par contactEmail OU email du client lié
        Long urgencesTraiteesPayees = em.createQuery(
                "select count(u) " +
                "from UrgenceRequest u " +
                "where (" +
                "       (u.contactEmail is not null and lower(u.contactEmail) = lower(:mail)) " +
                "    or (u.client is not null and lower(u.client.email) = lower(:mail))" +
                ") " +
                "and u.statut = 'TRAITE' " +
                "and u.stripeSessionId is not null and u.stripeSessionId <> ''",
                Long.class)
            .setParameter("mail", email)
            .getSingleResult();

        return (devisTraites != null && devisTraites > 0)
            || (urgencesTraiteesPayees != null && urgencesTraiteesPayees > 0);
    }
}
