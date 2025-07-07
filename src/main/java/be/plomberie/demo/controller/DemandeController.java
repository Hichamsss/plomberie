package be.plomberie.demo.controller;

import be.plomberie.demo.model.DemandeDevis;
import be.plomberie.demo.service.DemandeDevisService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DemandeController {

    private final DemandeDevisService service;

    public DemandeController(DemandeDevisService service) {
        this.service = service;
    }

    @GetMapping("/demande")
    public String showForm(Model model) {
        model.addAttribute("devis", new DemandeDevis());
        return "demande/demande";
    }

    @PostMapping("/demande")
    public String handleForm(@ModelAttribute("devis") DemandeDevis devis, Model model) {
        service.enregistrer(devis);
        model.addAttribute("message", "Merci pour votre demande, " + devis.getNom() + ". Nous vous répondrons rapidement !");
        return "demande/demande";
    }
}
