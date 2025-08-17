package be.plomberie.demo.controller;

import be.plomberie.demo.model.DemandeDevis;
import be.plomberie.demo.service.DemandeDevisService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping({"/admin/demandes-devis", "/admin/devis"})
public class AdminDevisController {

    private final DemandeDevisService devisService;

    public AdminDevisController(DemandeDevisService devisService) {
        this.devisService = devisService;
    }

    @GetMapping
    public String afficherDevis(Model model) {
        List<DemandeDevis> liste = devisService.getAll();
        model.addAttribute("devis", liste);
        model.addAttribute("nouveau", new DemandeDevis());
        return "admin/devis/liste";
    }

    @PostMapping("/ajouter")
    public String ajouter(@ModelAttribute("nouveau") DemandeDevis nouveau) {
        if (nouveau.getStatut() == null) {
            nouveau.setStatut(DemandeDevis.StatutDemande.EN_ATTENTE);
        }
        devisService.creer(nouveau);
        return "redirect:/admin/demandes-devis";
    }

    @PostMapping("/{id}/modifier-statut")
    public String modifierStatut(@PathVariable Long id, @RequestParam("statut") String statut) {
        devisService.updateStatut(id, statut);
        return "redirect:/admin/demandes-devis";
    }

    @PostMapping("/{id}/supprimer")
    public String supprimerDevis(@PathVariable Long id) {
        devisService.supprimer(id);
        return "redirect:/admin/demandes-devis";
    }
}
