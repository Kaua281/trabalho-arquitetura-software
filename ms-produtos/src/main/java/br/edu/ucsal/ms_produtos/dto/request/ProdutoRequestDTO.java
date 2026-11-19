package br.edu.ucsal.ms_produtos.dto.request;

import java.math.BigDecimal;

public record ProdutoRequestDTO(
		String nome,
		String descricao,
		BigDecimal preco
		) {

}
