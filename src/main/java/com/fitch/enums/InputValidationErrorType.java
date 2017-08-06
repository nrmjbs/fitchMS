package com.fitch.enums;

public enum InputValidationErrorType {

	MESSAGE_CANNOT_BE_EMPTY("Message cannot be empty."),

	MESSAGE_TEXT_CANNOT_BE_EMPTY("Message text cannot be empty."),

	MESSAGE_PRIORITY_CANNOT_BE_EMPTY("Message priority cannot be empty."),

	MESSAGE_TYPE_CANNOT_BE_EMPTY("Message type cannot be empty."),

	MESSAGE_TYPE_NOT_SUPPORTED("Message type %s not supported."),

	MESSAGE_PRIORITY_NOT_SUPPORTED("Message priority %d not supported.");

	String text;

	private InputValidationErrorType(String text) {

		this.text = text;

	}

	public String getText() {

		return this.text;

	}

}
