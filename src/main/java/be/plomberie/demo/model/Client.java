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
@NoArgsConstructor (force = true , access = AccessLevel.PUBLIC) // POTECTED changé en PUBLIC
@Entity
@Table(name = "clients") // Assurez-vous que votre table en base de données s'appelle bien "clients"
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String lastname;
    private String email;
    private String telephone;

   
}
