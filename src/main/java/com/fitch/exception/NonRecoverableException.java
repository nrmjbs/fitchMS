package com.fitch.exception;

public abstract class NonRecoverableException extends RuntimeException {

	private static final long serialVersionUID = 7626026058472105654L;

	public NonRecoverableException(String message) {

		super(message);

	}

	public NonRecoverableException(String message, Throwable cause) {

		super(message, cause);

	}

}
