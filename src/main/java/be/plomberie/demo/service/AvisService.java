package be.plomberie.demo.service;

<<<<<<< HEAD
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.plomberie.demo.model.Avis;
import be.plomberie.demo.repository.AvisRepository;
=======
import be.plomberie.demo.model.Avis;
import be.plomberie.demo.repository.AvisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
>>>>>>> origin/main

@Service
public class AvisService {

    @Autowired
    private AvisRepository avisRepository;

    // Récupérer tous les avis
    public List<Avis> getAllAvis() {
        return avisRepository.findAll();
    }

    // Récupérer un avis par son ID
    public Avis getAvisById(int idAvis) {
        return avisRepository.findById(idAvis).orElse(null);
    }

    // Ajouter un nouvel avis
    public Avis saveAvis(Avis avis) {
        return avisRepository.save(avis);
    }

    // Supprimer un avis
    public void deleteAvis(int idAvis) {
        avisRepository.deleteById(idAvis);
    }
}
