package be.plomberie.demo.controller;

import be.plomberie.demo.model.Compte;
import be.plomberie.demo.service.CompteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/comptes")
public class CompteController {

    private final CompteService compteService;

    public CompteController(CompteService compteService) {
        this.compteService = compteService;
    }

    // 🔹 1. Afficher tous les comptes (Page d'accueil)
    @GetMapping
    public String listComptes(Model model) {
        List<Compte> comptes = compteService.getAllComptes();
        model.addAttribute("comptes", comptes);
        model.addAttribute("compte", new Compte()); // Pour le formulaire d'ajout
        return "compte/index"; // Affiche la page index.html
    }

    // 🔹 2. Ajouter un compte
    @PostMapping("/add")
    public String addCompte(@ModelAttribute("compte") Compte compte) {
        compteService.createCompte(compte);
        return "redirect:/comptes"; // Redirige vers la liste des comptes après ajout
    }

    // 🔹 3. Supprimer un compte
    @GetMapping("/delete/{id}")
    public String deleteCompte(@PathVariable int id) {
        compteService.deleteCompte(id);
        return "redirect:/comptes"; // Redirection après suppression
    }
}
