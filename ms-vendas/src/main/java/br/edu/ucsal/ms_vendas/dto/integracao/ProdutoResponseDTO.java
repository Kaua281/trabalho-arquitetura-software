package br.edu.ucsal.ms_vendas.dto.integracao;

import java.math.BigDecimal;

public record ProdutoResponseDTO(
        Long id,
        String nome,
        BigDecimal preco
){
}
