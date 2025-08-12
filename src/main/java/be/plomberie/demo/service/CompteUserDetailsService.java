package be.plomberie.demo.service;

import be.plomberie.demo.model.Compte;
import be.plomberie.demo.model.Client;
import be.plomberie.demo.repository.CompteRepository;
import be.plomberie.demo.repository.ClientRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompteUserDetailsService implements UserDetailsService {

    private final CompteRepository comptes;
    private final ClientRepository clients;

    public CompteUserDetailsService(CompteRepository comptes, ClientRepository clients) {
        this.comptes = comptes;
        this.clients = clients;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null) throw new UsernameNotFoundException("username null");

        // On crée une nouvelle variable trimée
        String uname = username.trim();

        // 1) tente comptes par email
        Compte c = comptes.findByEmailIgnoreCase(uname).orElse(null);

        // 2) sinon, récupère l'email depuis clients (email ou username)
        if (c == null) {
            Client cli = clients.findByEmailIgnoreCase(uname)
                    .orElseGet(() -> clients.findByUsernameIgnoreCase(uname).orElse(null));
            if (cli != null) c = comptes.findByEmailIgnoreCase(cli.getEmail().trim()).orElse(null);
        }

        if (c == null) throw new UsernameNotFoundException("Compte introuvable: " + uname);

        return new org.springframework.security.core.userdetails.User(
                c.getEmail().trim(),
                c.getMotDePasse(), // {bcrypt}... attendu
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}