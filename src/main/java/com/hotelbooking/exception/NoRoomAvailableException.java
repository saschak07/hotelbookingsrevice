package com.hotelbooking.exception;

public class NoRoomAvailableException extends RuntimeException {

	private String message ;

	public NoRoomAvailableException(String message) {
		super();
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
}