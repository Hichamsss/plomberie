package be.plomberie.demo.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "urgences")
public class UrgenceRequest {

    public enum Statut { EN_ATTENTE, TRAITE }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String prenom;
    private String telephone;

    // On garde String pour coller à tes templates/controllers
    private String disponibilite;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private Statut statut = Statut.EN_ATTENTE;

    @Column(name = "stripe_session_id")
    private String stripeSessionId;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "contact_email")
    private String contactEmail;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    /** Acompte payé (Stripe) */
    @Column(nullable = false)
    private boolean paye = false;
}
