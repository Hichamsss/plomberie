package be.plomberie.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import be.plomberie.demo.service.DevisService;

@Controller
@RequestMapping("/devis")
public class DevisController {

    private final DevisService devisService;

    public DevisController(DevisService devisService) {
        this.devisService = devisService;
    }

    @GetMapping
    public String listDevis(Model model) {
        model.addAttribute("title", "Liste des Devis");
        model.addAttribute("devisList", devisService.getAllDevis());
        return "devis/list";
    }
}