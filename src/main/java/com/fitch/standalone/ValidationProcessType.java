package com.fitch.standalone;

public enum ValidationProcessType {

	HARD_STOPPED(1),

	CONTINUE(2);

	private int type;

	private ValidationProcessType(int type) {

		this.type = type;

	}

	public int getValue() {

		return type;

	}

}
