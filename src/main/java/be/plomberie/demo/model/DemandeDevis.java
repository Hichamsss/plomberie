package be.plomberie.demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class DemandeDevis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String email;
    private String telephone;
    private String message;

    @Enumerated(EnumType.STRING)
    private StatutDemande statut;

    public enum StatutDemande {
        EN_ATTENTE,
        EN_COURS,
        TRAITE
    }
}
