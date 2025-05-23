package be.plomberie.demo.controller;

import java.util.List;
import be.plomberie.demo.model.Avis;
import be.plomberie.demo.service.AvisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/avis")
public class AvisController {

    @Autowired
    private AvisService avisService;

    // Vue HTML
    @GetMapping
    public String afficherAvis(Model model) {
        model.addAttribute("avisList", avisService.getAllAvis());
        return "avis/index";
    }

    // API JSON
    @ResponseBody
    @GetMapping("/api")
    public List<Avis> getAvis() {
        return avisService.getAllAvis();
    }
}
