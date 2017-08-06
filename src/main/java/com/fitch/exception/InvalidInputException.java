package com.fitch.exception;

public class InvalidInputException extends ValidationException {

	private static final long serialVersionUID = 7626026058472105654L;

	public InvalidInputException(String message) {

		super(message);

	}

	public InvalidInputException(String message, Throwable cause) {

		super(message, cause);

	}

}
