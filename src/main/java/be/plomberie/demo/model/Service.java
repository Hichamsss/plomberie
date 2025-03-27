package be.plomberie.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="services")
public class Service {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int idService;
	private String service;
	private String description;
	private double prix;
}
