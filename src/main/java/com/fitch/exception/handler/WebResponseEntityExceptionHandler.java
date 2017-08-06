package com.fitch.exception.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fitch.dto.ErrorDto;
import com.fitch.exception.NotFoundException;
import com.fitch.exception.RecoverableException;
import com.fitch.exception.ValidationException;

@ControllerAdvice
public class WebResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { ValidationException.class, NullPointerException.class })
	protected ResponseEntity<Object> handleInputValidationErrors(RuntimeException ex, WebRequest request) {

		ErrorDto bodyOfResponse = new ErrorDto(
				Integer.toString(HttpStatus.BAD_REQUEST.value()),
				ex.getMessage());

		return handleExceptionInternal(
				ex,
				bodyOfResponse,
				new HttpHeaders(),
				HttpStatus.BAD_REQUEST,
				request);

	}

	@ExceptionHandler(value = { NotFoundException.class })
	protected ResponseEntity<Object> handleNotFoundErrors(RuntimeException ex, WebRequest request) {

		ErrorDto bodyOfResponse = new ErrorDto(
				Integer.toString(HttpStatus.NOT_FOUND.value()),
				ex.getMessage());

		return handleExceptionInternal(
				ex,
				bodyOfResponse,
				new HttpHeaders(),
				HttpStatus.NOT_FOUND,
				request);

	}

	@ExceptionHandler(value = { RecoverableException.class })
	protected ResponseEntity<Object> handleRecoverableErrors(RuntimeException ex, WebRequest request) {

		ErrorDto bodyOfResponse = new ErrorDto(
				Integer.toString(HttpStatus.SERVICE_UNAVAILABLE.value()),
				ex.getMessage());

		return handleExceptionInternal(
				ex,
				bodyOfResponse,
				new HttpHeaders(),
				HttpStatus.SERVICE_UNAVAILABLE,
				request);

	}

}