package be.plomberie.demo.api.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record UpdateFournitureRequest(
        @Size(min = 3, max = 64) String reference,
        @NotBlank @Size(min = 2, max = 160) String nom,
        @Size(max = 10000) String description,
        @NotBlank String categorie,
        @Size(max = 80) String marque,
        @NotNull @DecimalMin("0.00") BigDecimal prixHT,
        @NotNull @Min(0) @Max(100) Integer tva,
        @NotBlank String unite,
        @NotNull @Min(0) Integer stock,
        @NotNull Boolean actif
) {}
