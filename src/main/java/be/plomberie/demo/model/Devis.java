package be.plomberie.demo.model;

<<<<<<< HEAD

import jakarta.persistence.*;
=======
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
>>>>>>> origin/main
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

<<<<<<< HEAD

import java.time.LocalDateTime;

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
=======
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

>>>>>>> origin/main
}
