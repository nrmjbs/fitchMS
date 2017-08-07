package com.fitch.standalone;

public enum StatusType {

	ADDED(true),

	NOT_ADDED(false),

	VALID(true),

	INVALID(false);

	private boolean status;

	private StatusType(boolean status) {

		this.status = status;

	}

	public boolean getValue() {

		return status;

	}

}
