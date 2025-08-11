package be.plomberie.demo.service;

import be.plomberie.demo.model.Compte;
import be.plomberie.demo.repository.CompteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompteService {

    private final CompteRepository repository;

    public CompteService(CompteRepository repository) {
        this.repository = repository;
    }

    public List<Compte> getAllComptes() {
        return repository.findAll();
    }

    public Compte createCompte(Compte compte) {
        return repository.save(compte);
    }

    public void deleteCompte(Long id) {
        repository.deleteById(id);
    }
}
