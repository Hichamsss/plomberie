package be.plomberie.demo.service;

import be.plomberie.demo.model.Compte;
import be.plomberie.demo.model.PasswordResetToken;
import be.plomberie.demo.repository.PasswordResetTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
public class PasswordResetService {
    private final PasswordResetTokenRepository repo;
    private final SecureRandom random = new SecureRandom();

    public PasswordResetService(PasswordResetTokenRepository repo) {
        this.repo = repo;
    }

    private String generateToken() {
        byte[] bytes = new byte[48]; // 64 chars base64-ish
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    @Transactional
    public PasswordResetToken createToken(Compte compte, int minutesValidite) {
        repo.deleteAllExpired(LocalDateTime.now());
        String token = generateToken();
        PasswordResetToken t = new PasswordResetToken(token, LocalDateTime.now().plusMinutes(minutesValidite), compte);
        return repo.save(t);
    }
}
