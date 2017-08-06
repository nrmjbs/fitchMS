package com.fitch.service.impl;

import java.util.Objects;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.stereotype.Service;

import com.fitch.domain.Message;
import com.fitch.enums.ErrorType;
import com.fitch.enums.MessageType;
import com.fitch.exception.NotFoundException;
import com.fitch.repository.MessageRepository;
import com.fitch.service.IMessageService;

@Service
@Transactional(value = TxType.REQUIRED)
public class MessageService implements IMessageService {

	@Inject
	private MessageRepository messageRepository;

	@Override
	public void createMessage(Message message) {

		Objects.requireNonNull(message, ErrorType.MESSAGE_NON_NULL.getText());

		messageRepository.save(message);

	}

	@Override
	public Message findMessage(MessageType messageType) {

		Objects.requireNonNull(messageType, ErrorType.MESSAGE_TYPE_NON_NULL.getText());

		return messageRepository.findMessagesByMessageType(messageType)
								.stream()
								.sorted((Message message1, Message message2) -> {

									// get the difference between the priority code of two messages
									int comparisonResult = message1	.getMessagePriority()
																	.getPriorityCode() - message2
																									.getMessagePriority()
																									.getPriorityCode();

									// Only if the two messages are equal on priority, get their difference between creation date
									// to figure the message in FIFO order.
									// else return the difference between their priority code. That should be enough to get handle
									// of message with highest priority
									if (comparisonResult == 0) {

										return message1	.getMessageCreateDateTime()
														.compareTo(message2.getMessageCreateDateTime());

									} else {

										return comparisonResult;

									}

								})
								.findFirst()
								.orElseThrow(
										() -> new NotFoundException(
												String.format(
														ErrorType.MESSAGE_NOT_FOUND_BY_TYPE.getText(),
														messageType)));

	}

	@Override
	public void removeMessage(Long messageId) {

		Objects.requireNonNull(messageId, ErrorType.MESSAGE_ID_NON_NULL.getText());

		messageRepository.delete(messageId);

	}

}
