package be.plomberie.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "clients")
@Getter @Setter
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String lastname;
    private String email;
    private String telephone;

    @OneToOne(mappedBy = "client", fetch = FetchType.LAZY, optional = true)
    private Compte compte;
}
