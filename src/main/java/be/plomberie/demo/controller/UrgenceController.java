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
            try {
                Session session = stripeService.recupererSession(sessionId);

                UrgenceRequest urgence = new UrgenceRequest();
                urgence.setPrenom(session.getMetadata().get("prenom"));
                urgence.setTelephone(session.getMetadata().get("telephone"));
                urgence.setDisponibilite(session.getMetadata().get("disponibilite"));
                urgence.setDescription(session.getMetadata().get("description"));
                urgence.setContactEmail(session.getMetadata().get("email"));
                urgence.setStatut(Statut.EN_ATTENTE);

                if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName())) {
                    clientRepository.findByEmail(auth.getName()).ifPresent(c -> {
                        urgence.setClient(c);
                        urgence.setContactEmail(c.getEmail());
                    });
                }

                urgenceService.enregistrer(urgence);
                urgenceService.enregistrerStripeSession(urgence.getId(), sessionId);
                urgenceService.marquerPayeParSession(sessionId);

                model.addAttribute("confirmation", true);
            } catch (StripeException e) {
                model.addAttribute("confirmation", false);
            }
        }

        UrgenceForm form = new UrgenceForm();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName())) {
            form.setEmail(auth.getName());
        }
        model.addAttribute("urgenceForm", form);
        return "urgence/main";
    }

    @PostMapping("/urgence/payer")
    public RedirectView handleUrgenceForm(@ModelAttribute UrgenceForm form) throws StripeException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String emailForm = form.getEmail();

        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName())) {
            emailForm = auth.getName();
        }

        Session session = stripeService.creerSessionPaiementUrgenceAvecMetadataTemp(
                form.getPrenom(),
                form.getTelephone(),
                form.getDisponibilite(),
                form.getDescription(),
                emailForm
        );

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
