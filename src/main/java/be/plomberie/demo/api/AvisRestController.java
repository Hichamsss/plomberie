package be.plomberie.demo.api;

import be.plomberie.demo.api.dto.AvisDto;
import be.plomberie.demo.model.Avis;
import be.plomberie.demo.service.AvisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/public/avis", produces = "application/json")
@CrossOrigin
public class AvisRestController {

    private final AvisService avisService;

    public AvisRestController(AvisService avisService) {
        this.avisService = avisService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AvisDto>>> list() {
        List<AvisDto> payload = avisService.getAllAvis()
                .stream()
                .map(this::toDto)
                .toList();
        return ResponseEntity.ok(ApiResponse.ok(payload));
    }

    private AvisDto toDto(Avis a) {
        return new AvisDto(a.getIdAvis(), a.getContenu(), a.getNote());
    }
}
