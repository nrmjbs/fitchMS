package com.fitch.standalone;

public enum StatusMessageType {

	INVALID_MESSAGE("Message is not valid."),

	DUPLICATE_MESSAGE("Another message with same type, priority and create dateTime exist."),

	GOOD_MESSAGE("Message is added."),

	EMPTY("");

	private String text;

	private StatusMessageType(String text) {

		this.text = text;

	}

	public String getText() {

		return text;

	}

}
