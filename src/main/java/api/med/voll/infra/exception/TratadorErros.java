package api.med.voll.infra.exception;

import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class TratadorErros {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<?> tratarErro404(){
		return ResponseEntity.notFound().build();
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> tratarerro400(MethodArgumentNotValidException ex){
		
		var erros = ex.getFieldErrors();
		
		return ResponseEntity.badRequest().body(erros.stream().map(DadosErrosValidacao::new).toList());
	}
	
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<?> tratarerro400(SQLIntegrityConstraintViolationException ex){
		
		var erros = ex.getMessage();
		
		return ResponseEntity.badRequest().body(new GenericMessage(erros));
	}
	
	@ExceptionHandler(ValidacaoException.class)
	public ResponseEntity<?> tratarErroRegraNegocio(ValidacaoException ex){
		
		var erros = ex.getMessage();
		
		return ResponseEntity.badRequest().body(new GenericMessage(erros));
	}
	
	private record DadosErrosValidacao(String campo, String mensagem) {
		
		public DadosErrosValidacao(FieldError error) {
			this(error.getField(), error.getDefaultMessage());
		}
	}
	
	private record GenericMessage(String mensagem) {
	}
}
