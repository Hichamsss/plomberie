package be.plomberie.demo.service;

import be.plomberie.demo.model.Compte;
import be.plomberie.demo.repository.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CompteService {

    @Autowired
    private CompteRepository compteRepository;

  
    public Compte createCompte(Compte compte) {
        Optional<Compte> existingCompte = compteRepository.findByEmail(compte.getEmail());
        if (existingCompte.isPresent()) {
            throw new RuntimeException("Cet email est déjà utilisé !");
        }
        return compteRepository.save(compte);
    }

    
    public Compte updateCompte(int id, Compte compteDetails) {
        Compte existingCompte = compteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compte non trouvé avec l'ID : " + id));

        
        existingCompte.setNom(compteDetails.getNom());
        existingCompte.setEmail(compteDetails.getEmail());
        existingCompte.setMotDePasse(compteDetails.getMotDePasse());

        return compteRepository.save(existingCompte);
    }


    public List<Compte> getAllComptes() {
        return compteRepository.findAll();
    }


    public Optional<Compte> getCompteById(int id) {
        return compteRepository.findById(id);
    }


    public void deleteCompte(int id) {
        if (!compteRepository.existsById(id)) {
            throw new RuntimeException("Compte non trouvé avec l'ID : " + id);
        }
        compteRepository.deleteById(id);
    }
}
