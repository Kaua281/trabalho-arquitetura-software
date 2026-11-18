package br.edu.ucsal.ms_clientes.exception;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class CapturadorExcecoes {
	
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
	
	private RespostaErroPadrao formatarResposta(Integer status, String msgErroPadrao, String msgErroEspecifica, String endpoint) {	
		return  new RespostaErroPadrao(
				Instant.now(),
				status,
				msgErroPadrao,
				msgErroEspecifica,
				endpoint
				);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<RespostaErroPadrao> argumentosInvalidos(IllegalArgumentException e, HttpServletRequest request){
		Integer status = HttpStatus.BAD_REQUEST.value();
		String msgErroPadrao = "Erro nos dados da requisição.";
		
		RespostaErroPadrao error = formatarResposta(status, msgErroPadrao, e.getMessage(), request.getRequestURI());
		
		return ResponseEntity.status(status).body(error);
	}
}
