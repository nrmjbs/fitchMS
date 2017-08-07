package com.fitch.standalone;

import java.util.Objects;

import com.fitch.enums.ValidationErrorType;
import com.fitch.exception.InvalidInputException;
import com.fitch.exception.MissingInputException;
import com.fitch.service.impl.MessageValidationService;

public class StandaloneMessageValidationService extends MessageValidationService implements
		IStandaloneMessageValidationService {

	@Override
	public ValidationStatus validateMessageCallbackWrapper(MessageCallbackWrapper messageCallbackWrapper) {

		try {

			Objects.requireNonNull(
					messageCallbackWrapper,
					ValidationErrorType.MESSAGE_WRAPPER_CANNOT_BE_NULL.getText());

			Objects.requireNonNull(
					messageCallbackWrapper.getMessageCallback(),
					ValidationErrorType.MESSAGE_CALLBACK_CANNOT_BE_NULL.getText());

		} catch (NullPointerException npe) {

			return new ValidationStatus(
					StatusType.INVALID,
					StatusMessageType.INVALID_MESSAGE,
					npe.getMessage(),
					ValidationProcessType.HARD_STOPPED);

		}

		try {

			validateMessageForRequiredAttributes(messageCallbackWrapper.getMessage());

			return new ValidationStatus(
					StatusType.VALID,
					StatusMessageType.GOOD_MESSAGE,
					StatusMessageType.EMPTY.getText(),
					ValidationProcessType.CONTINUE);

		} catch (NullPointerException | MissingInputException | InvalidInputException rex) {

			return new ValidationStatus(
					StatusType.INVALID,
					StatusMessageType.INVALID_MESSAGE,
					rex.getMessage(),
					ValidationProcessType.CONTINUE);

		}

	}

}
