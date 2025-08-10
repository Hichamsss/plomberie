package be.plomberie.demo.service;

import java.util.List;
import java.util.Optional;

import be.plomberie.demo.model.Client;

public interface ClientService {
    List<Client> getAllClients();
    Client saveClient(Client client);
    Optional<Client> findByEmail(String email);
}
