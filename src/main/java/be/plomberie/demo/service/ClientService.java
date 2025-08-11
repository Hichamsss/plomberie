package be.plomberie.demo.service;

import be.plomberie.demo.model.Client;
import be.plomberie.demo.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository repo;

    public ClientService(ClientRepository repo) {
        this.repo = repo;
    }

    public List<Client> getAllClients() {
        return repo.findAll();
    }

    public Client save(Client client) {
        return repo.save(client);
    }

    public Optional<Client> findByEmail(String email) {
        return repo.findByEmail(email);
    }
}
