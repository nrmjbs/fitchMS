package com.fitch.standalone;

public class ValidationStatus {

	private StatusType status;

	private StatusMessageType statusMessage;

	private String additionalInformation;

	private ValidationProcessType processType;

	public ValidationStatus(StatusType status, StatusMessageType statusMessage, String additionalInformation,
			ValidationProcessType processType) {

		this.status = status;

		this.statusMessage = statusMessage;

		this.additionalInformation = additionalInformation;

		this.processType = processType;

	}

	public StatusType getStatus() {

		return status;

	}

	public StatusMessageType getStatusMessage() {

		return statusMessage;

	}

	public String getAdditionalInformation() {

		return additionalInformation;

	}

	public ValidationProcessType getProcessType() {

		return processType;

	}

	public boolean isValid() {

		return status.getValue();

	}

}
