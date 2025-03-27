package be.plomberie.demo.controller;

<<<<<<< HEAD
import java.util.List;

=======
import be.plomberie.demo.model.Avis;
import be.plomberie.demo.service.AvisService;
>>>>>>> origin/main
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

<<<<<<< HEAD
import be.plomberie.demo.model.Avis;
import be.plomberie.demo.service.AvisService;
=======
import java.util.List;
>>>>>>> origin/main

@Controller
public class AvisController {

    @Autowired
    private AvisService avisService;

    @GetMapping("/avis")
    public String afficherAvis(Model model) {
        List<Avis> avisList = avisService.getAllAvis();
        model.addAttribute("avisList", avisList);
        return "avis/index";
    }
}
