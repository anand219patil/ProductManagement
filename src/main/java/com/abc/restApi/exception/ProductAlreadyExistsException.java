package com.abc.restApi.exception;

public class ProductAlreadyExistsException extends RuntimeException{

	public ProductAlreadyExistsException(String message) {
		
		super(message);
	}
}
