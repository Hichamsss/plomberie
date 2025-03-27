package be.plomberie.demo.model;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Data
@NoArgsConstructor (force = true , access = AccessLevel.PUBLIC) // POTECTED changé en PUBLIC
@Entity
@Table(name = "devis")
public class Devis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDevis;
    
    private String description;
    private LocalDateTime dateCreation;
    private String status;
}
