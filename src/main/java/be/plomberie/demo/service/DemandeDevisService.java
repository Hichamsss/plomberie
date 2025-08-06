package be.plomberie.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import be.plomberie.demo.model.DemandeDevis;
import be.plomberie.demo.repository.DemandeDevisRepository;

@Service
public class DemandeDevisService {

    private final DemandeDevisRepository repository;

    public DemandeDevisService(DemandeDevisRepository repository) {
        this.repository = repository;
    }

    public void enregistrer(DemandeDevis devis) {
        // Initialisation de valeurs par défaut
        if (devis.getStatut() == null) {
            devis.setStatut(DemandeDevis.Statut.EN_ATTENTE);
        }

        if (devis.getClient() == null) {
            devis.setClient(null); // Client non connecté, on laisse null
        }

        repository.save(devis);
    }

    public List<DemandeDevis> getAll() {
        return repository.findAll();
    }

    public DemandeDevis getById(Long id) {
        return repository.findById(id).orElse(null);
    }
}
