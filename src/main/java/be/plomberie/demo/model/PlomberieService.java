package be.plomberie.demo.model;

import jakarta.persistence.*;

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
}
