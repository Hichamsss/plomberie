package be.plomberie.demo.controller;

import be.plomberie.demo.model.DemandeDevis;
import be.plomberie.demo.service.DemandeDevisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/devis")
public class AdminDevisController {

    private final DemandeDevisService demandeDevisService;

    @Autowired
    public AdminDevisController(DemandeDevisService demandeDevisService) {
        this.demandeDevisService = demandeDevisService;
    }

    // Affiche la liste des demandes de devis
    @GetMapping
    public String afficherDemandes(Model model) {
        model.addAttribute("demandes", demandeDevisService.getAll());
        return "admin/devis/liste";
    }

    // Met à jour le statut d'une demande de devis
    @PostMapping("/{id}/modifier-statut")
    public String modifierStatut(
            @PathVariable Long id,
            @RequestParam("statut") DemandeDevis.Statut statut
    ) {
        DemandeDevis devis = demandeDevisService.getById(id);
        devis.setStatut(statut);
        demandeDevisService.save(devis);
        return "redirect:/admin/devis";
    }
}
