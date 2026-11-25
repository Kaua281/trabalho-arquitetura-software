package br.edu.ucsal.ms_estoque.exception;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.edu.ucsal.ms_estoque.dto.response.ErroResponseDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EstoqueNaoEncontradoException.class)
    public ResponseEntity<ErroResponseDTO> naoEncotrado(EstoqueNaoEncontradoException ex) {
        return montar(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler({ EstoqueInsuficienteException.class, EstoqueDuplicadoException.class })
    public ResponseEntity<ErroResponseDTO> conflito(RuntimeException ex) {
        return montar(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponseDTO> validacao(MethodArgumentNotValidException ex) {
        String mensagem = ex.getBindingResult().getFieldErrors().stream()
                .map(erro -> erro.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return montar(HttpStatus.BAD_REQUEST, mensagem);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResponseDTO> erroInterno(Exception ex) {
        return montar(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no serviço de estoque");
    }

    private ResponseEntity<ErroResponseDTO> montar(HttpStatus status, String mensagem) {
        ErroResponseDTO corpo = new ErroResponseDTO(status.value(), status.getReasonPhrase(), mensagem, LocalDateTime.now());
        return ResponseEntity.status(status).body(corpo);
    }
}