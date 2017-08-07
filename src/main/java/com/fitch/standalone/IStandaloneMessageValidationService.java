package com.fitch.standalone;

import com.fitch.service.IMessageValidationService;

public interface IStandaloneMessageValidationService extends IMessageValidationService {

	ValidationStatus validateMessageCallbackWrapper(MessageCallbackWrapper messageCallbackWrapper);

}
