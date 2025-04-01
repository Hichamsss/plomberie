package be.plomberie.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="comptes")
public class Compte {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String nom;
	private String email;
	
	@Column(name = "mot_de_passe") // Ajoutez cette annotation
	private String motDePasse;
	
	
	 @OneToOne
	 @JoinColumn(name = "client_id", unique = true)
	 private Client client;

}
