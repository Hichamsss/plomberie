package be.plomberie.demo.controller;

import be.plomberie.demo.model.Client;
import be.plomberie.demo.repository.ClientRepository;
import be.plomberie.demo.repository.CompteRepository;
import be.plomberie.demo.repository.DemandeDevisRepository;
import be.plomberie.demo.repository.UrgenceRequestRepository;
import be.plomberie.demo.service.AvisEligibilityService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MonCompteController {

    private final ClientRepository clientRepo;
    private final DemandeDevisRepository devisRepo;
    private final UrgenceRequestRepository urgRepo;
    private final CompteRepository compteRepo;
    private final AvisEligibilityService avisEligibilityService;

    @PersistenceContext
    private EntityManager em;

    public MonCompteController(ClientRepository clientRepo,
                               DemandeDevisRepository devisRepo,
                               UrgenceRequestRepository urgRepo,
                               CompteRepository compteRepo,
                               AvisEligibilityService avisEligibilityService) {
        this.clientRepo = clientRepo;
        this.devisRepo = devisRepo;
        this.urgRepo = urgRepo;
        this.compteRepo = compteRepo;
        this.avisEligibilityService = avisEligibilityService;
    }

    @GetMapping({"/compte", "/compte/index"})
    public String compte(Authentication auth, Model model) {
        if (auth == null || auth.getName() == null) return "redirect:/login";
        em.clear();

        Client client = clientRepo.findByEmail(auth.getName()).orElse(null);
        model.addAttribute("client", client);

        if (client != null) {
            model.addAttribute("mesDevis",
                    devisRepo.findAllByClientOrEmailOrderByIdDesc(client, client.getEmail()));
            String tel = client.getTelephone() != null ? client.getTelephone().trim() : null;
            var urgences = urgRepo.findAllByClientOrTelephoneOrContactEmailOrderByCreatedAtDesc(
                    client, tel, client.getEmail());
            urgences.forEach(u -> { try { em.refresh(u); } catch (Exception ignore) {} });
            model.addAttribute("mesUrgences", urgences);
        } else {
            model.addAttribute("mesDevis", java.util.Collections.emptyList());
            model.addAttribute("mesUrgences", java.util.Collections.emptyList());
        }

        model.addAttribute("peutLaisserAvis", avisEligibilityService.canLeaveReview(auth.getName()));
        return "compte/index";
    }

    @PostMapping("/compte/supprimer")
    @Transactional
    public String supprimerCompte(Authentication auth, HttpServletRequest request) {
        if (auth != null && auth.getName() != null) {
            clientRepo.findByEmail(auth.getName()).ifPresent(client -> {
                urgRepo.deleteByClient(client);     // enfants 1
                devisRepo.deleteByClient(client);   // enfants 2
                compteRepo.deleteByClient(client);  // compte lié
                clientRepo.delete(client);          // enfin le client
            });
        }
        var session = request.getSession(false);
        if (session != null) session.invalidate();
        SecurityContextHolder.clearContext();
        return "redirect:/";
    }
}
