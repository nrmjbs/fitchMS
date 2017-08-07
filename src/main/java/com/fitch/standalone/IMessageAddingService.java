package com.fitch.standalone;

import java.util.Map;

import com.fitch.dto.MessageDto;

public interface IMessageAddingService {

	boolean addMessage(MessageCallbackWrapper messageCallbackWrapper);

	Map<MessageDto, MessageCallback> getMessageCallbackMap();

}
