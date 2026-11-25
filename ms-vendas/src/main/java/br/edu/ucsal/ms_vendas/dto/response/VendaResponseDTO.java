package br.edu.ucsal.ms_vendas.dto.response;

import java.math.BigDecimal;

public record VendaResponseDTO(
        Long id,
        Long idCliente,
        Long idProduto,
        Integer quantidade,
        BigDecimal precoUnitario,
        BigDecimal total
) {
}
