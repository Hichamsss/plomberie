package be.plomberie.demo.service;

import be.plomberie.demo.model.Compte;
import be.plomberie.demo.repository.CompteRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service("compteUserDetailsService")
public class CompteUserDetailsService implements UserDetailsService {

    private final CompteRepository repo;

    public CompteUserDetailsService(CompteRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Compte c = repo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Compte introuvable: " + username));

        List<GrantedAuthority> auth = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        return User.withUsername(c.getEmail())
                .password(c.getMotDePasse())
                .authorities(auth)
                .build();
    }
}
