package be.plomberie.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "conseil_entretien")
public class ConseilEntretien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank @Size(max = 140)
    private String titre;

    @NotBlank @Size(max = 1000)
    private String description;

    @NotBlank @Size(max = 40)
    private String categorie; // chauffage, sanitaire, robinetterie, etc.

    @Column(nullable = false)
    private boolean actif = true;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    void preUpdate() { updatedAt = LocalDateTime.now(); }

    // Getters/Setters
    public Long getId() { return id; }
    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getCategorie() { return categorie; }
    public void setCategorie(String categorie) { this.categorie = categorie; }
    public boolean isActif() { return actif; }
    public void setActif(boolean actif) { this.actif = actif; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
