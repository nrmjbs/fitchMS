package com.fitch.domain;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fitch.enums.MessagePriority;
import com.fitch.enums.MessageType;

@Entity
public class Message implements java.io.Serializable {

	private static final long serialVersionUID = -7663519385433037470L;

	@Id
	@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "messageSequenceGenerator", sequenceName = "messageSequence")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "messageSequenceGenerator")
	private Long id;

	@Column(name = "message_text", length = 100, nullable = false)
	private String messageText;

	@Column(name = "message_type", length = 1, nullable = false)
	private MessageType messageType;

	@Column(name = "message_priority", length = 1, nullable = false)
	private MessagePriority messagePriority;

	@Column(name = "message_create_ts", nullable = false)
	private LocalDateTime messageCreateDateTime;

	public Message() {
	}

	public Message(String messageText, MessageType messageType, MessagePriority messagePriority,
			LocalDateTime messageCreateDateTime) {

		this.messageText = messageText;

		this.messageType = messageType;

		this.messagePriority = messagePriority;

		this.messageCreateDateTime = messageCreateDateTime;

	}

	public Long getId() {

		return id;

	}

	public void setId(Long id) {

		this.id = id;

	}

	public String getMessageText() {

		return messageText;

	}

	public void setMessageText(String messageText) {

		this.messageText = messageText;

	}

	public MessageType getMessageType() {

		return messageType;

	}

	public void setMessageType(MessageType messageType) {

		this.messageType = messageType;

	}

	public MessagePriority getMessagePriority() {

		return messagePriority;

	}

	public void setMessagePriority(MessagePriority messagePriority) {

		this.messagePriority = messagePriority;

	}

	public LocalDateTime getMessageCreateDateTime() {
		return messageCreateDateTime;
	}

	public void setMessageCreateDateTime(LocalDateTime messageCreateDateTime) {
		this.messageCreateDateTime = messageCreateDateTime;
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

		if (!(obj instanceof Message)) {
			return false;
		}

		Message other = (Message) obj;

		return new EqualsBuilder()	.append(messageType, other.messageType)
									.append(messagePriority, other.messagePriority)
									.append(messageCreateDateTime, other.messageCreateDateTime)
									.isEquals();

	}

	@Override
	public String toString() {

		return new ToStringBuilder(this).append("id", id)
										.append("messageType", messageType)
										.append("messagePriority", messagePriority)
										.append("messageCreateDateTime", messageCreateDateTime)
										.append("messageText", messageText)
										.toString();

	}

}
