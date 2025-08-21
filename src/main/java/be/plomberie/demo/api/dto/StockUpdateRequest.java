package be.plomberie.demo.api.dto;


import jakarta.validation.constraints.NotNull;

public record StockUpdateRequest(
        @NotNull Integer delta
) {}
