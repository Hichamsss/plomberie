package be.plomberie.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.plomberie.demo.model.Devis;
import be.plomberie.demo.model.Devis.Statut;
import be.plomberie.demo.repository.DevisRepository;

@Service
public class DevisService {

    @Autowired
    private DevisRepository devisRepository;

    public List<Devis> getAllDevis() {
        return devisRepository.findAll();
    }

    public Optional<Devis> getDevisById(Long id) {
        return devisRepository.findById(id);
    }

    public Devis saveDevis(Devis devis) {
        return devisRepository.save(devis);
    }

    public void deleteDevis(Long id) {
        devisRepository.deleteById(id);
    }

    public List<Devis> getDevisByStatut(Statut statut) {
        return devisRepository.findByStatut(statut);
    }
}
