package com.fitch.standalone;

import com.fitch.dto.MessageDto;

public class MessageCallbackWrapper {

	private MessageDto message;

	private MessageCallback messageCallback;

	public MessageCallbackWrapper() {

	}

	public MessageCallbackWrapper(MessageDto message, MessageCallback messageCallback) {

		this.message = message;

		this.messageCallback = messageCallback;

	}

	public MessageDto getMessage() {

		return message;

	}

	public void setMessage(MessageDto message) {

		this.message = message;

	}

	public MessageCallback getMessageCallback() {

		return messageCallback;

	}

	public void setMessageCallback(MessageCallback messageCallback) {

		this.messageCallback = messageCallback;

	}

}
