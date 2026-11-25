package br.edu.ucsal.ms_vendas.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record VendaRequestDTO(
        @NotNull
        Long idCliente,
        @NotNull
        Long idProduto,
        @NotNull
        @Positive
        Integer quantidade
    ) {
}
