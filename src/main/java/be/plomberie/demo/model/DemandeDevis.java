package be.plomberie.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class DemandeDevis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String email;
    private String telephone;

    @Column(length = 1000)
    private String message;

    @Enumerated(EnumType.STRING)
    private Statut statut = Statut.EN_ATTENTE;

    private LocalDateTime dateSoumission = LocalDateTime.now();

    public enum Statut {
        EN_ATTENTE,
        TRAITE
    }

    // Getters et Setters
    public Long getId() { return id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public Statut getStatut() { return statut; }
    public void setStatut(Statut statut) { this.statut = statut; }
    public LocalDateTime getDateSoumission() { return dateSoumission; }
    public void setDateSoumission(LocalDateTime dateSoumission) { this.dateSoumission = dateSoumission; }
}
