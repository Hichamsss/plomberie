package be.plomberie.demo.api.dto;

import java.time.LocalDateTime;

public record ConseilDTO(
        Long id,
        String titre,
        String description,
        String categorie,
        boolean actif,
        LocalDateTime createdAt
) {}