package be.plomberie.demo.controller;

import be.plomberie.demo.service.ServiceService;
import be.plomberie.demo.model.PlomberieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/services")
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    // Vue HTML
    @GetMapping
    public String listServices(Model model) {
        model.addAttribute("services", serviceService.getAllServices());
        model.addAttribute("title", "Liste des services");
        return "services/list";
    }

    // API JSON
    @ResponseBody
    @GetMapping("/api")
    public List<PlomberieService> listServicesApi() {
        return serviceService.getAllServices();
    }
}
