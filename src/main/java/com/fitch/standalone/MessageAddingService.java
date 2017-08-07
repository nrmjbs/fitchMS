package com.fitch.standalone;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.fitch.dto.MessageDto;

public class MessageAddingService implements IMessageAddingService {

	private final Map<MessageDto, MessageCallback> messageCallbackMap = new ConcurrentHashMap<>();

	@Override
	public boolean addMessage(MessageCallbackWrapper messageCallbackWrapper) {

		return messageCallbackMap.put(
				messageCallbackWrapper.getMessage(),
				messageCallbackWrapper.getMessageCallback()) != null ? false : true;

	}

	public Map<MessageDto, MessageCallback> getMessageCallbackMap() {

		return messageCallbackMap;

	}

}
