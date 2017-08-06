package com.fitch.service;

import com.fitch.domain.Message;
import com.fitch.enums.MessageType;

public interface IMessageService {

	public void createMessage(Message message);

	public Message findMessage(MessageType messageType);

	public void removeMessage(Long messageId);

}
