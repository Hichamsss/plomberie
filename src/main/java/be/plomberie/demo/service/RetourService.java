package be.plomberie.demo.service;

import be.plomberie.demo.model.Retour;
import java.util.List;

public interface RetourService {
    List<Retour> getAllRetours();
    Retour saveRetour(Retour retour);
}
