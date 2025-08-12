package be.plomberie.demo.controller;

import be.plomberie.demo.model.UrgenceRequest;
import be.plomberie.demo.service.UrgenceRequestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/urgences")
public class AdminUrgenceController {

    private final UrgenceRequestService service;

    public AdminUrgenceController(UrgenceRequestService service) {
        this.service = service;
    }

    @GetMapping
    public String liste(Model model) {
        model.addAttribute("urgences", service.getPaidOnly()); // uniquement payées
        return "admin/urgence/liste";
    }

    @PostMapping("/{id}/traiter")
    public String traiter(@PathVariable Long id) {
        service.markAsTraite(id);
        return "redirect:/admin/urgences";
    }

    @PostMapping("/{id}/supprimer")
    public String supprimer(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/admin/urgences";
    }

    @PostMapping("/ajouter")
    public String ajouterDepuisAdmin(@RequestParam String prenom,
                                     @RequestParam String telephone,
                                     @RequestParam String dateDispo,
                                     @RequestParam String heureDispo,
                                     @RequestParam String description,
                                     @RequestParam(defaultValue = "EN_ATTENTE") String statut,
                                     @RequestParam(required = false) String contactEmail) {
        UrgenceRequest u = new UrgenceRequest();
        u.setPrenom(prenom);
        u.setTelephone(telephone);
        u.setDisponibilite(dateDispo + " " + heureDispo); // on garde String
        u.setDescription(description);
        u.setContactEmail(contactEmail);
        u.setStatut(UrgenceRequest.Statut.valueOf(statut));
        u.setPaye(true); // ajouté par admin => considéré payé
        service.save(u);
        return "redirect:/admin/urgences";
    }
}
