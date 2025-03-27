package be.plomberie.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import be.plomberie.demo.model.Client;
import be.plomberie.demo.service.ClientService;

@Controller
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/clients")
    public String afficherClients(Model model) {
        List<Client> clients = clientService.getAllClients();
        model.addAttribute("clients", clients);
        model.addAttribute("title", "Liste des clients");
        return "client/main"; 
    }

    @PostMapping("/ajouter-client")
    public String ajouterClient(@RequestParam String firstname, 
                                @RequestParam String lastname, 
                                @RequestParam String email, 
                                @RequestParam String telephone) {
        Client client = new Client();
        client.setFirstname(firstname);
        client.setLastname(lastname);
        client.setEmail(email);
        client.setTelephone(telephone);
        clientService.saveClient(client);
        return "redirect:/clients"; // Redirige vers la liste des clients après l'ajout
    }
}
