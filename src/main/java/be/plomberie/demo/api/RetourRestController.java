package be.plomberie.demo.api;

import be.plomberie.demo.api.dto.RetourDto;
import be.plomberie.demo.model.Retour;
import be.plomberie.demo.service.RetourService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/public/retours", produces = "application/json")
@CrossOrigin
public class RetourRestController {

    private final RetourService retourService;

    public RetourRestController(RetourService retourService) {
        this.retourService = retourService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<RetourDto>>> list() {
        List<RetourDto> payload = retourService.getAllRetours()
                .stream()
                .map(this::toDto)
                .toList();
        return ResponseEntity.ok(ApiResponse.ok(payload));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<RetourDto>> create(@RequestBody RetourDto dto) {
        Retour saved = retourService.saveRetour(
            new Retour(null, dto.getContenu(), dto.getNote())
        );
        return ResponseEntity.ok(ApiResponse.ok(toDto(saved), "Retour enregistré"));
    }

    private RetourDto toDto(Retour r) {
        return new RetourDto(r.getIdRetour(), r.getContenu(), r.getNote());
    }
}
