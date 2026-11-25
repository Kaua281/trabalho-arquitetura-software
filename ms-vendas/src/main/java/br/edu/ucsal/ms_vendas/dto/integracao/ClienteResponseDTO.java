package br.edu.ucsal.ms_vendas.dto.integracao;

public record ClienteResponseDTO(
        Long id,
        String nome,
        String email,
        String tipo,
        String idReceitaFederal
) {



}
