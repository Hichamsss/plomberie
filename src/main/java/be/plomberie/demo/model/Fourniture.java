package be.plomberie.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "fourniture")
public class Fourniture {

    public enum Categorie {
        TUBES, RACCORDS, ROBINETTERIE, CHAUFFAGE, SANITAIRE, ETANCHEITE, OUTILLAGE, DRAINAGE, ISOLATION, DIVERS
    }

    public enum Unite {
        PIECE, METRE, LOT, LITRE, KILO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 64)
    private String reference;

    @Column(nullable = false, length = 160)
    private String nom;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private Categorie categorie;

    @Column(length = 80)
    private String marque;

    @Column(name = "prix_ht", precision = 12, scale = 2, nullable = false)
    private BigDecimal prixHT;

    @Min(0) @Max(100)
    @Column(nullable = false)
    private Integer tva;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private Unite unite;

    @Min(0)
    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private boolean actif = true;

    @Column(nullable = false)
    private boolean deleted = false;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
        if (reference == null || reference.isBlank()) {
            reference = "REF-" + System.currentTimeMillis();
        }
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Getters & Setters
    public Long getId() { return id; }
    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Categorie getCategorie() { return categorie; }
    public void setCategorie(Categorie categorie) { this.categorie = categorie; }
    public String getMarque() { return marque; }
    public void setMarque(String marque) { this.marque = marque; }
    public BigDecimal getPrixHT() { return prixHT; }
    public void setPrixHT(BigDecimal prixHT) { this.prixHT = prixHT; }
    public Integer getTva() { return tva; }
    public void setTva(Integer tva) { this.tva = tva; }
    public Unite getUnite() { return unite; }
    public void setUnite(Unite unite) { this.unite = unite; }
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
    public boolean isActif() { return actif; }
    public void setActif(boolean actif) { this.actif = actif; }
    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
