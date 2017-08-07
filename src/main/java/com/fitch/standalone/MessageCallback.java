package com.fitch.standalone;

import com.fitch.dto.MessageDto;

@FunctionalInterface
public interface MessageCallback {

	public void process(MessageDto message, StatusType status, StatusMessageType statusMessage,
			String additionalInformation);

}
