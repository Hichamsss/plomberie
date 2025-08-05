package be.plomberie.demo.service;

import be.plomberie.demo.model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    List<Client> getAllClients();
    Client saveClient(Client client);
    Optional<Client> findByEmail(String email);
}
