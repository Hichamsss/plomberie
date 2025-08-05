package be.plomberie.demo.service;

import be.plomberie.demo.model.UrgenceRequest;
import be.plomberie.demo.repository.UrgenceRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UrgenceRequestService {

    private final UrgenceRequestRepository repository;

    public UrgenceRequestService(UrgenceRequestRepository repository) {
        this.repository = repository;
    }

    public void enregistrer(UrgenceRequest urgence) {
        repository.save(urgence);
    }

    public List<UrgenceRequest> getAll() {
        return repository.findAll();
    }

    public void marquerTraite(Long id) {
        UrgenceRequest urgence = repository.findById(id).orElseThrow();
        urgence.setStatut(UrgenceRequest.Statut.TRAITE);
        repository.save(urgence);
    }
}
