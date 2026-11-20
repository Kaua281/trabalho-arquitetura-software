package br.edu.ucsal.ms_produtos.exception;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class CapturadorExcecoes {
	
	private RespostaErroPadrao formatarResposta(Integer status, String msgErroPadrao, String msgErroEspecifica, String endpoint) {	
		return  new RespostaErroPadrao(
				Instant.now(),
				status,
				msgErroPadrao,
				msgErroEspecifica,
				endpoint
				);
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<RespostaErroPadrao> entidadeNaoEncontrada(EntityNotFoundException e, HttpServletRequest request){
		Integer status = HttpStatus.NOT_FOUND.value();
		String msgErroPadrao = "Entidade não encontrada.";
		
		RespostaErroPadrao error = formatarResposta(status, msgErroPadrao, e.getMessage(), request.getRequestURI());
		
		return ResponseEntity.status(status).body(error);
		
	}
	
	@ExceptionHandler(EntityExistsException.class)
	public ResponseEntity<RespostaErroPadrao> entidadeExistente(EntityExistsException e, HttpServletRequest request){
		Integer status = HttpStatus.CONFLICT.value();
		String msgErroPadrao = "Entidade duplicada.";
		
		RespostaErroPadrao error = formatarResposta(status, msgErroPadrao, e.getMessage(), request.getRequestURI());
		
		return ResponseEntity.status(status).body(error);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<RespostaErroPadrao> argumentosInvalidos(IllegalArgumentException e, HttpServletRequest request){
		Integer status = HttpStatus.BAD_REQUEST.value();
		String msgErroPadrao = "Erro nos dados da requisição.";
		
		RespostaErroPadrao error = formatarResposta(status, msgErroPadrao, e.getMessage(), request.getRequestURI());
		
		return ResponseEntity.status(status).body(error);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<RespostaErroPadrao> errosValidacao(MethodArgumentNotValidException e, HttpServletRequest request){
		Integer status = HttpStatus.BAD_REQUEST.value();
		String msgErroPadrao = "Erro nos dados da requisição.";
		
		// Obtém os campos inválidos identificados pelo @Valid nos parâmetros de alguns métodos do controller e concatena suas respectivas mensagens de erro
		String msgErroEspecifica = e.getBindingResult()
		            .getFieldErrors()
		            .stream()
		            .map(erro -> erro.getField() + ": " + erro.getDefaultMessage())
		            .collect(Collectors.joining("; "));
		
		RespostaErroPadrao error = formatarResposta(status, msgErroPadrao, msgErroEspecifica, request.getRequestURI());
		
		return ResponseEntity.status(status).body(error);
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<RespostaErroPadrao> jsonInvalido(HttpMessageNotReadableException e, HttpServletRequest request){
		Integer status = HttpStatus.BAD_REQUEST.value();
		String msgErroPadrao = "Corpo da requisição inválido ou malformado.";
		String msgErroEspecifica = "Verifique a estrutura e os tipos dos dados enviados no JSON.";
		
		RespostaErroPadrao error = formatarResposta(status, msgErroPadrao, msgErroEspecifica, request.getRequestURI());
		
		return ResponseEntity.status(status).body(error);
	}
	
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<RespostaErroPadrao> erroInesperado(Exception e, HttpServletRequest request){
		Integer status = HttpStatus.INTERNAL_SERVER_ERROR.value();
		
		String msgErroPadrao = "Erro interno.";
		String msgErroEspecifica = "Ocorreu um erro inesperado. Tente novamente mais tarde.";
		
		RespostaErroPadrao error = formatarResposta(status, msgErroPadrao, msgErroEspecifica, request.getRequestURI());
		
		return ResponseEntity.status(status).body(error);
	}
}
