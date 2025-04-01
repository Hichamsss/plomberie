package be.plomberie.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import be.plomberie.demo.service.RealisationService;

@Controller
@RequestMapping("/realisations")
public class RealisationController {

    private final RealisationService realisationService;

    public RealisationController(RealisationService realisationService) {
        this.realisationService = realisationService;
    }

    @GetMapping
    public String listRealisations(Model model) {
        model.addAttribute("realisations", realisationService.getAllRealisations());
        return "realisation/main";
    }
}