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
    private Integer idAvis;

    @Column(nullable = false, length = 500)
    private String contenu;

    @Column(nullable = false)
    private int note;
}
