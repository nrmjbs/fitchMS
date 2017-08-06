package com.fitch.service;

import com.fitch.domain.Message;
import com.fitch.dto.MessageDto;

public interface IMessageMapperService {

	Message mapToMessage(MessageDto messageDto);

	MessageDto mapToMessageDto(Message message);

}
