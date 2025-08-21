package be.plomberie.demo.controller;

import be.plomberie.demo.api.dto.*;
import be.plomberie.demo.mapper.FournitureMapper;
import be.plomberie.demo.model.Fourniture;
import be.plomberie.demo.service.FournitureService;
import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/public/fournitures")
public class FournitureController {

    private final FournitureService service;

    public FournitureController(FournitureService service) {
        this.service = service;
    }

    @GetMapping
    public Page<FournitureDTO> list(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String categorie,
            @RequestParam(required = false) BigDecimal minPrix,
            @RequestParam(required = false) BigDecimal maxPrix,
            @RequestParam(required = false) Boolean actif,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "nom,asc") String sort
    ) {
        String[] s = sort.split(",", 2);
        Sort.Direction dir = (s.length > 1 && s[1].equalsIgnoreCase("desc")) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(dir, s[0]));
        Page<Fourniture> result = service.list(q, categorie, minPrix, maxPrix, actif, pageable);
        return result.map(FournitureMapper::toDTO);
    }

    @GetMapping("/{id}")
    public FournitureDTO get(@PathVariable Long id) {
        return FournitureMapper.toDTO(service.get(id));
    }

    @PostMapping
    public FournitureDTO create(@Valid @RequestBody CreateFournitureRequest req) {
        return FournitureMapper.toDTO(service.create(req));
    }

    @PutMapping("/{id}")
    public FournitureDTO update(@PathVariable Long id, @Valid @RequestBody UpdateFournitureRequest req) {
        return FournitureMapper.toDTO(service.update(id, req));
    }

    @PatchMapping("/{id}/stock")
    public FournitureDTO adjustStock(@PathVariable Long id, @Valid @RequestBody StockUpdateRequest req) {
        return FournitureMapper.toDTO(service.adjustStock(id, req.delta()));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
