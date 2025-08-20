package be.plomberie.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "retour")
public class Retour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRetour;

    @Column(nullable = false, length = 500)
    private String contenu;

    @Column(nullable = false)
    private int note;
}
