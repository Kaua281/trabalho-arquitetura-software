package exception;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class CapturadorExcecoes {
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<RespostaErroPadrao> entidadeNaoEncontrada(EntityNotFoundException e, HttpServletRequest request){
		HttpStatus status = HttpStatus.NOT_FOUND;
		
		RespostaErroPadrao error = new RespostaErroPadrao(
				Instant.now(),
				status.value(),
				"Entidade não encontrada",
				e.getMessage(),
				request.getRequestURI());
		
		return ResponseEntity.status(status).body(error);
		
	}
}
