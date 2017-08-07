package com.fitch.dto;

import java.io.Serializable;
import java.util.Objects;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@XmlRootElement
public final class MessageDto implements Serializable, Comparable<MessageDto> {

	private static final long serialVersionUID = -7597489593165521628L;

	private String messageText;

	private String messageType;

	private int messagePriority;

	private String messageCreateDateTime;

	public MessageDto() {
	}

	public MessageDto(String messageText, String messageType, int messagePriority) {

		this(messageText, messageType, messagePriority, null);

	}

	public MessageDto(String messageText, String messageType, int messagePriority,
			String messageCreateDateTime) {

		this.messageText = messageText;

		this.messageType = messageType;

		this.messagePriority = messagePriority;

		this.messageCreateDateTime = messageCreateDateTime;

	}

	public String getMessageText() {

		return messageText;

	}

	public String getMessageType() {

		return messageType;

	}

	public int getMessagePriority() {

		return messagePriority;

	}

	public String getMessageCreateDateTime() {

		return messageCreateDateTime;

	}

	@Override
	public int hashCode() {

		return Objects.hash(messageType, messagePriority, messageCreateDateTime);

	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (!(obj instanceof MessageDto)) {
			return false;
		}

		MessageDto other = (MessageDto) obj;

		return new EqualsBuilder()	.append(messageType, other.messageType)
									.append(messagePriority, other.messagePriority)
									.append(messageCreateDateTime, other.messageCreateDateTime)
									.isEquals();

	}

	@Override
	public String toString() {

		return new ToStringBuilder(this).append("messageType", messageType)
										.append("messagePriority", messagePriority)
										.append("messageCreateDateTime", messageCreateDateTime)
										.append("messageText", messageText)
										.toString();

	}

	@Override
	public int compareTo(MessageDto messageDto) {

		CompareToBuilder compareToBuilder = new CompareToBuilder();

		return compareToBuilder	.append(messageType, messageDto.getMessageType())
								.append(messagePriority, messageDto.getMessagePriority())
								.append(messageCreateDateTime, messageDto.getMessageCreateDateTime())
								.toComparison();

	}

}
