package be.plomberie.demo.controller;

import be.plomberie.demo.model.Client;
import be.plomberie.demo.repository.ClientRepository;
import be.plomberie.demo.repository.DemandeDevisRepository;
import be.plomberie.demo.repository.UrgenceRequestRepository;
import be.plomberie.demo.service.AvisEligibilityService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MonCompteController {

    private final ClientRepository clientRepo;
    private final DemandeDevisRepository devisRepo;
    private final UrgenceRequestRepository urgRepo;
    private final AvisEligibilityService avisEligibilityService;

    @PersistenceContext
    private EntityManager em;

    public MonCompteController(ClientRepository clientRepo,
                               DemandeDevisRepository devisRepo,
                               UrgenceRequestRepository urgRepo,
                               AvisEligibilityService avisEligibilityService) {
        this.clientRepo = clientRepo;
        this.devisRepo = devisRepo;
        this.urgRepo = urgRepo;
        this.avisEligibilityService = avisEligibilityService;
    }

    @GetMapping("/compte")
    public String compte(Authentication auth, Model model) {
        if (auth == null || auth.getName() == null) {
            return "redirect:/login";
        }

        em.clear();

        Client client = clientRepo.findByEmail(auth.getName()).orElse(null);
        model.addAttribute("client", client);

        if (client != null) {
            model.addAttribute("mesDevis",
                    devisRepo.findAllByClientOrEmailOrderByIdDesc(client, client.getEmail()));

            String tel = (client.getTelephone() != null) ? client.getTelephone().trim() : null;

            var urgences = urgRepo.findAllByClientOrTelephoneOrContactEmailOrderByCreatedAtDesc(
                    client, tel, client.getEmail());

            urgences.forEach(u -> {
                try { em.refresh(u); } catch (Exception ignore) {}
            });

            model.addAttribute("mesUrgences", urgences);
        } else {
            model.addAttribute("mesDevis", java.util.Collections.emptyList());
            model.addAttribute("mesUrgences", java.util.Collections.emptyList());
        }

        boolean peutLaisserAvis = avisEligibilityService.canLeaveReview(auth.getName());
        model.addAttribute("peutLaisserAvis", peutLaisserAvis);

        return "compte/index";
    }
}
