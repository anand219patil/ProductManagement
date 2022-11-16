package com.abc.restApi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ProductAlreadyExistsException.class)
	public ResponseEntity<String> productAlreadyExistsException(ProductAlreadyExistsException excep) {
		String msg = excep.getMessage();
		return new ResponseEntity<String>(msg, HttpStatus.OK);
	}
	
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<String> productNotFound(ProductNotFoundException excep) {
		String msg = excep.getMessage();
		return new ResponseEntity<String>(msg, HttpStatus.OK);
	}
	

}
