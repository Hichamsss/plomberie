package be.plomberie.demo.service;

import be.plomberie.demo.model.Devis;
import be.plomberie.demo.service.DevisService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DemandeController {

    private final DevisService devisService;

    public DemandeController(DevisService devisService) {
        this.devisService = devisService;
    }

    // Affiche le formulaire de demande de devis
    @GetMapping("/demande")
    public String showForm(Model model) {
        model.addAttribute("devis", new Devis());
        return "demande/demande"; // Assure-toi que le fichier templates/demande/demande.html existe
    }

    // Traite le formulaire après envoi
    @PostMapping("/demande")
    public String handleForm(@ModelAttribute("devis") Devis devis, Model model) {
        devis.setStatut(Devis.Statut.EN_ATTENTE); // Optionnel si déjà par défaut
        devisService.saveDevis(devis);
        model.addAttribute("message", "Merci pour votre demande !");
        return "demande/demande";
    }
}
