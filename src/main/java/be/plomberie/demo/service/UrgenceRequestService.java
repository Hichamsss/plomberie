package be.plomberie.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.plomberie.demo.model.UrgenceRequest;
import be.plomberie.demo.model.UrgenceRequest.Statut;
import be.plomberie.demo.repository.UrgenceRequestRepository;

@Service
public class UrgenceRequestService {

    private final UrgenceRequestRepository repository;

    public UrgenceRequestService(UrgenceRequestRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public UrgenceRequest enregistrer(UrgenceRequest urgence) {
        return repository.save(urgence);
    }

    @Transactional(readOnly = true)
    public List<UrgenceRequest> getAll() {
        return repository.findAll();
    }

    @Transactional
    public void marquerTraite(Long id) {
        UrgenceRequest urgence = repository.findById(id).orElseThrow();
        urgence.setStatut(Statut.TRAITE);
        // pas besoin d'appeler save() si l'entité est managée, mais on peut laisser:
        repository.save(urgence);
    }

    @Transactional
    public void supprimer(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public void enregistrerStripeSession(Long idUrgence, String sessionId) {
        UrgenceRequest u = repository.findById(idUrgence).orElseThrow();
        u.setStripeSessionId(sessionId);
        repository.save(u);
    }

    @Transactional
    public void marquerPayeParSession(String sessionId) {
        repository.findByStripeSessionId(sessionId).ifPresent(u -> {
            // ⚠️ Ne PAS réinitialiser le statut ici.
            // On se contente d'acter le paiement. Si tu veux,
            // passe en EN_COURS SEULEMENT si le statut est null.
            if (u.getStatut() == null) {
                u.setStatut(Statut.EN_ATTENTE); // ou EN_COURS selon ta logique
            }
            repository.save(u);
        });
    }
}
