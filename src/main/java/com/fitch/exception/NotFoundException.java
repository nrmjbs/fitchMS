package com.fitch.exception;

public class NotFoundException extends NonRecoverableException {

	private static final long serialVersionUID = 7626026058472105654L;

	public NotFoundException(String message) {

		super(message);

	}

	public NotFoundException(String message, Throwable cause) {

		super(message, cause);

	}

}
