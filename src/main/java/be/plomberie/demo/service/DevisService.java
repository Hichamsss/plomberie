package be.plomberie.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.plomberie.demo.model.Devis;
import be.plomberie.demo.repository.DevisRepository;

@Service
public class DevisService {

    @Autowired
    private DevisRepository devisRepository;

    // Récupérer tous les devis
    public List<Devis> getAllDevis() {
        return devisRepository.findAll();
    }

    // Récupérer un devis par son ID
    public Optional<Devis> getDevisById(Integer id) {
        return devisRepository.findById(id);
    }

    // Ajouter ou mettre à jour un devis
    public Devis saveDevis(Devis devis) {
        return devisRepository.save(devis);
    }

    // Supprimer un devis
    public void deleteDevis(Integer id) {
        devisRepository.deleteById(id);
    }

    // Trouver les devis par statut (utilise votre méthode existante dans le repository)
    public List<Devis> getDevisByStatus(String status) {
        return devisRepository.findByStatus(status);
    }
}