package com.fitch.exception;

public abstract class ValidationException extends NonRecoverableException {

	private static final long serialVersionUID = 7626026058472105654L;

	public ValidationException(String message) {

		super(message);

	}

	public ValidationException(String message, Throwable cause) {

		super(message, cause);

	}

}
