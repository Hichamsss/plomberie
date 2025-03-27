package be.plomberie.demo.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	private String Description;
	private LocalDateTime datePublication;
	
}
