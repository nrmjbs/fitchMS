package com.fitch.exception;

public abstract class RecoverableException extends RuntimeException {

	private static final long serialVersionUID = 7626026058472105654L;

	public RecoverableException(String message) {

		super(message);

	}

	public RecoverableException(String message, Throwable cause) {

		super(message, cause);

	}

}
