package com.fitch.standalone;

import java.util.Map;

import com.fitch.dto.MessageDto;
import com.fitch.enums.ErrorType;
import com.fitch.exception.NotFoundException;

public class MessageGettingService implements IMessageGettingService {

	private final Object mutex = new Object();

	@Override
	public MessageCallbackWrapper getMessage(String messageType, Map<MessageDto, MessageCallback> messageCallbackMap) {

		synchronized (mutex) {

			MessageDto messageDto = messageCallbackMap	.keySet()
														.stream()
														.filter(
																innerMessageDto -> innerMessageDto
																									.getMessageType()
																									.equals(
																											messageType))
														.sorted()
														.findFirst()
														.orElseThrow(
																() -> new NotFoundException(
																		String.format(
																				ErrorType.MESSAGE_NOT_FOUND_BY_TYPE.getText(),
																				messageType)));

			MessageCallback messageCallback = messageCallbackMap.remove(messageDto);

			return new MessageCallbackWrapper(messageDto, messageCallback);

		}

	}

}
