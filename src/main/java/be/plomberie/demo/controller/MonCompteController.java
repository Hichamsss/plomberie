package be.plomberie.demo.controller;

import be.plomberie.demo.model.Client;
import be.plomberie.demo.repository.ClientRepository;
import be.plomberie.demo.repository.DemandeDevisRepository;
import be.plomberie.demo.repository.UrgenceRequestRepository;
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

    @PersistenceContext
    private EntityManager em; // ✅ ajout minimal

    public MonCompteController(ClientRepository clientRepo,
                               DemandeDevisRepository devisRepo,
                               UrgenceRequestRepository urgRepo) {
        this.clientRepo = clientRepo;
        this.devisRepo = devisRepo;
        this.urgRepo = urgRepo;
    }

    @GetMapping("/compte")
    public String compte(Authentication auth, Model model) {
        em.clear(); // vide le 1er niveau de cache

        Client client = clientRepo.findByEmail(auth.getName()).orElseThrow();
        model.addAttribute("client", client);

        model.addAttribute("mesDevis",
                devisRepo.findAllByClientOrEmailOrderByIdDesc(client, client.getEmail()));

        String tel = (client.getTelephone() != null) ? client.getTelephone().trim() : null;

        // 1) on récupère tes urgences comme d’habitude
        var urgences = urgRepo.findAllByClientOrTelephoneOrContactEmailOrderByCreatedAtDesc(
                client, tel, client.getEmail());

        // 2) on force un rechargement depuis la BDD pour CHAQUE entité
        //    (met à jour le champ statut même si l’instance était périmée)
        urgences.forEach(u -> {
            try { em.refresh(u); } catch (Exception ignore) {}
        });

        model.addAttribute("mesUrgences", urgences);
        return "compte/index";
    }


}
