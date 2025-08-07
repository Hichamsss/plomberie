package be.plomberie.demo.controller;

import be.plomberie.demo.model.Devis;
import be.plomberie.demo.model.Devis.Statut;
import be.plomberie.demo.service.DevisService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/devis")
public class DevisController {

    private final DevisService devisService;

    public DevisController(DevisService devisService) {
        this.devisService = devisService;
    }

    // 🔹 Vue utilisateur (liste des devis)
    @GetMapping
    public String listDevis(Model model) {
        model.addAttribute("title", "Liste des Devis");
        model.addAttribute("devisList", devisService.getAllDevis());
        return "devis/list";
    }

    // 🔹 Vue formulaire de demande de devis (utilisateur)
    @GetMapping("/demande")
    public String showForm(Model model) {
        model.addAttribute("devis", new Devis());
        return "demande/demande";
    }

    @PostMapping("/demande")
    public String handleForm(@ModelAttribute("devis") Devis devis, Model model) {
        devis.setStatut(Statut.EN_ATTENTE); // Statut initial
        devisService.saveDevis(devis);
        model.addAttribute("message", "Merci pour votre demande, nous vous répondrons rapidement !");
        return "demande/demande";
    }

    // 🔹 Vue admin
    @GetMapping("/admin")
    public String voirDemandesDevisAdmin(Model model) {
        model.addAttribute("devisList", devisService.getAllDevis());
        return "admin/demandes-devis";
    }

    // 🔹 Modifier le statut depuis l'admin
    @PostMapping("/admin/{id}/modifier-statut")
    public String modifierStatut(@PathVariable Long id, @RequestParam("statut") Statut statut) {
        Devis devis = devisService.getDevisById(id).orElse(null);
        if (devis != null) {
            devis.setStatut(statut);
            devisService.saveDevis(devis);
        }
        return "redirect:/devis/admin";
    }

    // 🔹 API JSON
    @ResponseBody
    @GetMapping("/api")
    public List<Devis> getDevisApi() {
        return devisService.getAllDevis();
    }
}
