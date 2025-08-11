package be.plomberie.demo.controller;

import be.plomberie.demo.model.DemandeDevis;
import be.plomberie.demo.model.Client;
import be.plomberie.demo.repository.ClientRepository;
import be.plomberie.demo.service.DemandeDevisService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class DemandeController {

    private final DemandeDevisService demandeService;
    private final ClientRepository clientRepository;

    public DemandeController(DemandeDevisService demandeService, ClientRepository clientRepository) {
        this.demandeService = demandeService;
        this.clientRepository = clientRepository;
    }

    @GetMapping("/demande")
    public String afficherFormulaire(Model model) {
        model.addAttribute("demande", new DemandeDevis());
        return "demande/demande";
    }

    @PostMapping("/demande")
    public String envoyer(@Valid @ModelAttribute("demande") DemandeDevis demande,
                          BindingResult binding, Model model) {
        if (binding.hasErrors()) return "demande/demande";

        if (demande.getStatut() == null) {
            demande.setStatut(DemandeDevis.StatutDemande.EN_ATTENTE);
        }

        // Lier le client si connecté
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName())) {
            clientRepository.findByEmail(auth.getName()).ifPresent(demande::setClient);
        }

        demandeService.creer(demande);
        model.addAttribute("message", "Merci pour votre demande, nous vous répondrons rapidement !");
        model.addAttribute("demande", new DemandeDevis());
        return "demande/demande";
    }
}
