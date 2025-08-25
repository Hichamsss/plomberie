package be.plomberie.demo.service;

import java.util.List;
import org.springframework.stereotype.Service;
import be.plomberie.demo.model.Avis;
import be.plomberie.demo.repository.AvisRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AvisService {

    private final AvisRepository avisRepository;

    public List<Avis> getAllAvis() {
        return avisRepository.findAll();
    }

    public Avis getAvisById(Long idAvis) {
        return avisRepository.findById(idAvis).orElse(null);
    }

    public Avis saveAvis(Avis avis) {
        return avisRepository.save(avis);
    }

    public void deleteAvis(Long idAvis) {
        avisRepository.deleteById(idAvis);
    }
}
