package com.fitch.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import com.fitch.domain.Message;
import com.fitch.dto.MessageDto;
import com.fitch.enums.MessagePriority;
import com.fitch.enums.MessageType;
import com.fitch.service.IMessageMapperService;

@Named
public class MessageMapperService implements IMessageMapperService {

	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

	@Override
	public Message mapToMessage(MessageDto messageDto) {

		return new Message(
				messageDto.getMessageText(),
				MessageType.valueOf(messageDto.getMessageType()),
				MessagePriority.getByCode(messageDto.getMessagePriority()),
				StringUtils.isNotBlank(messageDto.getMessageCreateDateTime())
						? LocalDateTime.parse(messageDto.getMessageCreateDateTime(), dateTimeFormatter)
						: LocalDateTime.now());

	}

	@Override
	public MessageDto mapToMessageDto(Message message) {

		return new MessageDto(
				message.getMessageText(),
				message	.getMessageType()
						.name(),
				message	.getMessagePriority()
						.getPriorityCode(),
				message	.getMessageCreateDateTime()
						.format(dateTimeFormatter));

	}

}
