package be.plomberie.demo.mapper;

import be.plomberie.demo.api.dto.*;
import be.plomberie.demo.model.Fourniture;

public class FournitureMapper {

    public static FournitureDTO toDTO(Fourniture f) {
        return new FournitureDTO(
                f.getId(),
                f.getReference(),
                f.getNom(),
                f.getDescription(),
                f.getCategorie().name(),
                f.getMarque(),
                f.getPrixHT(),
                f.getTva(),
                f.getUnite().name(),
                f.getStock(),
                f.isActif()
        );
    }

    public static Fourniture fromCreate(CreateFournitureRequest r) {
        Fourniture f = new Fourniture();
        f.setReference(r.reference());
        f.setNom(r.nom());
        f.setDescription(r.description());
        f.setCategorie(Fourniture.Categorie.valueOf(r.categorie().toUpperCase()));
        f.setMarque(r.marque());
        f.setPrixHT(r.prixHT());
        f.setTva(r.tva());
        f.setUnite(Fourniture.Unite.valueOf(r.unite().toUpperCase()));
        f.setStock(r.stock());
        if (r.actif() != null) f.setActif(r.actif());
        return f;
    }

    public static void applyUpdate(Fourniture f, UpdateFournitureRequest r) {
        f.setReference(r.reference());
        f.setNom(r.nom());
        f.setDescription(r.description());
        f.setCategorie(Fourniture.Categorie.valueOf(r.categorie().toUpperCase()));
        f.setMarque(r.marque());
        f.setPrixHT(r.prixHT());
        f.setTva(r.tva());
        f.setUnite(Fourniture.Unite.valueOf(r.unite().toUpperCase()));
        f.setStock(r.stock());
        f.setActif(r.actif());
    }
}
