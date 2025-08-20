package be.plomberie.demo.service.impl;

import be.plomberie.demo.model.Retour;
import be.plomberie.demo.repository.RetourRepository;
import be.plomberie.demo.service.RetourService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RetourServiceImpl implements RetourService {

    private final RetourRepository repo;

    public RetourServiceImpl(RetourRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Retour> getAllRetours() {
        return repo.findAll();
    }

    @Override
    public Retour saveRetour(Retour retour) {
        return repo.save(retour);
    }
}
