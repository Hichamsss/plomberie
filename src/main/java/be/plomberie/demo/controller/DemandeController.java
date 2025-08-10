package be.plomberie.demo.controller;

import be.plomberie.demo.model.DemandeDevis;
import be.plomberie.demo.service.DemandeDevisService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DemandeController {

    private final DemandeDevisService demandeService;

    public DemandeController(DemandeDevisService demandeService) {
        this.demandeService = demandeService;
    }

    @GetMapping("/demande")
    public String afficherFormulaire(Model model) {
        model.addAttribute("demande", new DemandeDevis());
        return "demande/demande";
    }

    @PostMapping("/demande")
    public String envoyer(@Valid @ModelAttribute("demande") DemandeDevis demande,
                          BindingResult binding, Model model) {
        if (binding.hasErrors()) return "demande/demande";
        if (demande.getStatut() == null) {
            demande.setStatut(DemandeDevis.StatutDemande.EN_ATTENTE);
        }
        demandeService.creer(demande);
        model.addAttribute("message", "Merci pour votre demande, nous vous répondrons rapidement !");
        model.addAttribute("demande", new DemandeDevis()); // reset formulaire
        return "demande/demande";
    }
}
