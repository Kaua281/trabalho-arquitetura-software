package br.edu.ucsal.ms_produtos.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProdutoRequestDTO(
		@NotBlank
		@Size(max = 150)
		String nome,
		
		@Size(max = 255)
		String descricao,
		
		@NotNull
		@DecimalMin(value = "0.01")
		@Digits(integer = 10, fraction = 2)
		BigDecimal preco
		) {

}
