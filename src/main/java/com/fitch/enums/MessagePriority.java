package com.fitch.enums;

import java.util.Arrays;

public enum MessagePriority {

	HIGHEST(1),

	MEDIUM(2),

	LOWEST(3);

	private int priorityCode;

	private MessagePriority(int priorityCode) {

		this.priorityCode = priorityCode;

	}

	public int getPriorityCode() {

		return this.priorityCode;

	}

	public static MessagePriority getByCode(int priorityCode) {

		return Arrays	.stream(MessagePriority.values())
						.filter(messagePriority -> (messagePriority.getPriorityCode() == priorityCode))
						.findFirst()
						.orElse(null);

	}

}
