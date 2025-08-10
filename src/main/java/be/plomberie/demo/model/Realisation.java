package be.plomberie.demo.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
	
	 @OneToMany(mappedBy = "realisation", cascade = CascadeType.ALL, orphanRemoval = true)
	 private List<Avis> avis;
	
}
