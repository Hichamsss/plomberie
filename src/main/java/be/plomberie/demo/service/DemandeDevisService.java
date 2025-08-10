package be.plomberie.demo.service;

import be.plomberie.demo.model.DemandeDevis;
import be.plomberie.demo.repository.DemandeDevisRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemandeDevisService {

    private final DemandeDevisRepository repository;

    public DemandeDevisService(DemandeDevisRepository repository) {
        this.repository = repository;
    }

    public List<DemandeDevis> getAll() {
        return repository.findAll();
    }

    public DemandeDevis creer(DemandeDevis d) {
        return repository.save(d);
    }

    public void updateStatut(Long id, String statut) {
        DemandeDevis.StatutDemande cible;
        try {
            cible = DemandeDevis.StatutDemande.valueOf(statut);
        } catch (IllegalArgumentException e) {
            cible = DemandeDevis.StatutDemande.EN_ATTENTE;
        }
        DemandeDevis devis = repository.findById(id).orElseThrow();
        devis.setStatut(cible);
        repository.save(devis);
    }

    public void supprimer(Long id) {
        repository.deleteById(id);
    }
}
