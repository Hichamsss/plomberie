package be.plomberie.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    // Noms "FR"
    private String prenom;
    private String nom;

    // Sécurité
    @Column(name = "mot_de_passe", nullable = false)
    private String motDePasse; // BCrypt

    @Column(nullable = false)
    private String role = "ROLE_USER";

    private String telephone;


    public String getFirstName() 
    { return prenom; }
    
    public void setFirstName(String firstName)
    { this.prenom = firstName; }
    
    public String getLastName() 
    { return nom; }
    
    public void setLastName(String lastName) 
    { this.nom = lastName; }

    public void setFirstNameString(String firstName)
    { this.prenom = firstName; }

    public String getFirstname() 
    { return prenom; }
    
    public void setFirstname(String firstname) 
    { this.prenom = firstname; }
    
    public String getLastname() 
    { return nom; }
    
    public void setLastname(String lastname)
    { this.nom = lastname; }
}
