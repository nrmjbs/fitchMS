package com.fitch.enums;

public enum MessageType {

	RED("R"),

	YELLOW("Y"),

	BLUE("B");

	private String shortCode;

	private MessageType(String shortCode) {

		this.shortCode = shortCode;

	}

	public String getShortCode() {

		return this.shortCode;

	}

}
