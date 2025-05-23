package be.plomberie.demo.controller;

import be.plomberie.demo.service.ServiceService;
import be.plomberie.demo.model.PlomberieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ServiceService serviceService;

    // Vue HTML
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Liste des services");
        model.addAttribute("services", serviceService.getAllServices());
        return "home/list";
    }

    // API JSON
    @ResponseBody
    @GetMapping("/api/home")
    public List<PlomberieService> homeApi() {
        return serviceService.getAllServices();
    }
}
