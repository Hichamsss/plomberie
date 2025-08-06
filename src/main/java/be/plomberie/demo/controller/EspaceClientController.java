package be.plomberie.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import be.plomberie.demo.model.Client;
import be.plomberie.demo.model.DemandeDevis;
import be.plomberie.demo.model.UrgenceRequest;
import be.plomberie.demo.service.ClientService;
import be.plomberie.demo.service.DemandeDevisService;
import be.plomberie.demo.service.UrgenceRequestService;

@Controller
@RequestMapping("/client")
public class EspaceClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private DemandeDevisService devisService;

    @Autowired
    private UrgenceRequestService urgenceRequestService;

    @GetMapping("/espace")
    public String afficherEspaceClient(Model model, Authentication authentication) {
        String email = authentication.getName();
        Client client = clientService.findByEmail(email).orElse(null);

        if (client == null) {
            return "redirect:/login?error=client_not_found";
        }

        List<DemandeDevis> tousLesDevis = devisService.getAll();

        // Filtrage manuel par client si nécessaire
        List<DemandeDevis> mesDevis = tousLesDevis.stream()
                .filter(devis -> devis.getClient() != null && devis.getClient().getId().equals(client.getId()))
                .collect(Collectors.toList());

        List<UrgenceRequest> mesUrgences = urgenceRequestService.getAll();

        model.addAttribute("mesDevis", mesDevis);
        model.addAttribute("mesUrgences", mesUrgences);
        return "client/espace";
    }
}
