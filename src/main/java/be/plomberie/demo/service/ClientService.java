package be.plomberie.demo.service;

import be.plomberie.demo.model.Client;
import be.plomberie.demo.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository repo;

    public ClientService(ClientRepository repo) {
        this.repo = repo;
    }

    public Optional<Client> findByEmail(String email) {
        return repo.findByEmailIgnoreCase(email);
    }

    public Client save(Client c) {
        return repo.save(c);
    }

    public Optional<Client> findByUsername(String username) {
        return repo.findByUsernameIgnoreCase(username);
    }
}
