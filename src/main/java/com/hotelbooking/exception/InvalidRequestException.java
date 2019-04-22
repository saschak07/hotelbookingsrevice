package com.hotelbooking.exception;

public class InvalidRequestException extends RuntimeException {

	private String message ;

	public InvalidRequestException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
}
