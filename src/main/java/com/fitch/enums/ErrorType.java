package com.fitch.enums;

public enum ErrorType {

	MESSAGE_NON_NULL("Message cannot be null."),

	MESSAGE_NOT_FOUND("Message not found."),

	MESSAGE_NOT_FOUND_BY_TYPE("Message with type %s not found."),

	MESSAGE_NOT_FOUND_BY_TYPE_AND_PRIORITY("Message with type %s and %d not found."),

	MESSAGE_ID_NON_NULL("Message id cannot be null."),

	MESSAGE_TYPE_NON_NULL("Message type cannot be null.");

	private String text;

	private ErrorType(String text) {

		this.text = text;

	}

	public String getText() {

		return this.text;

	}

}
