package br.edu.ucsal.ms_produtos.dto.response;

import java.math.BigDecimal;

public record ProdutoResponseDTO(
		Long id,
		String nome,
		String descricao,
		BigDecimal preco
		) {

}
