package be.plomberie.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import be.plomberie.demo.model.Devis;
import be.plomberie.demo.service.DevisService;

@Controller
@RequestMapping("/devis")
public class DevisController {

    private final DevisService devisService;

    public DevisController(DevisService devisService) {
        this.devisService = devisService;
    }

    // Vue HTML
    @GetMapping
    public String listDevis(Model model) {
        model.addAttribute("title", "Liste des Devis");
        model.addAttribute("devisList", devisService.getAllDevis());
        return "devis/list";
    }

    // API JSON
    @ResponseBody
    @GetMapping("/api")
    public List<Devis> getDevisApi() {
        return devisService.getAllDevis();
    }
}
