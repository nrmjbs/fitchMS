package com.fitch.service.impl;

import java.util.Objects;

import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import com.fitch.dto.MessageDto;
import com.fitch.enums.ValidationErrorType;
import com.fitch.enums.MessagePriority;
import com.fitch.enums.MessageType;
import com.fitch.exception.InvalidInputException;
import com.fitch.exception.MissingInputException;
import com.fitch.service.IMessageValidationService;

@Named
public class MessageValidationService implements IMessageValidationService {

	@Override
	public void validateMessageForRequiredAttributes(MessageDto message) {

		Objects.requireNonNull(message, ValidationErrorType.MESSAGE_CANNOT_BE_NULL.getText());

		validateMessageTextNotBlank(message.getMessageText());

		validateMessageType(message.getMessageType());

		validateMessagePrioritySupported(message.getMessagePriority());

	}

	@Override
	public void validateMessageType(String messageType) {

		validateMessageTypeNotBlank(messageType);

		validateMessageTypeSupported(messageType);

	}

	private void validateMessageTextNotBlank(String messageText) {

		if (StringUtils.isBlank(messageText)) {

			throw new MissingInputException(ValidationErrorType.MESSAGE_TEXT_CANNOT_BE_EMPTY.getText());

		}

	}

	private void validateMessageTypeNotBlank(String messageType) {

		if (StringUtils.isBlank(messageType)) {

			throw new MissingInputException(ValidationErrorType.MESSAGE_TYPE_CANNOT_BE_EMPTY.getText());

		}

	}

	private void validateMessageTypeSupported(String messageType) {

		try {

			MessageType.valueOf(messageType);

		} catch (IllegalArgumentException iae) {

			throw new InvalidInputException(
					String.format(ValidationErrorType.MESSAGE_TYPE_NOT_SUPPORTED.getText(), messageType));

		}

	}

	private void validateMessagePrioritySupported(int priorityCode) {

		MessagePriority messagePriority = MessagePriority.getByCode(priorityCode);

		if (messagePriority == null) {

			throw new InvalidInputException(
					String.format(
							ValidationErrorType.MESSAGE_PRIORITY_NOT_SUPPORTED.getText(),
							priorityCode));

		}

	}

}
