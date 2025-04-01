package be.plomberie.demo.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="services")
public class PlomberieService {  // 👈 Nouveau nom pour éviter le conflit
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idService;
    private String service;
    private String description;
    private double prix;
    
    @ManyToMany(mappedBy = "services")
    private List<Devis> devis;
}
