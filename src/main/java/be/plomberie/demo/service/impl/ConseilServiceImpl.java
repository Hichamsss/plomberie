package be.plomberie.demo.service.impl;

import be.plomberie.demo.model.ConseilEntretien;
import be.plomberie.demo.repository.ConseilEntretienRepository;
import be.plomberie.demo.service.ConseilService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ConseilServiceImpl implements ConseilService {

    private final ConseilEntretienRepository repo;

    public ConseilServiceImpl(ConseilEntretienRepository repo) {
        this.repo = repo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConseilEntretien> list(String q, String categorie) {
        if (q != null && !q.isBlank()) {
            return repo.findTop50ByTitreContainingIgnoreCaseAndActifTrueOrderByIdDesc(q.trim());
        }
        if (categorie != null && !categorie.isBlank()) {
            return repo.findTop50ByCategorieIgnoreCaseAndActifTrueOrderByIdDesc(categorie.trim());
        }
        return repo.findTop50ByActifTrueOrderByIdDesc();
    }

    @Override
    @Transactional(readOnly = true)
    public ConseilEntretien get(Long id) {
        return repo.findById(id).orElseThrow();
    }
}
