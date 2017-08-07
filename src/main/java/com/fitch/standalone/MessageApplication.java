package com.fitch.standalone;

public class MessageApplication {

	private final IMessageAddingService messageAddingService = new MessageAddingService();

	private final IStandaloneMessageValidationService messageValidationService = new StandaloneMessageValidationService();

	private final IMessageGettingService messageGettingService = new MessageGettingService();

	////////////////////////////////////////////////////////////////////

	public void addMessage(MessageCallbackWrapper messageCallbackWrapper) {

		ValidationStatus validationStatus = messageValidationService.validateMessageCallbackWrapper(
				messageCallbackWrapper);

		if (!validationStatus.isValid()) {

			if (ValidationProcessType.HARD_STOPPED.equals(validationStatus.getProcessType())) {

				// return quietly as the key components are missing for a
				// callback
				return;

			} else {

				messageCallbackWrapper	.getMessageCallback()
										.process(
												messageCallbackWrapper.getMessage(),
												StatusType.NOT_ADDED,
												validationStatus.getStatusMessage(),
												validationStatus.getAdditionalInformation());

				return;

			}

		}

		boolean isAdded = messageAddingService.addMessage(messageCallbackWrapper);

		messageCallbackWrapper	.getMessageCallback()
								.process(
										messageCallbackWrapper.getMessage(),
										isAdded ? StatusType.ADDED : StatusType.NOT_ADDED,
										isAdded ? StatusMessageType.GOOD_MESSAGE : StatusMessageType.DUPLICATE_MESSAGE,
										StatusMessageType.EMPTY.getText());

	}

	public MessageCallbackWrapper getMessage(String messageType) {

		messageValidationService.validateMessageType(messageType);

		return messageGettingService.getMessage(messageType, messageAddingService.getMessageCallbackMap());

	}

}
