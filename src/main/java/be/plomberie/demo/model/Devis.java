package be.plomberie.demo.model;

import java.time.LocalDateTime;

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
@Table(name="devis")
public class Devis {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idDevis;
	private String description;
	private LocalDateTime Date;
	private Status status;

}
