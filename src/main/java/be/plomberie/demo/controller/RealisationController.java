package be.plomberie.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import be.plomberie.demo.model.Realisation;
import be.plomberie.demo.service.RealisationService;

@Controller
@RequestMapping("/realisations")
public class RealisationController {

    private final RealisationService realisationService;

    public RealisationController(RealisationService realisationService) {
        this.realisationService = realisationService;
    }

    // Vue HTML
    @GetMapping
    public String listRealisations(Model model) {
        model.addAttribute("title", "Nos Réalisations");
        model.addAttribute("realisations", realisationService.getAllRealisations());
        return "realisation/main";
    }

    // API JSON
    @ResponseBody
    @GetMapping("/api")
    public List<Realisation> getRealisationsApi() {
        return realisationService.getAllRealisations();
    }
}
