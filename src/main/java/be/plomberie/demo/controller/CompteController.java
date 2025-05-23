package be.plomberie.demo.controller;

import java.util.List;
import be.plomberie.demo.model.Compte;
import be.plomberie.demo.service.CompteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/comptes")
public class CompteController {

    private final CompteService compteService;

    public CompteController(CompteService compteService) {
        this.compteService = compteService;
    }

    // Vue HTML
    @GetMapping
    public String listComptes(Model model) {
        model.addAttribute("comptes", compteService.getAllComptes());
        model.addAttribute("compte", new Compte());
        return "compte/index";
    }

    @PostMapping("/add")
    public String addCompte(@ModelAttribute("compte") Compte compte) {
        compteService.createCompte(compte);
        return "redirect:/comptes";
    }

    @GetMapping("/delete/{id}")
    public String deleteCompte(@PathVariable int id) {
        compteService.deleteCompte(id);
        return "redirect:/comptes";
    }

    // API JSON
    @ResponseBody
    @GetMapping("/api")
    public List<Compte> getComptesApi() {
        return compteService.getAllComptes();
    }

    @ResponseBody
    @PostMapping("/api")
    public Compte createCompteApi(@RequestBody Compte compte) {
        return compteService.createCompte(compte);
    }

    @ResponseBody
    @DeleteMapping("/api/{id}")
    public void deleteCompteApi(@PathVariable int id) {
        compteService.deleteCompte(id);
    }
}
