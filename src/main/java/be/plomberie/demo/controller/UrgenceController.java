package be.plomberie.demo.controller;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import be.plomberie.demo.model.UrgenceForm;
import be.plomberie.demo.model.UrgenceRequest;
import be.plomberie.demo.model.UrgenceRequest.Statut;
import be.plomberie.demo.repository.ClientRepository;
import be.plomberie.demo.service.StripeService;
import be.plomberie.demo.service.UrgenceRequestService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class UrgenceController {

    private final StripeService stripeService;
    private final UrgenceRequestService urgenceService;
    private final ClientRepository clientRepository;

    public UrgenceController(UrgenceRequestService urgenceService,
                             StripeService stripeService,
                             ClientRepository clientRepository) {
        this.urgenceService = urgenceService;
        this.stripeService = stripeService;
        this.clientRepository = clientRepository;
    }

    // ------ Côté public (paiement) ------
    @GetMapping("/urgence")
    public String showUrgenceForm(@RequestParam(required = false) String success,
                                  @RequestParam(name = "session_id", required = false) String sessionId,
                                  Authentication auth,
                                  Model model) {
        if ("true".equals(success) && sessionId != null) {
            urgenceService.marquerPayeParSession(sessionId);
            model.addAttribute("confirmation", true);
        }

        UrgenceForm form = new UrgenceForm();
        // Préremplir l'email si l’utilisateur est connecté
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName())) {
            form.setEmail(auth.getName());
        }
        model.addAttribute("urgenceForm", form);
        return "urgence/main";
    }

    @PostMapping("/urgence/payer")
    public RedirectView handleUrgenceForm(@ModelAttribute UrgenceForm form) throws StripeException {
        UrgenceRequest urgence = new UrgenceRequest();
        urgence.setPrenom(form.getPrenom());
        urgence.setTelephone(form.getTelephone());
        urgence.setDisponibilite(form.getDisponibilite());
        urgence.setDescription(form.getDescription());
        urgence.setStatut(Statut.EN_ATTENTE);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName())) {
            clientRepository.findByEmail(auth.getName()).ifPresent(c -> {
                urgence.setClient(c);
                urgence.setContactEmail(c.getEmail());
            });
        }

        // Si pas connecté ou pas de client trouvé : on prend l’email du formulaire
        if (urgence.getContactEmail() == null || urgence.getContactEmail().isBlank()) {
            urgence.setContactEmail(form.getEmail());
        }

        // Enregistrement AVANT paiement
        urgenceService.enregistrer(urgence);

        Session session = stripeService.creerSessionPaiementUrgenceAvecMetadata(urgence.getId());
        urgenceService.enregistrerStripeSession(urgence.getId(), session.getId());

        return new RedirectView(session.getUrl());
    }

    // ------ Côté admin ------
    @GetMapping("/admin/urgences")
    public String voirToutesLesUrgences(Model model) {
        model.addAttribute("urgences", urgenceService.getAll());
        return "admin/urgence/urgences";
    }

    @PostMapping("/admin/urgences/{id}/traiter")
    public String marquerTraite(@PathVariable Long id) {
        urgenceService.marquerTraite(id);
        return "redirect:/admin/urgences";
    }

    @PostMapping("/admin/urgences/ajouter")
    public String ajouterUrgence(@RequestParam String prenom,
                                 @RequestParam String telephone,
                                 @RequestParam String dateDispo,
                                 @RequestParam String heureDispo,
                                 @RequestParam String description,
                                 @RequestParam(defaultValue = "EN_ATTENTE") String statut,
                                 @RequestParam(required = false) String contactEmail) {
        String disponibilite = dateDispo.trim() + " " + heureDispo.trim();

        UrgenceRequest urgence = new UrgenceRequest();
        urgence.setPrenom(prenom);
        urgence.setTelephone(telephone);
        urgence.setDisponibilite(disponibilite);
        urgence.setDescription(description);
        urgence.setStatut(Statut.valueOf(statut));
        urgence.setContactEmail(contactEmail);

        urgenceService.enregistrer(urgence);
        return "redirect:/admin/urgences";
    }

    @PostMapping("/admin/urgences/{id}/supprimer")
    public String supprimer(@PathVariable Long id) {
        urgenceService.supprimer(id);
        return "redirect:/admin/urgences";
    }
}
