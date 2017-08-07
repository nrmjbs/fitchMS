package com.fitch.enums;

public enum ValidationErrorType {

	MESSAGE_CANNOT_BE_NULL("Message cannot be null."),

	MESSAGE_WRAPPER_CANNOT_BE_NULL("Message wrapper cannot be null."),

	MESSAGE_CALLBACK_CANNOT_BE_NULL("Message callback cannot be null."),

	MESSAGE_TEXT_CANNOT_BE_EMPTY("Message text cannot be empty."),

	MESSAGE_PRIORITY_CANNOT_BE_EMPTY("Message priority cannot be empty."),

	MESSAGE_TYPE_CANNOT_BE_EMPTY("Message type cannot be empty."),

	MESSAGE_TYPE_NOT_SUPPORTED("Message type %s not supported."),

	MESSAGE_PRIORITY_NOT_SUPPORTED("Message priority %d not supported.");

	private String text;

	private ValidationErrorType(String text) {

		this.text = text;

	}

	public String getText() {

		return this.text;

	}

}
