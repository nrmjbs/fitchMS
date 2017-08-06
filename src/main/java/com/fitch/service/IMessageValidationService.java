package com.fitch.service;

import com.fitch.dto.MessageDto;

public interface IMessageValidationService {

	void validateMessageForRequiredAttributes(MessageDto message);

	void validateMessageType(String messageType);

}
