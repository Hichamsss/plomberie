package be.plomberie.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class UrgenceRequest {

    public enum Statut {
        EN_ATTENTE,
        TRAITE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String prenom;
    private String telephone;
    private String disponibilite;

    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private Statut statut;

    public UrgenceRequest() {
        this.createdAt = LocalDateTime.now();
        this.statut = Statut.EN_ATTENTE;
    }

    public Long getId() {
        return id;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getDisponibilite() {
        return disponibilite;
    }

    public void setDisponibilite(String disponibilite) {
        this.disponibilite = disponibilite;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }
}
