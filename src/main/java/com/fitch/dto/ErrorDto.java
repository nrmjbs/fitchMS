package com.fitch.dto;

import java.io.Serializable;
import java.util.Objects;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@XmlRootElement
public class ErrorDto implements Serializable {

	private static final long serialVersionUID = -7597489593165521628L;

	private String code;

	private String message;

	public ErrorDto() {

	}

	public ErrorDto(String code, String message) {

		this.code = code;

		this.message = message;

	}

	public String getCode() {

		return code;

	}

	public void setCode(String code) {

		this.code = code;

	}

	public String getMessage() {

		return message;

	}

	public void setMessage(String message) {

		this.message = message;

	}

	@Override
	public int hashCode() {

		return Objects.hash(code, message);

	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj) {

			return true;

		}

		if (obj == null) {

			return false;

		}

		if (!(obj instanceof ErrorDto)) {

			return false;

		}

		ErrorDto other = (ErrorDto) obj;

		return Objects.equals(code, other.code) &&
				Objects.equals(message, other.message);

	}

	@Override
	public String toString() {

		ToStringBuilder toStringBuilder = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE);

		toStringBuilder.append("code", code);

		toStringBuilder.append("message", message);

		return toStringBuilder.toString();

	}

}
