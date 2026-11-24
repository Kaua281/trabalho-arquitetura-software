package br.edu.ucsal.ms_estoque.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record EstoqueRequestDTO (
	@NotNull(message = "produtoId é obrigatório") @Positive(message = "produtoId deve ser positivo") Long produtoId,
	@NotNull(message = "quantidade é obrigatória") @PositiveOrZero(message = "quantidade não pode ser negativa") Integer quantidade) {

    }

