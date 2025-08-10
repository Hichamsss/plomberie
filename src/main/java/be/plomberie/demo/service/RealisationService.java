package be.plomberie.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.plomberie.demo.model.Realisation;
import be.plomberie.demo.repository.RealisationRepository;

@Service
public class RealisationService {

	@Autowired
    private RealisationRepository realisationRepository;

    public RealisationService(RealisationRepository realisationRepository) {
        this.realisationRepository = realisationRepository;
    }

    public List<Realisation> getAllRealisations() {
        return realisationRepository.findAll();
    }

    public Realisation saveRealisation(Realisation realisation) {
        return realisationRepository.save(realisation);
    }

    public void deleteRealisation(Integer id) {
        realisationRepository.deleteById(id);
    }
}