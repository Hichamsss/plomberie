package be.plomberie.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
    name = "comptes",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_comptes_email", columnNames = "email"),
        @UniqueConstraint(name = "uk_comptes_client", columnNames = "client_id")
    }
)
@Getter @Setter
public class Compte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @Column(length = 191, nullable = false)
    private String email;

    @Column(name = "mot_de_passe", nullable = false)
    private String motDePasse;

    @OneToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id",
        foreignKey = @ForeignKey(name = "fk_comptes_client"))
    private Client client;
}
