package be.plomberie.demo.controller;

import be.plomberie.demo.api.dto.ConseilDTO;
import be.plomberie.demo.mapper.ConseilMapper;
import be.plomberie.demo.service.ConseilService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/public/conseils")
public class ConseilController {

    private final ConseilService service;

    public ConseilController(ConseilService service) {
        this.service = service;
    }

    @GetMapping
    public List<ConseilDTO> list(@RequestParam(required = false) String q,
                                 @RequestParam(required = false) String categorie) {
        return service.list(q, categorie).stream().map(ConseilMapper::toDTO).toList();
    }

    @GetMapping("/{id}")
    public ConseilDTO get(@PathVariable Long id) {
        return ConseilMapper.toDTO(service.get(id));
    }
}
