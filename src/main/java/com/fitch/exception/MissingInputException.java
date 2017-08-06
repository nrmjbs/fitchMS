package com.fitch.exception;

public class MissingInputException extends ValidationException {

	private static final long serialVersionUID = 7626026058472105654L;

	public MissingInputException(String message) {

		super(message);

	}

	public MissingInputException(String message, Throwable cause) {

		super(message, cause);

	}

}
