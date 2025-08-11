package be.plomberie.demo.controller;

import be.plomberie.demo.model.Client;
import be.plomberie.demo.service.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ClientController {

    private final ClientService service;

    public ClientController(ClientService service) {
        this.service = service;
    }

    @PostMapping("/clients/creer")
    public String creerClient(@RequestParam String firstname,
                              @RequestParam String lastname,
                              @RequestParam String email,
                              @RequestParam(required = false) String telephone,
                              Model model) {
        Client c = new Client();
        c.setFirstname(firstname);
        c.setLastname(lastname);
        c.setEmail(email);
        c.setTelephone(telephone);

        service.save(c);
        return "redirect:/clients";
    }
}
