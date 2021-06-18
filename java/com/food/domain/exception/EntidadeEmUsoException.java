package com.food.domain.exception;

public class EntidadeEmUsoException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EntidadeEmUsoException() {
		super();
	}

	public EntidadeEmUsoException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EntidadeEmUsoException(String message, Throwable cause) {
		super(message, cause);
	}

	public EntidadeEmUsoException(String message) {
		super(message);
	}

	public EntidadeEmUsoException(Throwable cause) {
		super(cause);
	}

	
	
}
