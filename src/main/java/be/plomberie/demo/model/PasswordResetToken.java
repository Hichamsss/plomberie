package be.plomberie.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "password_reset_tokens", indexes = @Index(columnList = "token", unique = true))
public class PasswordResetToken {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true, length=100)
    private String token;

    @ManyToOne(optional = false)
    private Compte compte;

    @Column(nullable=false)
    private LocalDateTime expiresAt;

    private boolean used = false;

    // getters/setters
    public Long getId() { return id; }
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public Compte getCompte() { return compte; }
    public void setCompte(Compte compte) { this.compte = compte; }
    public LocalDateTime getExpiresAt() { return expiresAt; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }
    public boolean isUsed() { return used; }
    public void setUsed(boolean used) { this.used = used; }
}
