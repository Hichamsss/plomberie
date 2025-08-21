package be.plomberie.demo.mapper;

import be.plomberie.demo.api.dto.ConseilDTO;
import be.plomberie.demo.model.ConseilEntretien;

public class ConseilMapper {
    public static ConseilDTO toDTO(ConseilEntretien c) {
        return new ConseilDTO(
                c.getId(),
                c.getTitre(),
                c.getDescription(),
                c.getCategorie(),
                c.isActif(),
                c.getCreatedAt()
        );
    }
}