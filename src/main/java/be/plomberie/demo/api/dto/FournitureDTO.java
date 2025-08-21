package be.plomberie.demo.api.dto;

import java.math.BigDecimal;

public record FournitureDTO(
        Long id,
        String reference,
        String nom,
        String description,
        String categorie,
        String marque,
        BigDecimal prixHT,
        Integer tva,
        String unite,
        Integer stock,
        boolean actif
) {}
