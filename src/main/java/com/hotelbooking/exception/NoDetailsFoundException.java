package com.hotelbooking.exception;

public class NoDetailsFoundException extends RuntimeException {

	private String MESSAGE ;

	public NoDetailsFoundException(String mESSAGE) {
		super();
		MESSAGE = mESSAGE;
	}
	
}
