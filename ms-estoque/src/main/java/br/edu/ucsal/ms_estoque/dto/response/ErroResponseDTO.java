package br.edu.ucsal.ms_estoque.dto.response;

import java.time.LocalDateTime;

public record ErroResponseDTO(int status, String erro, String mensagem, LocalDateTime timestamp) {
}