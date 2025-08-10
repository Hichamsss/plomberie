package be.plomberie.demo.controller;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;

import be.plomberie.demo.model.UrgenceForm;
import be.plomberie.demo.model.UrgenceRequest;
import be.plomberie.demo.model.UrgenceRequest.Statut;
import be.plomberie.demo.service.StripeService;
import be.plomberie.demo.service.UrgenceRequestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class UrgenceController {

    @Autowired
    private StripeService stripeService;

    private final UrgenceRequestService urgenceService;

    public UrgenceController(UrgenceRequestService urgenceService) {
        this.urgenceService = urgenceService;
    }

    // ------ Côté public (paiement) ------

    @GetMapping("/urgence")
    public String showUrgenceForm(@RequestParam(required = false) String success,
                                  @RequestParam(name = "session_id", required = false) String sessionId,
                                  Model model) {
        if ("true".equals(success) && sessionId != null) {
            // Marquer "payée" au retour de Stripe si besoin
            urgenceService.marquerPayeParSession(sessionId);
            model.addAttribute("confirmation", true);
        }
        model.addAttribute("urgenceForm", new UrgenceForm());
        return "urgence/main";
    }

    @PostMapping("/urgence/payer")
    public RedirectView handleUrgenceForm(@ModelAttribute UrgenceForm form) throws StripeException {
        // 1) Enregistrer l'urgence en base (EN_ATTENTE)
        UrgenceRequest u = new UrgenceRequest();
        u.setPrenom(form.getPrenom());
        u.setTelephone(form.getTelephone());
        u.setDisponibilite(form.getDisponibilite()); // ex: "2025-08-10 16:30"
        u.setDescription(form.getDescription());
        u.setStatut(Statut.EN_ATTENTE);
        u = urgenceService.enregistrer(u);

        // 2) Créer session Stripe avec metadata (id d'urgence)
        Session session = stripeService.creerSessionPaiementUrgenceAvecMetadata(u.getId());

        // 3) Mémoriser l'id de session Stripe pour le retour
        urgenceService.enregistrerStripeSession(u.getId(), session.getId());

        // 4) Rediriger vers Stripe
        return new RedirectView(session.getUrl());
    }

    // ------ Côté admin ------

    // Liste
    @GetMapping("/admin/urgences")
    public String voirToutesLesUrgences(Model model) {
        model.addAttribute("urgences", urgenceService.getAll());
        return "admin/urgence/urgences"; // <-- garde ta vue existante
    }

    // Marquer comme traité
    @PostMapping("/admin/urgences/{id}/traiter")
    public String marquerTraite(@PathVariable Long id) {
        urgenceService.marquerTraite(id);
        return "redirect:/admin/urgences";
    }

    // Ajout (formulaire au-dessus du tableau, avec date + heure séparées)
    @PostMapping("/admin/urgences/ajouter")
    public String ajouterUrgence(
            @RequestParam String prenom,
            @RequestParam String telephone,
            @RequestParam String dateDispo,   // format attendu: yyyy-MM-dd
            @RequestParam String heureDispo,  // format attendu: HH:mm
            @RequestParam String description,
            @RequestParam(defaultValue = "EN_ATTENTE") String statut
    ) {
        String disponibilite = dateDispo.trim() + " " + heureDispo.trim(); // "2025-08-10 16:30"

        UrgenceRequest u = new UrgenceRequest();
        u.setPrenom(prenom);
        u.setTelephone(telephone);
        u.setDisponibilite(disponibilite);
        u.setDescription(description);
        u.setStatut(Statut.valueOf(statut)); // EN_ATTENTE ou TRAITE

        urgenceService.enregistrer(u);
        return "redirect:/admin/urgences";
    }

    // Supprimer (corrige l'erreur 404)
    @PostMapping("/admin/urgences/{id}/supprimer")
    public String supprimer(@PathVariable Long id) {
        urgenceService.supprimer(id);
        return "redirect:/admin/urgences";
    }
}
