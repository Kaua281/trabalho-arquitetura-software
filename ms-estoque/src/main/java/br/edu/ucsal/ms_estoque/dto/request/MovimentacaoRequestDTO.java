package br.edu.ucsal.ms_estoque.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record MovimentacaoRequestDTO(
    @NotNull(message = "produtoId é obrigatório") @Positive(message = "produtoId deve ser positivo") Long produtoId,
    @NotNull(message = "quantidade é obrigatória") @Positive(message = "quantidade deve ser maior que zero") Integer quantidade) {

    }