package br.mma.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.mma.exception.PedidoNotFoundException;
import br.mma.exception.SalesException;

/**
 * Capturar e tratar exceções
 */
@RestControllerAdvice
public class ApplicationControllerAdvice {

	@ExceptionHandler(SalesException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrors handleException(SalesException ex) {
		return new ApiErrors(ex.getMessage());
	}
	
	@ExceptionHandler(PedidoNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiErrors handlePedidoNotFoundException(PedidoNotFoundException ex) {
		return new ApiErrors(ex.getMessage());
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiErrors handlePedidoNotFoundException(MethodArgumentNotValidException ex) {
		
		List<String> errors = ex.getBindingResult()
								.getAllErrors()
								.stream()
								.map(err -> err.getDefaultMessage())
								.collect(Collectors.toList());
		
		return new ApiErrors(errors);
	}
}
