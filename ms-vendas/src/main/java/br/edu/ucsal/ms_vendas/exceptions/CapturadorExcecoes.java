package br.edu.ucsal.ms_vendas.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class CapturadorExcecoes {

    private RespostaErroPadrao formatarResposta(Integer status, String erro, String mensagem, String caminho) {
        return new RespostaErroPadrao(Instant.now(),status,erro,mensagem,caminho);
    }

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<RespostaErroPadrao> recursoNaoEncontrado(RecursoNaoEncontradoException e, HttpServletRequest request) {
        int status = HttpStatus.NOT_FOUND.value();
        String msgErro = "Recurso não encontrado.";
        RespostaErroPadrao resposta = formatarResposta(status, msgErro, e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(resposta);
    }
    @ExceptionHandler(EstoqueInsuficienteException.class)
    public ResponseEntity<RespostaErroPadrao> estoqueInsuficiente(EstoqueInsuficienteException e, HttpServletRequest request) {
        int status = HttpStatus.CONFLICT.value();
        String msgErro = "Estoque insuficiente.";
        RespostaErroPadrao resposta = formatarResposta(status, msgErro, e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(resposta);
    }

    @ExceptionHandler(ServicoIndisponivelException.class)
    public ResponseEntity<RespostaErroPadrao> servicoIndisponivel(ServicoIndisponivelException e, HttpServletRequest request) {
        int status = HttpStatus.SERVICE_UNAVAILABLE.value();
        String msgErro = "Serviço indisponível.";
        RespostaErroPadrao resposta = formatarResposta(status, msgErro, e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(resposta);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RespostaErroPadrao> errosValidacao(MethodArgumentNotValidException e, HttpServletRequest request) {
        int status = HttpStatus.BAD_REQUEST.value();
        String msgErro = "Erro nos dados da requisição.";
        String msgErroEspecifica = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(erro -> erro.getField() + ": " + erro.getDefaultMessage())
                .collect(Collectors.joining("; "));

        RespostaErroPadrao resposta = formatarResposta(status, msgErro, msgErroEspecifica, request.getRequestURI());

        return ResponseEntity.status(status).body(resposta);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<RespostaErroPadrao> jsonInvalido(HttpMessageNotReadableException e, HttpServletRequest request) {
        int status = HttpStatus.BAD_REQUEST.value();
        String msgErro = "Corpo da requisição inválido ou malformado.";
        String jsonInvalido = "Verifique a estrutura e os tipos dos dados enviados no JSON.";
        RespostaErroPadrao resposta = formatarResposta(status, msgErro, jsonInvalido, request.getRequestURI());

        return ResponseEntity.status(status).body(resposta);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<RespostaErroPadrao> parametroInvalido(MethodArgumentTypeMismatchException e, HttpServletRequest request) {
        int status = HttpStatus.BAD_REQUEST.value();
        String msgErro = "Parâmetro inválido.";
        String parametroInvalido = "O parâmetro informado na URL não é válido.";
        RespostaErroPadrao resposta = formatarResposta(status, msgErro, parametroInvalido, request.getRequestURI());

        return ResponseEntity.status(status).body(resposta);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RespostaErroPadrao> erroInesperado(Exception e, HttpServletRequest request) {
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        String msgErro = "Erro interno.";
        String erroInesperado = "Ocorreu um erro inesperado. Tente novamente mais tarde.";
        log.error("Erro inesperado em {} {}", request.getMethod(), request.getRequestURI(), e);
        RespostaErroPadrao resposta = formatarResposta(status, msgErro, erroInesperado, request.getRequestURI());

        return ResponseEntity.status(status).body(resposta);
    }
}
