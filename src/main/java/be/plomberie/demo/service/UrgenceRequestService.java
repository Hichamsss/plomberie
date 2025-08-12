package be.plomberie.demo.service;

import be.plomberie.demo.model.UrgenceRequest;
import be.plomberie.demo.repository.UrgenceRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UrgenceRequestService {

    private final UrgenceRequestRepository repo;

    public UrgenceRequestService(UrgenceRequestRepository repo) {
        this.repo = repo;
    }

    public UrgenceRequest save(UrgenceRequest req) {
        return repo.save(req);
    }

    public List<UrgenceRequest> getPaidOnly() {
        return repo.findByPayeTrueOrderByIdDesc();
    }

    public void markAsTraite(Long id) {
        UrgenceRequest u = repo.findById(id).orElseThrow();
        u.setStatut(UrgenceRequest.Statut.TRAITE);
        repo.save(u);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
