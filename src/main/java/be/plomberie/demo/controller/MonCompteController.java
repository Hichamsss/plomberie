package be.plomberie.demo.controller;

import be.plomberie.demo.model.Client;
import be.plomberie.demo.repository.ClientRepository;
import be.plomberie.demo.repository.DemandeDevisRepository;
import be.plomberie.demo.repository.UrgenceRequestRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MonCompteController {

    private final ClientRepository clientRepo;
    private final DemandeDevisRepository devisRepo;
    private final UrgenceRequestRepository urgRepo;

    public MonCompteController(ClientRepository clientRepo,
                               DemandeDevisRepository devisRepo,
                               UrgenceRequestRepository urgRepo) {
        this.clientRepo = clientRepo;
        this.devisRepo = devisRepo;
        this.urgRepo = urgRepo;
    }

    @GetMapping("/compte")
    public String compte(Authentication auth, Model model) {
        Client client = clientRepo.findByEmail(auth.getName()).orElseThrow();

        model.addAttribute("client", client);

        // 🔹 Devis liés au client ou à son email
        model.addAttribute("mesDevis",
                devisRepo.findAllByClientOrEmailOrderByIdDesc(client, client.getEmail()));

        // 🔹 Urgences liées au client, à son téléphone ou à son email
        String tel = (client.getTelephone() != null) ? client.getTelephone().trim() : null;
        model.addAttribute("mesUrgences",
                urgRepo.findAllByClientOrTelephoneOrContactEmailOrderByCreatedAtDesc(
                        client, tel, client.getEmail()));

        return "compte/index";
    }
}
