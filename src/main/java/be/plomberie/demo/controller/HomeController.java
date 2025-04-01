package be.plomberie.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import be.plomberie.demo.service.ServiceService;

@Controller
public class HomeController {

    @Autowired
    private ServiceService serviceService;

    @GetMapping("/") 
    public String home(Model model) {
        model.addAttribute("title", "Liste des services");
        model.addAttribute("services", serviceService.getAllServices());
        return "home/list"; 
    }
}
