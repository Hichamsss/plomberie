package be.plomberie.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import be.plomberie.demo.model.Client;
import be.plomberie.demo.service.ClientService;

@Controller
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    // Vue utilisateur
    @GetMapping
    public String afficherClients(Model model) {
        model.addAttribute("clients", clientService.getAllClients());
        model.addAttribute("title", "Liste des clients");
        return "client/main";
    }

    @PostMapping("/ajouter")
    public String ajouterClient(@RequestParam String firstname, 
                                @RequestParam String lastname, 
                                @RequestParam String email, 
                                @RequestParam String telephone) {
        Client client = new Client(firstname, lastname, email, telephone);
        clientService.saveClient(client);
        return "redirect:/clients";
    }

    // Vue admin
    @GetMapping("/admin/clients")
    public String afficherClientsAdmin(Model model) {
        model.addAttribute("clients", clientService.getAllClients());
        return "admin/clients";
    }

    // API JSON
    @ResponseBody
    @GetMapping("/api")
    public List<Client> getClients() {
        return clientService.getAllClients();
    }

    @ResponseBody
    @PostMapping("/api")
    public Client addClientApi(@RequestBody Client client) {
        return clientService.saveClient(client);
    }
}
