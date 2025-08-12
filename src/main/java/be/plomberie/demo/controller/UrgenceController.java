package be.plomberie.demo.controller;

import be.plomberie.demo.model.Client;
import be.plomberie.demo.model.UrgenceRequest;
import be.plomberie.demo.service.PendingUrgenceStore;
import be.plomberie.demo.service.StripeService;
import be.plomberie.demo.service.UrgenceRequestService;
import be.plomberie.demo.web.dto.UrgenceForm;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/urgence")
public class UrgenceController {

    private final StripeService stripeService;
    private final PendingUrgenceStore pendingStore;
    private final UrgenceRequestService urgenceService;

    public UrgenceController(StripeService stripeService,
                             PendingUrgenceStore pendingStore,
                             UrgenceRequestService urgenceService) {
        this.stripeService = stripeService;
        this.pendingStore = pendingStore;
        this.urgenceService = urgenceService;
    }

    @GetMapping
    public String form(Model model,
                       @RequestParam(value = "confirmation", required = false) Boolean confirmation) {
        model.addAttribute("urgenceForm", new UrgenceForm());
        if (Boolean.TRUE.equals(confirmation)) {
            model.addAttribute("confirmation", true);
        }
        return "urgence";
    }

    @PostMapping("/payer")
    public String payer(@ModelAttribute("urgenceForm") UrgenceForm form) throws StripeException {
        String successUrl = "http://localhost:8080/urgence/success";
        String cancelUrl = "http://localhost:8080/urgence/canceled";

        Session session = stripeService.creerSessionPaiementUrgence(successUrl, cancelUrl);

        // Stocke le formulaire en mémoire (clé = sessionId Stripe)
        pendingStore.put(session.getId(), form);

        return "redirect:" + session.getUrl();
    }

    @GetMapping("/success")
    public String success(@RequestParam("session_id") String sessionId) throws StripeException {
        Session session = stripeService.recupererSession(sessionId);
        if (!"paid".equalsIgnoreCase(session.getPaymentStatus())) {
            return "redirect:/urgence?confirmation=false";
        }

        UrgenceForm form = pendingStore.pop(sessionId);
        if (form == null) {
            return "redirect:/urgence?confirmation=false";
        }

        // Création en base SEULEMENT ici (paye=true)
        UrgenceRequest u = new UrgenceRequest();
        u.setPrenom(form.getPrenom());
        u.setTelephone(form.getTelephone());
        u.setDisponibilite(form.getDisponibilite()); // on garde String
        u.setDescription(form.getDescription());
        u.setContactEmail(form.getContactEmail());
        u.setStripeSessionId(sessionId);
        u.setPaye(true);
        // u.setClient(...); // si tu veux lier un client connecté, ajoute-le ici

        urgenceService.save(u);

        return "redirect:/urgence?confirmation=true";
    }

    @GetMapping("/canceled")
    public String canceled() {
        // rien en DB
        return "redirect:/urgence?confirmation=false";
    }
}
