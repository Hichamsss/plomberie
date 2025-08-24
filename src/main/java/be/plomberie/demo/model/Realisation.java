package be.plomberie.demo.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
@Table(name="realisations")
public class Realisation {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int idRealisation;
	private String titre;
	private String description;
	private LocalDateTime datePublication;
	
	@Transient
	private java.util.List<Avis> avis = new java.util.ArrayList<>();
	
}
