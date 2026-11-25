package br.edu.ucsal.ms_vendas.dto.integracao;

public record EstoqueSaidaRequestDTO(
        Long produtoId,
        Integer quantidade
) {
}
