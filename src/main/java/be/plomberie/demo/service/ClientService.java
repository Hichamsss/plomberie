package be.plomberie.demo.service;

<<<<<<< HEAD
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.plomberie.demo.model.Client;
import be.plomberie.demo.repository.ClientRepository;
=======
import be.plomberie.demo.model.Client;
import be.plomberie.demo.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
>>>>>>> origin/main

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    // Récupérer tous les clients
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    // Récupérer un client par son ID
    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    // Ajouter ou mettre à jour un client
    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    // Supprimer un client
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}
