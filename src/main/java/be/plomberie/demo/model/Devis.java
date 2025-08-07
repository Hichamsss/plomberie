package be.plomberie.demo.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "devis")
public class Devis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDevis;

    private String description;
    private LocalDateTime dateCreation;

    @Enumerated(EnumType.STRING)
    private Statut statut;

    @ManyToMany
    @JoinTable(
        name = "devis_services",
        joinColumns = @JoinColumn(name = "devis_id"),
        inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private List<PlomberieService> services;

    public enum Statut {
        EN_ATTENTE,
        EN_COURS,
        TERMINE
    }
}
