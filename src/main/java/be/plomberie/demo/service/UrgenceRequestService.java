package be.plomberie.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import be.plomberie.demo.model.UrgenceRequest;
import be.plomberie.demo.model.UrgenceRequest.Statut;
import be.plomberie.demo.repository.UrgenceRequestRepository;

@Service
public class UrgenceRequestService {

    private final UrgenceRequestRepository repository;

    public UrgenceRequestService(UrgenceRequestRepository repository) {
        this.repository = repository;
    }

    public UrgenceRequest enregistrer(UrgenceRequest urgence) {
        return repository.save(urgence);
    }

    public List<UrgenceRequest> getAll() {
        return repository.findAll();
    }

    public void marquerTraite(Long id) {
        UrgenceRequest urgence = repository.findById(id).orElseThrow();
        urgence.setStatut(Statut.TRAITE);
        repository.save(urgence);
    }

    public void supprimer(Long id) {
        repository.deleteById(id);
    }

    public void enregistrerStripeSession(Long idUrgence, String sessionId) {
        UrgenceRequest u = repository.findById(idUrgence).orElseThrow();
        u.setStripeSessionId(sessionId);
        repository.save(u);
    }

    public void marquerPayeParSession(String sessionId) {
        repository.findByStripeSessionId(sessionId).ifPresent(u -> {
            // Ici, ta logique métier. Si "payé" ne change rien au badge
            // (tu n’affiches que EN_ATTENTE/TRAITE), on reste sur EN_ATTENTE.
            u.setStatut(Statut.EN_ATTENTE);
            repository.save(u);
        });
    }
}
