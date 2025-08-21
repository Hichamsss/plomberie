package be.plomberie.demo.service;

import be.plomberie.demo.api.dto.*;
import be.plomberie.demo.mapper.FournitureMapper;
import be.plomberie.demo.model.Fourniture;
import be.plomberie.demo.repository.FournitureRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

@Service
public class FournitureService {

    private final FournitureRepository repo;

    public FournitureService(FournitureRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public Page<Fourniture> list(String q, String categorie, BigDecimal min, BigDecimal max, Boolean actif,
                                 Pageable pageable) {
        if (q != null && !q.isBlank()) {
            return repo.findByNomContainingIgnoreCaseAndActifTrue(q.trim(), pageable);
        }
        if (categorie != null && !categorie.isBlank()) {
            return repo.findByCategorieAndActifTrue(Fourniture.Categorie.valueOf(categorie.toUpperCase()), pageable);
        }
        if (min != null && max != null) {
            return repo.findByPrixHTBetweenAndActifTrue(min, max, pageable);
        }
        if (Boolean.TRUE.equals(actif)) {
            return repo.findByActifTrue(pageable);
        }
        return repo.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Fourniture get(Long id) {
        return repo.findById(id).orElseThrow(() -> new NoSuchElementException("Fourniture introuvable"));
    }

    @Transactional
    public Fourniture create(CreateFournitureRequest r) {
        Fourniture f = FournitureMapper.fromCreate(r);
        if (f.getReference() != null && repo.existsByReference(f.getReference())) {
            f.setReference(f.getReference() + "-" + System.currentTimeMillis());
        }
        return repo.save(f);
    }

    @Transactional
    public Fourniture update(Long id, UpdateFournitureRequest r) {
        Fourniture f = get(id);
        FournitureMapper.applyUpdate(f, r);
        return repo.save(f);
    }

    @Transactional
    public Fourniture adjustStock(Long id, int delta) {
        Fourniture f = get(id);
        int newStock = Math.max(0, f.getStock() + delta);
        f.setStock(newStock);
        return repo.save(f);
    }

    @Transactional
    public void delete(Long id) {
        Fourniture f = get(id);
        f.setDeleted(true);
        f.setActif(false);
        repo.save(f);
    }
}
