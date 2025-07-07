package be.plomberie.demo.model;



import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    
    @ManyToMany
    @JoinTable(
        name = "devis_services",
        joinColumns = @JoinColumn(name = "devis_id"),
        inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private List<PlomberieService> services;

}
