package br.edu.ucsal.ms_estoque.util;

import br.edu.ucsal.ms_estoque.dto.request.EstoqueRequestDTO;
import br.edu.ucsal.ms_estoque.dto.EstoqueResponseDTO;
import br.edu.ucsal.ms_estoque.model.Estoque;

public class EstoqueMapper {

    private EstoqueMapper() {
    }

    public static Estoque toEntity(EstoqueRequestDTO dto) {
        return new Estoque(dto.produtoId(), dto.quantidade());
    }

    public static EstoqueResponseDTO toResponse(Estoque estoque) {
        return new EstoqueResponseDTO(estoque.getId(), estoque.getProdutoId(), estoque.getQuantidade());
    }
}