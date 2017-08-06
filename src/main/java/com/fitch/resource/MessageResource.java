package com.fitch.resource;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fitch.domain.Message;
import com.fitch.dto.MessageDto;
import com.fitch.enums.MessageType;
import com.fitch.service.IMessageMapperService;
import com.fitch.service.IMessageService;
import com.fitch.service.IMessageValidationService;

@RestController
public class MessageResource {

	@Inject
	private IMessageService messageService;

	@Inject
	private IMessageValidationService messageValidationService;

	@Inject
	private IMessageMapperService messageMapperService;

	@PostMapping(value = "/messages")
	public MessageDto createMessage(@RequestBody MessageDto messageDto) {

		// validate the input
		messageValidationService.validateMessageForRequiredAttributes(messageDto);

		// map message dto to message
		Message message = messageMapperService.mapToMessage(messageDto);

		// save the message
		messageService.createMessage(message);

		// map and return the created message
		return messageMapperService.mapToMessageDto(message);

	}

	@GetMapping(value = "/messages")
	public MessageDto findAndRemoveMessage(@RequestParam(value = "type") String messageType) {

		// validate the input
		messageValidationService.validateMessageType(messageType);

		Message message = null;

		synchronized (this) {

			// get the message by type
			message = messageService.findMessage(MessageType.valueOf(messageType));

			// remove the message
			messageService.removeMessage(message.getId());

		}

		// map message to dto and return
		return messageMapperService.mapToMessageDto(message);

	}

}
