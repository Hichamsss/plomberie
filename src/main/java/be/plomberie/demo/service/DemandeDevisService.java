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

    public void enregistrer(DemandeDevis devis) {
        repository.save(devis);
    }

    public List<DemandeDevis> getAll() {
        return repository.findAll();
    }

    public void marquerTraite(Long id) {
        DemandeDevis devis = repository.findById(id).orElseThrow();
        devis.setStatut(DemandeDevis.Statut.TRAITE);
        repository.save(devis);
    }
    
    public DemandeDevis getById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public void save(DemandeDevis devis) {
        repository.save(devis);
    }

}
