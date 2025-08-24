package be.plomberie.demo.controller;

import be.plomberie.demo.model.Avis;
import be.plomberie.demo.repository.AvisRepository;
import be.plomberie.demo.service.AvisEligibilityService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@Validated
public class AvisController {

    private final AvisRepository avisRepo;
    private final AvisEligibilityService eligibility;

    public AvisController(AvisRepository avisRepo, AvisEligibilityService eligibility) {
        this.avisRepo = avisRepo;
        this.eligibility = eligibility;
    }

    // ✅ LISTE HTML: GET /avis
    @GetMapping("/avis")
    public String list(Model model) {
        model.addAttribute("avis", avisRepo.findAll(Sort.by(Sort.Direction.ASC, "idAvis")));
        return "avis/index";
    }

    // ✅ FORMULAIRE HTML
    @GetMapping("/avis/nouveau")
    public String form(Authentication auth, Model model) {
        if (auth == null || !eligibility.canLeaveReview(auth.getName())) {
            return "redirect:/compte/index";
        }
        model.addAttribute("form", new AvisForm());
        return "avis/form";
    }

    // ✅ SOUMISSION AVIS: POST /avis
    @PostMapping("/avis")
    public String submit(@ModelAttribute("form") @Validated AvisForm form,
                         Authentication auth) {
        if (auth == null || !eligibility.canLeaveReview(auth.getName())) {
            return "redirect:/compte/index";
        }
        Avis a = Avis.builder()
                .contenu(form.getContenu().trim())
                .note(form.getNote())
                .build();
        avisRepo.save(a);
        return "redirect:/avis";
    }

    public static class AvisForm {
        @NotBlank
        private String contenu;
        @Min(1) @Max(5)
        private int note = 5;

        public String getContenu() { return contenu; }
        public void setContenu(String contenu) { this.contenu = contenu; }
        public int getNote() { return note; }
        public void setNote(int note) { this.note = note; }
    }
}
