package com.bludkiewicz.montyhall.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

/**
 * Exceptions are automatically handled by Spring Boot.
 * I am overriding that handling as the response does not usually indicate what the problem was.
 */
@ControllerAdvice
public class ControllerExceptionHandler {

	/**
	 * Bean Validation API Constraints.
	 * For some reason this is not automatically returned as a 400.
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<?> handleBadInput(ConstraintViolationException ex) {

		return new ResponseEntity<>(formatMessage(ex.getMessage()), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Unsupported request methods.
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<?> handleInvalidMethodArgument(HttpRequestMethodNotSupportedException ex) {

		return new ResponseEntity<>(formatMessage(ex.getMessage()), HttpStatus.METHOD_NOT_ALLOWED);
	}

	/**
	 * Invalid argument types (character values in an integer, etc).
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<?> handleInvalidMethodArgument(IllegalArgumentException ex) {

		return new ResponseEntity<>(formatMessage(ex.getMessage()), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Invalid JSON arguments.
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleInvalidMethodArgument(MethodArgumentNotValidException ex) {

		// ex.getMessage() is not very user friendly
		// so we will build our own messages
		StringBuilder message = new StringBuilder();
		ex.getBindingResult().getFieldErrors()
				.forEach(error -> message.append(error.getField()).append(" ").append(error.getDefaultMessage()).append('\n'));

		return new ResponseEntity<>(formatMessage(message.toString()), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Invalid JSON format.
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {

		return new ResponseEntity<>(formatMessage(ex.getMessage()), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Formats Error Message.
	 */
	private String formatMessage(String message) {

		return MESSAGE_PREFIX + '\n' + message;
	}

	// Helps test that exception handler is firing when it's supposed to.
	public final static String MESSAGE_PREFIX = "ERROR(S):";
}
