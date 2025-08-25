package be.plomberie.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "avis")
public class Avis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_avis")
    private Long idAvis;

    private String contenu;

    private int note; // 1..5

    // Client FACULTATIF
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id",
        nullable = true,
        foreignKey = @ForeignKey(name = "fk_avis_client"))
    private Client client;

    // Réalisation FACULTATIVE (clé étrangère autorisant NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "realisation_id",
        nullable = true,
        foreignKey = @ForeignKey(name = "fk_avis_realisation"))
    private Realisation realisation;
}