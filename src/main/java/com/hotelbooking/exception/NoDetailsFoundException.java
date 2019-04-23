package com.hotelbooking.exception;

public class NoDetailsFoundException extends RuntimeException {

	private String message ;

	public NoDetailsFoundException(String message) {
		super();
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
}
