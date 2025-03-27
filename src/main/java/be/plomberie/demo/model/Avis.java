package be.plomberie.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor (force = true , access = AccessLevel.PROTECTED)
@Entity
@Table (name="avis")
public class Avis {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int idAvis;
	private String contenu;
	private int note;

}
