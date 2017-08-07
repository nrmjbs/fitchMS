package com.fitch.standalone;

import java.util.Map;

import com.fitch.dto.MessageDto;

public interface IMessageGettingService {

	MessageCallbackWrapper getMessage(String messageType, Map<MessageDto, MessageCallback> messageCallbackMap);

}
