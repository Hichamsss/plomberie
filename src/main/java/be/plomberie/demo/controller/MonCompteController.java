package be.plomberie.demo.controller;

import java.util.Collections;
import java.util.List;

import be.plomberie.demo.model.Client;
import be.plomberie.demo.model.UrgenceRequest;
import be.plomberie.demo.repository.ClientRepository;
import be.plomberie.demo.repository.CompteRepository;
import be.plomberie.demo.repository.DemandeDevisRepository;
import be.plomberie.demo.repository.UrgenceRequestRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MonCompteController {

    private final ClientRepository clientRepo;
    private final CompteRepository compteRepo;
    private final DemandeDevisRepository devisRepo;
    private final UrgenceRequestRepository urgRepo;

    public MonCompteController(ClientRepository clientRepo,
                               CompteRepository compteRepo,
                               DemandeDevisRepository devisRepo,
                               UrgenceRequestRepository urgRepo) {
        this.clientRepo = clientRepo;
        this.compteRepo = compteRepo;
        this.devisRepo = devisRepo;
        this.urgRepo = urgRepo;
    }

    @GetMapping("/compte")
    public String compte(Authentication auth, Model model) {
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getName())) {
            return "redirect:/login";
        }

        String emailOrUsername = auth.getName();

        Client client = clientRepo.findByEmailIgnoreCase(emailOrUsername)
                .orElseGet(() -> clientRepo.findByUsernameIgnoreCase(emailOrUsername).orElse(null));

        model.addAttribute("client", client);

        // Devis : rassemblés par e-mail
        String email = (client != null) ? client.getEmail() : emailOrUsername;
        model.addAttribute("mesDevis", devisRepo.findAllByEmailOrderByIdDesc(email));

        // Urgences payées : via repo custom
        String tel = (client != null) ? client.getTelephone() : null;
        List<UrgenceRequest> urgencesPayees = urgRepo.findPaidForCurrentUser(client, tel, email);

        model.addAttribute("mesUrgences", urgencesPayees != null ? urgencesPayees : Collections.emptyList());

        return "compte/index";
    }
}
